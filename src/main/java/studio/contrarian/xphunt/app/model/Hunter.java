package studio.contrarian.xphunt.app.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
public class Hunter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int totalXp;



    private String password;

    @OneToMany(mappedBy = "hunter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<HunterRoom> hunterRooms = new HashSet<>();

    @OneToMany(mappedBy = "claimedBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Task> claimedTasks = new HashSet<>();

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Task> createdTasks = new HashSet<>();

    public Hunter() {}

    private Hunter(HunterBuilder builder) {
        this.name = builder.name;
        this.totalXp = builder.totalXp;
        this.password = builder.password;
        if (builder.claimedTasks != null) {
            this.claimedTasks = builder.claimedTasks;
        }
        if (builder.createdTasks != null) {
            this.createdTasks = builder.createdTasks;
        }
        if (builder.hunterRooms != null) {
            this.hunterRooms = builder.hunterRooms;
        }
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public static HunterBuilder builder() {
        return new HunterBuilder();
    }

    public static class HunterBuilder {
        private String name;
        private String password;
        private int totalXp;
        private Set<Task> claimedTasks = new HashSet<>();
        private Set<HunterRoom> hunterRooms = new HashSet<>();
        private Set<Task> createdTasks = new HashSet<>();


        public HunterBuilder name(String name) {
            this.name = name;
            return this;
        }

        public HunterBuilder password(String password) {
            this.password = password;
            return this;
        }

        public HunterBuilder totalXp(int totalXp) {
            this.totalXp = totalXp;
            return this;
        }

        public HunterBuilder claimedTasks(Set<Task> claimedTasks) {
            this.claimedTasks = claimedTasks;
            return this;
        }

        public HunterBuilder createdTasks(Set<Task> createdTasks) {
            this.createdTasks = createdTasks;
            return this;
        }

        public HunterBuilder hunterRooms(Set<HunterRoom> hunterRooms) {
            this.hunterRooms = hunterRooms;
            return this;
        }

        public Hunter build() {
            return new Hunter(this);
        }
    }

    public Set<HunterRoom> getHunterRooms() {
        return hunterRooms;
    }

    public void setHunterRooms(Set<HunterRoom> hunterRooms) {
        this.hunterRooms = hunterRooms;
    }

    public Set<Task> getClaimedTasks() {
        return claimedTasks;
    }

    public void setClaimedTasks(Set<Task> claimedTasks) {
        this.claimedTasks = claimedTasks;
    }

    public Set<Task> getCreatedTasks() {
        return createdTasks;
    }

    public void setCreatedTasks(Set<Task> createdTasks) {
        this.createdTasks = createdTasks;
    }

    // Getters, setters, equals, hashCode, toString...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getTotalXp() { return totalXp; }
    public void setTotalXp(int totalXp) { this.totalXp = totalXp; }
    // etc.
}