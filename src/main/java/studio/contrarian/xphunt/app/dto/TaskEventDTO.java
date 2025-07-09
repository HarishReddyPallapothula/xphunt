package studio.contrarian.xphunt.app.dto;

import java.time.LocalDateTime;

public class TaskEventDTO {
    private Long id;
    private String type;
    private LocalDateTime timestamp;
    private String notes;
    private HunterSimpleDTO hunter;

    public TaskEventDTO(Long id, String type, LocalDateTime timestamp, String notes, HunterSimpleDTO hunter) {
        this.id = id;
        this.type = type;
        this.timestamp = timestamp;
        this.notes = notes;
        this.hunter = hunter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public HunterSimpleDTO getUser() {
        return hunter;
    }

    public void setUser(HunterSimpleDTO hunter) {
        this.hunter = hunter;
    }
}
