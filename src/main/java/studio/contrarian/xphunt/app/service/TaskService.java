package studio.contrarian.xphunt.app.service;

import studio.contrarian.xphunt.app.dto.TaskDTO;

public interface TaskService {
    TaskDTO getTaskById(Long taskId, Long hunterId);
    TaskDTO claimTask(Long taskId, Long hunterId);
    TaskDTO completeTask(Long taskId, Long hunterId);
    void deleteTask(Long taskId, Long hunterId);
    // You could also add an updateTask method here
}