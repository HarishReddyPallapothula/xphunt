package studio.contrarian.xphunt.app.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class TaskEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EventType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hunter_id")
    private Hunter hunter; // who performed the event

    private LocalDateTime timestamp;
    private String notes;

    /** Public no-arg constructor for JPA */
    public TaskEvent() {}

    /** Private constructor for the Builder */
    private TaskEvent(TaskEventBuilder builder) {
        this.type = builder.type;
        this.task = builder.task;
        this.hunter = builder.hunter;
        this.timestamp = builder.timestamp;
        this.notes = builder.notes;
    }

    // --- Builder Entry Point ---
    public static TaskEventBuilder builder() {
        return new TaskEventBuilder();
    }

    // --- The Static Nested Builder Class ---
    public static class TaskEventBuilder {
        private EventType type;
        private Task task;
        private Hunter hunter;
        private LocalDateTime timestamp;
        private String notes;

        public TaskEventBuilder type(EventType type) {
            this.type = type;
            return this;
        }

        public TaskEventBuilder task(Task task) {
            this.task = task;
            return this;
        }

        public TaskEventBuilder hunter(Hunter hunter) {
            this.hunter = hunter;
            return this;
        }

        public TaskEventBuilder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public TaskEventBuilder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public TaskEvent build() {
            // You can add validation here, e.g., to ensure 'task' is not null
            Objects.requireNonNull(task, "Task cannot be null for a TaskEvent");
            Objects.requireNonNull(type, "EventType cannot be null for a TaskEvent");
            return new TaskEvent(this);
        }
    }

    // --- Getters, Setters, etc. ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public EventType getType() { return type; }
    public void setType(EventType type) { this.type = type; }
    public Task getTask() { return task; }
    public void setTask(Task task) { this.task = task; }
    public Hunter getHunter() { return hunter; }
    public void setHunter(Hunter hunter) { this.hunter = hunter; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}