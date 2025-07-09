package studio.contrarian.xphunt.app.mappers;

import studio.contrarian.xphunt.app.dto.TaskEventDTO;
import studio.contrarian.xphunt.app.model.TaskEvent;

public class TaskEventMapper {

    public static TaskEventDTO toDTO(TaskEvent event) {
        return new TaskEventDTO(
            event.getId(),
            event.getType().name(),
            event.getTimestamp(),
            event.getNotes(),
            event.getHunter() != null ? HunterMapper.toSimple(event.getHunter()) : null
        );
    }
}
