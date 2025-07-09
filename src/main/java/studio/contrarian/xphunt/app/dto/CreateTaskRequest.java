package studio.contrarian.xphunt.app.dto;// CreateTaskRequest.java (New DTO)


public class CreateTaskRequest {
    private String description;
    private int xp;

    public CreateTaskRequest(String description, int xp) {
        this.description = description;
        this.xp = xp;
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