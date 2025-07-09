package studio.contrarian.xphunt.app.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String inviteCode;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<HunterRoom> hunterRooms = new HashSet<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Task> tasks = new HashSet<>();

    public Room() {}

    private Room(RoomBuilder builder) {
        this.name = builder.name;
        this.inviteCode = builder.inviteCode;
        if (builder.hunterRooms != null) this.hunterRooms = builder.hunterRooms;
        if (builder.tasks != null) this.tasks = builder.tasks;
    }

    public static RoomBuilder builder() {
        return new RoomBuilder();
    }

    public static class RoomBuilder {
        private String name;
        private String inviteCode;
        private Set<HunterRoom> hunterRooms = new HashSet<>();
        private Set<Task> tasks = new HashSet<>();

        public RoomBuilder name(String name) {
            this.name = name;
            return this;
        }

        public RoomBuilder inviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
            return this;
        }

        public RoomBuilder hunterRooms(Set<HunterRoom> hunterRooms) {
            this.hunterRooms = hunterRooms;
            return this;
        }

        public RoomBuilder tasks(Set<Task> tasks) {
            this.tasks = tasks;
            return this;
        }

        public Room build() {
            return new Room(this);
        }
    }

    // Getters, setters, equals, hashCode, toString...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getInviteCode() { return inviteCode; }
    public void setInviteCode(String inviteCode) { this.inviteCode = inviteCode; }
    public Set<HunterRoom> getHunterRooms() { return hunterRooms; }
    public void setHunterRooms(Set<HunterRoom> hunterRooms) { this.hunterRooms = hunterRooms; }
    public Set<Task> getTasks() { return tasks; }
    public void setTasks(Set<Task> tasks) { this.tasks = tasks; }

    // etc.
}