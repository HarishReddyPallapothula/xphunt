package studio.contrarian.xphunt.app.service;

import org.springframework.stereotype.Service;
import studio.contrarian.xphunt.exception.ForbiddenException;
import studio.contrarian.xphunt.app.repo.HunterRoomRepository;
import studio.contrarian.xphunt.app.repo.TaskRepository;
import studio.contrarian.xphunt.app.model.Task;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PermissionServiceImpl implements PermissionService{

    private final HunterRoomRepository hunterRoomRepository;
    private final TaskRepository taskRepository;

    public PermissionServiceImpl(HunterRoomRepository hunterRoomRepository, TaskRepository taskRepository) {
        this.hunterRoomRepository = hunterRoomRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void verifyHunterIsMemberOfRoom(Long roomId, Long hunterId) {
        if (hunterId == null || roomId == null) {
            throw new ForbiddenException("Access Denied: Insufficient information for permission check.");
        }

        hunterRoomRepository.findByRoomIdAndHunterId(roomId, hunterId)
                .orElseThrow(() -> new ForbiddenException("Access Denied: You are not a member of this room."));
    }

    @Override
    public void verifyHunterCanAccessTask(Long taskId, Long hunterId) {
        if (hunterId == null || taskId == null) {
            throw new ForbiddenException("Access Denied: Insufficient information for permission check.");
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));

        verifyHunterIsMemberOfRoom( task.getRoom().getId(), hunterId);

    }

    @Override
    public void verifyHunterIsTaskCreator(Long hunterId, Long taskId) {
        if (hunterId == null || taskId == null) {
            throw new ForbiddenException("Access Denied: Insufficient information for permission check.");
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));

        if (!task.getCreatedBy().getId().equals(hunterId)) {
            throw new ForbiddenException("Access Denied: You are not the creator of this task.");
        }
    }
}