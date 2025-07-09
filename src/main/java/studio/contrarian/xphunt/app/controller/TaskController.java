package studio.contrarian.xphunt.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import studio.contrarian.xphunt.app.dto.TaskDTO;
import studio.contrarian.xphunt.app.service.TaskService;
import studio.contrarian.xphunt.auth.model.CustomUserDetails;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    private final TaskService taskService;


    /**
     * GET /api/tasks/{taskId}
     * Get the full details of a single task, including its event history.
     */
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long taskId, @AuthenticationPrincipal CustomUserDetails currentUser) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }

    /**
     * POST /api/tasks/{taskId}/claim
     * Allows the current user to claim an unclaimed task.
     */
    @PostMapping("/{taskId}/claim")
    public ResponseEntity<TaskDTO> claimTask(@PathVariable Long taskId,
                                             @AuthenticationPrincipal CustomUserDetails currentUser) {
        Long hunterId = currentUser.getId();
        TaskDTO claimedTask = taskService.claimTask(taskId, hunterId);
        return ResponseEntity.ok(claimedTask);
    }
    
    /**
     * POST /api/tasks/{taskId}/complete
     * Allows the claiming user to complete a task and receive XP.
     */
    @PostMapping("/{taskId}/complete")
    public ResponseEntity<TaskDTO> completeTask(@PathVariable Long taskId,
                                                @AuthenticationPrincipal CustomUserDetails currentUser) {
        Long hunterId = currentUser.getId();
        TaskDTO completedTask = taskService.completeTask(taskId, hunterId);
        return ResponseEntity.ok(completedTask);
    }

    /**
     * DELETE /api/tasks/{taskId}
     * Deletes a task. Only the creator can do this.
     */
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId,
                                           @AuthenticationPrincipal CustomUserDetails currentUser) {
        Long hunterId = currentUser.getId();
        taskService.deleteTask(taskId, hunterId);
        return ResponseEntity.noContent().build();
    }
}