package studio.contrarian.xphunt.app.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private int xp;
    private TaskStatusType status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "claimed_by_id")
    private Hunter claimedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id", nullable = false)
    private Hunter createdBy;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskEvent> events = new ArrayList<>();

    /**
     * Public no-arg constructor required by JPA.
     * It's not intended for general use.
     */
    public Task() {
    }

    /**
     * Private constructor to be used exclusively by the Builder.
     */
    private Task(TaskBuilder builder) {
        this.description = builder.description;
        this.xp = builder.xp;
        this.status = builder.status;
        this.room = builder.room;
        this.claimedBy = builder.claimedBy;
        this.createdBy = builder.createdBy;
        if (builder.events != null) {
            this.events = builder.events;
        }
    }

    // --- Static method to get the builder instance ---
    public static TaskBuilder builder() {
        return new TaskBuilder();
    }

    // --- The Static Nested Builder Class ---
    public static class TaskBuilder {
        private String description;
        private int xp;
        private TaskStatusType status = TaskStatusType.UNASSIGNED; // Default value
        private Room room;
        private Hunter claimedBy;
        private Hunter createdBy;
        private List<TaskEvent> events = new ArrayList<>();

        // Fluent setters that return the builder for chaining
        public TaskBuilder description(String description) {
            this.description = description;
            return this;
        }

        public TaskBuilder xp(int xp) {
            this.xp = xp;
            return this;
        }

        public TaskBuilder status(TaskStatusType status) {
            this.status = status;
            return this;
        }

        public TaskBuilder room(Room room) {
            this.room = room;
            return this;
        }

        public TaskBuilder claimedBy(Hunter claimedBy) {
            this.claimedBy = claimedBy;
            return this;
        }

        public TaskBuilder createdBy(Hunter createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public TaskBuilder events(List<TaskEvent> events) {
            this.events = events;
            return this;
        }

        // The final build method that creates the Task instance
        public Task build() {
            return new Task(this);
        }
    }

    // --- Standard Getters and Setters ---
    // (You would generate these with your IDE)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getXp() { return xp; }
    public void setXp(int xp) { this.xp = xp; }

    public TaskStatusType getStatus() {
        return status;
    }

    public void setStatus(TaskStatusType status) {
        this.status = status;
    }
    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }
    public Hunter getClaimedBy() { return claimedBy; }
    public void setClaimedBy(Hunter claimedBy) { this.claimedBy = claimedBy; }
    public Hunter getCreatedBy() { return createdBy; }
    public void setCreatedBy(Hunter createdBy) { this.createdBy = createdBy; }
    public List<TaskEvent> getEvents() { return events; }
    public void setEvents(List<TaskEvent> events) { this.events = events; }

    // --- equals, hashCode, and toString ---
    // Be careful with bi-directional relationships to avoid StackOverflowError
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id != null && id.equals(task.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode(); // Use a constant for entities
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", xp=" + xp +
                ", status=" + status +
                ", room=" + room +
                ", claimedBy=" + claimedBy +
                ", createdBy=" + createdBy +
                ", events=" + events +
                '}';
    }
}

