package studio.contrarian.xphunt.app.dto;

import studio.contrarian.xphunt.app.model.TaskStatusType;

public class TaskSimpleDTO {
    private Long id;
    private String description;
    private int xp;
    private TaskStatusType status;

    public TaskSimpleDTO(Long id, String description, int xp, TaskStatusType status) {
        this.id = id;
        this.description = description;
        this.xp = xp;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public TaskStatusType getStatus() {
        return status;
    }

    public void setStatus(TaskStatusType status) {
        this.status = status;
    }
}
