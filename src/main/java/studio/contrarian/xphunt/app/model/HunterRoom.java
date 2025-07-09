package studio.contrarian.xphunt.app.model;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
public class HunterRoom {

    @EmbeddedId
    private HunterRoomId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("hunterId") // Maps hunterId from the EmbeddedId
    @JoinColumn(name = "hunter_id")
    private Hunter hunter;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roomId") // Maps roomId from the EmbeddedId
    @JoinColumn(name = "room_id")
    private Room room;

    private int xpInRoom;

    /** Public no-arg constructor for JPA */
    public HunterRoom() {}

    /** Private constructor for the Builder */
    private HunterRoom(HunterRoomBuilder builder) {
        this.id = new HunterRoomId(builder.hunter.getId(), builder.room.getId());
        this.hunter = builder.hunter;
        this.room = builder.room;
        this.xpInRoom = builder.xpInRoom;
    }

    // --- Builder Entry Point ---
    public static HunterRoomBuilder builder() {
        return new HunterRoomBuilder();
    }

    // --- The Static Nested Builder Class ---
    public static class HunterRoomBuilder {
        private Hunter hunter;
        private Room room;
        private int xpInRoom = 0; // Default value

        public HunterRoomBuilder hunter(Hunter hunter) {
            this.hunter = hunter;
            return this;
        }

        public HunterRoomBuilder room(Room room) {
            this.room = room;
            return this;
        }

        public HunterRoomBuilder xpInRoom(int xpInRoom) {
            this.xpInRoom = xpInRoom;
            return this;
        }

        public HunterRoom build() {
            Objects.requireNonNull(hunter, "Hunter cannot be null for a HunterRoom link");
            Objects.requireNonNull(room, "Room cannot be null for a HunterRoom link");
            return new HunterRoom(this);
        }
    }

    // --- Getters, Setters, etc. ---
    public HunterRoomId getId() { return id; }
    public void setId(HunterRoomId id) { this.id = id; }
    public Hunter getHunter() { return hunter; }
    public void setHunter(Hunter hunter) { this.hunter = hunter; }
    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }
    public int getXpInRoom() { return xpInRoom; }
    public void setXpInRoom(int xpInRoom) { this.xpInRoom = xpInRoom; }
}