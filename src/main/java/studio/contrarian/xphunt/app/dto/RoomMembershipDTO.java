package studio.contrarian.xphunt.app.dto;


public class RoomMembershipDTO {
    private Long roomId;
    private String roomName;
    private int xpInRoom;

    public RoomMembershipDTO(Long roomId, String roomName, int xpInRoom) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.xpInRoom = xpInRoom;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getXpInRoom() {
        return xpInRoom;
    }

    public void setXpInRoom(int xpInRoom) {
        this.xpInRoom = xpInRoom;
    }
}