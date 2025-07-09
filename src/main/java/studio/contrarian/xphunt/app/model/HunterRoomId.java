package studio.contrarian.xphunt.app.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class HunterRoomId implements Serializable {
    private Long hunterId;
    private Long roomId;

    public HunterRoomId() {
    }

    public HunterRoomId(Long hunterId, Long roomId) {
        this.hunterId = hunterId;
        this.roomId = roomId;
    }

    public Long getHunterId() {
        return hunterId;
    }

    public void setHunterId(Long hunterId) {
        this.hunterId = hunterId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HunterRoomId that = (HunterRoomId) o;
        return Objects.equals(hunterId, that.hunterId) && Objects.equals(roomId, that.roomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hunterId, roomId);
    }
    // equals() and hashCode() required!
}