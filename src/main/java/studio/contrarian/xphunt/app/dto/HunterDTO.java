package studio.contrarian.xphunt.app.dto;

import java.util.List;

public class HunterDTO {
    private Long id;
    private String name;
    private int totalXp;
    private List<RoomMembershipDTO> memberships;
    private List<TaskSimpleDTO> claimedTasks;

    public HunterDTO(Long id, String name, int totalXp, List<RoomMembershipDTO> memberships, List<TaskSimpleDTO> claimedTasks) {
        this.id = id;
        this.name = name;
        this.totalXp = totalXp;
        this.memberships = memberships;
        this.claimedTasks = claimedTasks;
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

    public int getTotalXp() {
        return totalXp;
    }

    public void setTotalXp(int totalXp) {
        this.totalXp = totalXp;
    }

    public List<RoomMembershipDTO> getMemberships() {
        return memberships;
    }

    public void setMemberships(List<RoomMembershipDTO> memberships) {
        this.memberships = memberships;
    }

    public List<TaskSimpleDTO> getClaimedTasks() {
        return claimedTasks;
    }

    public void setClaimedTasks(List<TaskSimpleDTO> claimedTasks) {
        this.claimedTasks = claimedTasks;
    }
}
