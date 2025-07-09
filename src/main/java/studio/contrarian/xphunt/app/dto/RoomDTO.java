package studio.contrarian.xphunt.app.dto;

import java.util.ArrayList;
import java.util.List;

public class RoomDTO {
    private Long id;
    private String name;
    private String inviteCode;
    private List<HunterRoomSimpleDTO> hunters = new ArrayList<>();
    private List<TaskDTO> tasks = new ArrayList<>();

    public RoomDTO(Long id, String name, String inviteCode, List<HunterRoomSimpleDTO> hunters, List<TaskDTO> tasks) {
        this.id = id;
        this.name = name;
        this.inviteCode = inviteCode;
        this.hunters = hunters;
        this.tasks = tasks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public List<HunterRoomSimpleDTO> getHunters() {
        return hunters;
    }

    public void setHunters(List<HunterRoomSimpleDTO> hunters) {
        this.hunters = hunters;
    }

    public List<TaskDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }
}
