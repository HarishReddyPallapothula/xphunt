package studio.contrarian.xphunt.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import studio.contrarian.xphunt.app.dto.HunterDTO;
import studio.contrarian.xphunt.auth.dto.LoginRequest;
import studio.contrarian.xphunt.auth.dto.LoginResponse;
import studio.contrarian.xphunt.auth.dto.RegisterRequest;
import studio.contrarian.xphunt.auth.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<HunterDTO> register(@RequestBody RegisterRequest request) {
        HunterDTO newUser = authService.register(request);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}