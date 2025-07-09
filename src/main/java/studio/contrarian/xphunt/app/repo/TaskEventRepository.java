package studio.contrarian.xphunt.app.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import studio.contrarian.xphunt.app.model.TaskEvent;

import java.util.List;

public interface TaskEventRepository extends JpaRepository<TaskEvent, Long> {
    List<TaskEvent> findByTask_Id(Long taskId);
}

