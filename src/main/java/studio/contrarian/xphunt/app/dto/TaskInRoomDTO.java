package studio.contrarian.xphunt.app.dto;

import java.util.List;

public class TaskInRoomDTO {
    private Long id;
    private String description;
    private int xp;

    private boolean createdByUser;
    private boolean claimedByUser;

    private List<TaskEventDTO> eventsByUserInRoom;

    // Getters and Setters
}