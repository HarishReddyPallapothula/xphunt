package studio.contrarian.xphunt.app.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studio.contrarian.xphunt.app.dto.*;
import studio.contrarian.xphunt.app.model.*;
import studio.contrarian.xphunt.app.dto.*;
import studio.contrarian.xphunt.app.mappers.HunterMapper;
import studio.contrarian.xphunt.app.mappers.RoomMapper;
import studio.contrarian.xphunt.app.mappers.TaskEventMapper;
import studio.contrarian.xphunt.app.mappers.TaskMapper;
import studio.contrarian.xphunt.app.model.*;
import studio.contrarian.xphunt.app.repo.HunterRepository;
import studio.contrarian.xphunt.app.repo.HunterRoomRepository;
import studio.contrarian.xphunt.app.repo.RoomRepository;
import studio.contrarian.xphunt.app.repo.TaskRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final HunterRepository hunterRepository;
    private final TaskRepository taskRepository;
    private final HunterRoomRepository hunterRoomRepository;
    private final PermissionService permissionService;

    public RoomServiceImpl(RoomRepository roomRepository, HunterRepository hunterRepository, TaskRepository taskRepository, HunterRoomRepository hunterRoomRepository, PermissionService permissionService) {
        this.roomRepository = roomRepository;
        this.hunterRepository = hunterRepository;
        this.taskRepository = taskRepository;
        this.hunterRoomRepository = hunterRoomRepository;
        this.permissionService = permissionService;
    }



    @Override
    @Transactional
    public RoomDTO createRoom(CreateRoomRequest request, Long creatorId) {

        Hunter creator = hunterRepository.findById(creatorId)
                .orElseThrow(() -> new EntityNotFoundException("Hunter not found with id: " + creatorId));

        Room room = Room.builder()
                .name(request.getName())
                .inviteCode(UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .build();

        // Link the creator to the new room
        //HunterRoom hunterRoom = new HunterRoom(new HunterRoomId(creator.getId(), null), creator, room, 0);
        HunterRoom hunterRoom = new HunterRoom().builder()
                .room(room).hunter(creator).xpInRoom(0).build();
        room.getHunterRooms().add(hunterRoom);

        Room savedRoom = roomRepository.save(room);
        return RoomMapper.toDTO(savedRoom);
    }


    @Override
    @Transactional
    public RoomDTO joinRoom(String inviteCode, Long hunterId) {
        Room room = roomRepository.findByInviteCode(inviteCode)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with invite code: " + inviteCode));

        Hunter hunter = hunterRepository.findById(hunterId)
                .orElseThrow(() -> new EntityNotFoundException("Hunter not found with id: " + hunterId));

        // Check if user is already in the room
        if (hunterRoomRepository.findByRoomIdAndHunterId(room.getId(), hunter.getId()).isPresent()) {
            throw new IllegalStateException("Hunter is already in this room.");
        }

        //HunterRoom hunterRoom = new HunterRoom(new HunterRoomId(hunter.getId(), room.getId()), hunter, room, 0);
        HunterRoom hunterRoom = new HunterRoom().builder()
                        .room(room).hunter(hunter).xpInRoom(0).build();
        room.getHunterRooms().add(hunterRoom);

        Room savedRoom = roomRepository.save(room); // Cascade will save the new HunterRoom
        return RoomMapper.toDTO(savedRoom);
    }

    @Override
    @Transactional
    public TaskDTO addTaskToRoom(Long roomId, CreateTaskRequest request, Long creatorId) {
        permissionService.verifyHunterIsMemberOfRoom(roomId, creatorId);

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with id: " + roomId));

        Hunter creator = hunterRepository.findById(creatorId)
                .orElseThrow(() -> new EntityNotFoundException("Hunter not found with id: " + creatorId));

        // Ensure the creator is a member of the room
        hunterRoomRepository.findByRoomIdAndHunterId(roomId, creatorId)
                .orElseThrow(() -> new IllegalStateException("Creator is not a member of this room."));

        Task task = Task.builder()
                .description(request.getDescription())
                .xp(request.getXp())
                .room(room)
                .createdBy(creator)
                .build();

        // Create the initial "CREATED" event
        TaskEvent createdEvent = TaskEvent.builder()
                .task(task)
                .hunter(creator)
                .type(EventType.CREATED)
                .timestamp(LocalDateTime.now())
                .notes("Task created.")
                .build();

        task.getEvents().add(createdEvent);

        Task savedTask = taskRepository.save(task);
        return TaskMapper.toDTO(savedTask);
    }

    @Override
    @Transactional
    public TaskDTO claimTask(Long roomId, Long taskId, Long hunterId) {
        permissionService.verifyHunterCanAccessTask(taskId, hunterId);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));


        if (!task.getRoom().getId().equals(roomId)) {
            throw new IllegalArgumentException("Task does not belong to the specified room.");
        }
        if (task.getClaimedBy() != null) {
            throw new IllegalStateException("Task is already claimed by another hunter.");
        }

        Hunter claimer = hunterRepository.findById(hunterId)
                .orElseThrow(() -> new EntityNotFoundException("Hunter not found with id: " + hunterId));

        // Ensure the claimer is a member of the room
        hunterRoomRepository.findByRoomIdAndHunterId(roomId, hunterId)
                .orElseThrow(() -> new IllegalStateException("Claimer is not a member of this room."));

        task.setClaimedBy(claimer);

        // Create the "CLAIMED" event
        TaskEvent claimedEvent = TaskEvent.builder()
                .task(task)
                .hunter(claimer)
                .type(EventType.CLAIMED)
                .timestamp(LocalDateTime.now())
                .notes("Task claimed.")
                .build();

        task.getEvents().add(claimedEvent);

        Task savedTask = taskRepository.save(task);
        return TaskMapper.toDTO(savedTask);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskEventDTO> getRoomEvents(Long roomId) {
        return taskRepository.findEventsByRoomId(roomId).stream()
                .map(TaskEventMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<HunterRoomSimpleDTO> getRoomLeaderboard(Long roomId, Long hunterId) {
        permissionService.verifyHunterIsMemberOfRoom(roomId, hunterId);
        if (!roomRepository.existsById(roomId)) {
            throw new EntityNotFoundException("Room not found with id: " + roomId);
        }

        return hunterRoomRepository.findByRoomIdOrderByXpInRoomDesc(roomId).stream()
                .map(HunterMapper::toHunterRoomSimpleDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RoomDTO getRoomById(Long roomId, Long hunterId) {

        permissionService.verifyHunterIsMemberOfRoom(hunterId, roomId);
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with id: " + roomId));
        return RoomMapper.toDTO(room);
    }

    @Override
    @Transactional
    public RoomDTO updateRoom(Long roomId, UpdateRoomRequest request, Long userId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with id: " + roomId));

        // TODO: Add a proper permission check. For now, anyone can update.
        // A real check might see if the user is a designated 'admin' of the room.

        room.setName(request.getName());
        Room updatedRoom = roomRepository.save(room);
        return RoomMapper.toDTO(updatedRoom);
    }

    @Override
    @Transactional
    public void leaveRoom(Long roomId, Long userId) {
        HunterRoom hunterRoom = hunterRoomRepository.findByRoomIdAndHunterId(roomId, userId)
                .orElseThrow(() -> new IllegalStateException("User is not a member of this room."));

        // This will delete the join table record, effectively removing the user.
        hunterRoomRepository.delete(hunterRoom);
    }
}