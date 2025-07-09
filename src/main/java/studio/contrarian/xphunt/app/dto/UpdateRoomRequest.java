package studio.contrarian.xphunt.app.dto;


public class UpdateRoomRequest {
    private String name;

    public UpdateRoomRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}