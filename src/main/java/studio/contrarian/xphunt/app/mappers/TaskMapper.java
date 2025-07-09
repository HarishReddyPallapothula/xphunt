package studio.contrarian.xphunt.app.mappers;

import studio.contrarian.xphunt.app.dto.TaskDTO;
import studio.contrarian.xphunt.app.dto.TaskSimpleDTO;
import studio.contrarian.xphunt.app.model.Task;

import java.util.List;
import java.util.stream.Collectors;

public class TaskMapper {

    public static TaskSimpleDTO toSimple(Task task) {
        return new TaskSimpleDTO(task.getId(), task.getDescription(), task.getXp());
    }

    public static TaskDTO toDTO(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getDescription(),
                task.getXp(),
                RoomMapper.toSimple(task.getRoom()),
                task.getClaimedBy() != null ? HunterMapper.toSimple(task.getClaimedBy()) : null,
                task.getCreatedBy() != null ? HunterMapper.toSimple(task.getCreatedBy()) : null,
                task.getEvents() != null
                        ? task.getEvents().stream()
                        .map(TaskEventMapper::toDTO)
                        .collect(Collectors.toList())
                        : List.of()
        );
    }
}

