package studio.contrarian.xphunt.app.service;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import studio.contrarian.xphunt.app.dto.TaskDTO;
import studio.contrarian.xphunt.app.mappers.TaskMapper;
import studio.contrarian.xphunt.app.model.*;
import studio.contrarian.xphunt.app.repo.HunterRepository;
import studio.contrarian.xphunt.app.repo.HunterRoomRepository;
import studio.contrarian.xphunt.app.repo.TaskRepository;


import java.time.LocalDateTime;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final HunterRepository hunterRepository;
    private final HunterRoomRepository hunterRoomRepository;

    public TaskServiceImpl(TaskRepository taskRepository, HunterRepository hunterRepository, HunterRoomRepository hunterRoomRepository) {
        this.taskRepository = taskRepository;
        this.hunterRepository = hunterRepository;
        this.hunterRoomRepository = hunterRoomRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public TaskDTO getTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));
        return TaskMapper.toDTO(task);
    }

    @Override
    @Transactional
    public TaskDTO claimTask(Long taskId, Long hunterId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));

        if (task.getClaimedBy() != null) {
            throw new IllegalStateException("Task is already claimed.");
        }

        Hunter claimer = hunterRepository.findById(hunterId)
                .orElseThrow(() -> new EntityNotFoundException("Hunter not found with id: " + hunterId));

        // Ensure the claimer is a member of the task's room
        hunterRoomRepository.findByRoomIdAndHunterId(task.getRoom().getId(), hunterId)
                .orElseThrow(() -> new IllegalStateException("You must be a member of the room to claim this task."));
        
        task.setClaimedBy(claimer);
        
        // Create event
        TaskEvent event = TaskEvent.builder()
            .task(task)
            .hunter(claimer)
            .type(EventType.CLAIMED)
            .timestamp(LocalDateTime.now())
            .notes("Task claimed.")
            .build();
        task.getEvents().add(event);
        
        return TaskMapper.toDTO(taskRepository.save(task));
    }

    @Override
    @Transactional
    public TaskDTO completeTask(Long taskId, Long hunterId) {
        // This is the same logic we had in RoomServiceImpl before
        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));
        
        if (task.isCompleted()) throw new IllegalStateException("Task is already completed.");
        if (task.getClaimedBy() == null || !task.getClaimedBy().getId().equals(hunterId)) {
            throw new IllegalStateException("Task must be claimed by you to be completed.");
        }
        
        task.setCompleted(true);
        
        Hunter completer = task.getClaimedBy();
        HunterRoom hunterRoom = hunterRoomRepository.findByRoomIdAndHunterId(task.getRoom().getId(), hunterId)
            .orElseThrow(() -> new IllegalStateException("Hunter is not a member of the room. This should not happen."));
        
        // Award XP
        hunterRoom.setXpInRoom(hunterRoom.getXpInRoom() + task.getXp());
        completer.setTotalXp(completer.getTotalXp() + task.getXp());

        // Create event
        TaskEvent event = TaskEvent.builder()
            .task(task)
            .hunter(completer)
            .type(EventType.COMPLETED)
            .timestamp(LocalDateTime.now())
            .notes("Task completed. " + task.getXp() + " XP awarded.")
            .build();
        task.getEvents().add(event);
        
        return TaskMapper.toDTO(taskRepository.save(task));
    }

    @Override
    @Transactional
    public void deleteTask(Long taskId, Long hunterId) {
        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));
        
        // Permission check: Only the person who created the task can delete it.
        if (!task.getCreatedBy().getId().equals(hunterId)) {
            throw new IllegalStateException("You do not have permission to delete this task.");
        }

        taskRepository.delete(task);
    }
}