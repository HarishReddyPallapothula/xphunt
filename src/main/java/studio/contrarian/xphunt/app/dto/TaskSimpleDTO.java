package studio.contrarian.xphunt.app.dto;

public class TaskSimpleDTO {
    private Long id;
    private String description;
    private int xp;

    public TaskSimpleDTO(Long id, String description, int xp) {
        this.id = id;
        this.description = description;
        this.xp = xp;
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
}
