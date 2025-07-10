package studio.contrarian.xphunt.app.dto;

import studio.contrarian.xphunt.app.model.TaskStatusType;

import java.util.List;

public class TaskDTO {
    private Long id;
    private String description;
    private int xp;
    private TaskStatusType status;
    private RoomSimpleDTO room;
    private HunterSimpleDTO claimedBy;
    private HunterSimpleDTO createdBy;
    private List<TaskEventDTO> events;

    public TaskDTO(Long id, String description, int xp, RoomSimpleDTO room, HunterSimpleDTO claimedBy, HunterSimpleDTO createdBy, List<TaskEventDTO> events, TaskStatusType status) {
        this.id = id;
        this.description = description;
        this.xp = xp;
        this.room = room;
        this.claimedBy = claimedBy;
        this.createdBy = createdBy;
        this.events = events;
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

    public RoomSimpleDTO getRoom() {
        return room;
    }

    public void setRoom(RoomSimpleDTO room) {
        this.room = room;
    }

    public HunterSimpleDTO getClaimedBy() {
        return claimedBy;
    }

    public void setClaimedBy(HunterSimpleDTO claimedBy) {
        this.claimedBy = claimedBy;
    }

    public HunterSimpleDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(HunterSimpleDTO createdBy) {
        this.createdBy = createdBy;
    }

    public List<TaskEventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<TaskEventDTO> events) {
        this.events = events;
    }
}
