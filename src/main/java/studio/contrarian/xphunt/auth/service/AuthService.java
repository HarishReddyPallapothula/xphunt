package studio.contrarian.xphunt.auth.service;

import studio.contrarian.xphunt.app.dto.HunterDTO;
import studio.contrarian.xphunt.auth.dto.LoginRequest;
import studio.contrarian.xphunt.auth.dto.LoginResponse;
import studio.contrarian.xphunt.auth.dto.RegisterRequest;

public interface AuthService {
    HunterDTO register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}