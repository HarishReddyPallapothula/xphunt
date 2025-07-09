package studio.contrarian.xphunt.app.dto;

import java.util.List;

public class HunterInRoomDTO {
    private Long id;
    private String name;
    private int xpInRoom; // XP specifically for the room
    private List<TaskSimpleDTO> claimedTasksInRoom;

    public HunterInRoomDTO(Long id, String name, int xpInRoom, List<TaskSimpleDTO> claimedTasksInRoom) {
        this.id = id;
        this.name = name;
        this.xpInRoom = xpInRoom;
        this.claimedTasksInRoom = claimedTasksInRoom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getXpInRoom() {
        return xpInRoom;
    }

    public void setXpInRoom(int xpInRoom) {
        this.xpInRoom = xpInRoom;
    }

    public List<TaskSimpleDTO> getClaimedTasksInRoom() {
        return claimedTasksInRoom;
    }

    public void setClaimedTasksInRoom(List<TaskSimpleDTO> claimedTasksInRoom) {
        this.claimedTasksInRoom = claimedTasksInRoom;
    }
}
