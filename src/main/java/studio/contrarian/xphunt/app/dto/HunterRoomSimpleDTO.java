package studio.contrarian.xphunt.app.dto;


/**
 * Represents a simplified view of a Hunter specifically for display within a Room's member list.
 * It includes the XP earned by the hunter only in that particular room.
 */

public class HunterRoomSimpleDTO {
    private Long hunterId;
    private String hunterName;
    private int xpInRoom;

    public HunterRoomSimpleDTO(Long hunterId, String hunterName, int xpInRoom) {
        this.hunterId = hunterId;
        this.hunterName = hunterName;
        this.xpInRoom = xpInRoom;
    }

    public Long getHunterId() {
        return hunterId;
    }

    public void setHunterId(Long hunterId) {
        this.hunterId = hunterId;
    }

    public String getHunterName() {
        return hunterName;
    }

    public void setHunterName(String hunterName) {
        this.hunterName = hunterName;
    }

    public int getXpInRoom() {
        return xpInRoom;
    }

    public void setXpInRoom(int xpInRoom) {
        this.xpInRoom = xpInRoom;
    }
}