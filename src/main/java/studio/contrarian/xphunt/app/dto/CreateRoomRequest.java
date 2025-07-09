package studio.contrarian.xphunt.app.dto;// CreateRoomRequest.java (New DTO)


public class CreateRoomRequest {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CreateRoomRequest(String name) {
        this.name = name;
    }
}