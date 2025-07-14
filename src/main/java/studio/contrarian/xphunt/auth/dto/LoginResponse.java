package studio.contrarian.xphunt.auth.dto;


import studio.contrarian.xphunt.app.dto.HunterDTO;

public class LoginResponse {
    private String token;
    private HunterDTO user;

    public LoginResponse(String token, HunterDTO user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public HunterDTO getUser() {
        return user;
    }

    public void setUser(HunterDTO user) {
        this.user = user;
    }
}