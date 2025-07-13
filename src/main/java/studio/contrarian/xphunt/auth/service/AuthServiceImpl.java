package studio.contrarian.xphunt.auth.service;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import studio.contrarian.xphunt.app.dto.HunterDTO;
import studio.contrarian.xphunt.app.mappers.HunterMapper;
import studio.contrarian.xphunt.app.model.Hunter;
import studio.contrarian.xphunt.app.repo.HunterRepository;
import studio.contrarian.xphunt.auth.dto.LoginRequest;
import studio.contrarian.xphunt.auth.dto.LoginResponse;
import studio.contrarian.xphunt.auth.dto.RegisterRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import studio.contrarian.xphunt.auth.filter.JwtAuthenticationFilter;
import studio.contrarian.xphunt.auth.model.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;


@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final HunterRepository hunterRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public AuthServiceImpl(HunterRepository hunterRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.hunterRepository = hunterRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public LoginResponse register(RegisterRequest request) { // CHANGED return type
        if (hunterRepository.findByName(request.getName()).isPresent()) {
            throw new IllegalStateException("Hunter with that name already exists.");
        }

        Hunter hunter = Hunter.builder()
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword())) // Hash the password!
                .totalXp(0)
                .build();

        hunterRepository.save(hunter);

        // --- NEW LOGIC ---
        // After successfully registering the user, automatically log them in
        // by calling our existing login method. This is great for code reuse!
        logger.info("Hunter '{}' registered successfully. Proceeding to login.", request.getName());
        LoginRequest loginRequest = new LoginRequest(request.getName(), request.getPassword());
        return this.login(loginRequest);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        logger.info("Authenticating hunter: {}", request.getName());
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                request.getName(),
                request.getPassword()
        );

        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        logger.info("JWT generated for hunter: {}", request.getName());

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        HunterDTO hunterDTO = hunterRepository.findById(userDetails.getId())
                .map(HunterMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Logged in user not found with ID: " + userDetails.getId()));

        return new LoginResponse(token, hunterDTO);
    }
}