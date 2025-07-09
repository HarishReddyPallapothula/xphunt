package studio.contrarian.xphunt.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import studio.contrarian.xphunt.app.model.Task;
import studio.contrarian.xphunt.app.model.TaskEvent;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // A query to get all events in a room, ordered by time
    @Query("SELECT te FROM TaskEvent te WHERE te.task.room.id = :roomId ORDER BY te.timestamp DESC")
    List<TaskEvent> findEventsByRoomId(Long roomId);
}
