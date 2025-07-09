package studio.contrarian.xphunt.app.dto;


public class UpdateHunterRequest {
    private String name;

    public String getName() {
        return name;
    }

    public UpdateHunterRequest(String name) {
        this.name = name;
    }
// You could add other updatable fields here, like a bio or profile picture URL
}