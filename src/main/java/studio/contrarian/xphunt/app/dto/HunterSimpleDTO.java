package studio.contrarian.xphunt.app.dto;

public class HunterSimpleDTO {
    private Long id;
    private String name;
    private int totalXp;

    public HunterSimpleDTO(Long id, String name, int totalXp) {
        this.id = id;
        this.name = name;
        this.totalXp = totalXp;
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
}
