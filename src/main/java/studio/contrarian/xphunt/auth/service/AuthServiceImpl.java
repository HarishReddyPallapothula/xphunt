package studio.contrarian.xphunt.auth.service;

import jakarta.persistence.EntityNotFoundException;
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
import studio.contrarian.xphunt.auth.model.CustomUserDetails;


@Service

public class AuthServiceImpl implements AuthService {

    private final HunterRepository hunterRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;// Inject the encoder

    public AuthServiceImpl(HunterRepository hunterRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.hunterRepository = hunterRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public HunterDTO register(RegisterRequest request) {
        if (hunterRepository.findByName(request.getName()).isPresent()) {
            throw new IllegalStateException("Hunter with that name already exists.");
        }

        Hunter hunter = Hunter.builder()
            .name(request.getName())
            .password(passwordEncoder.encode(request.getPassword())) // Hash the password!
            .totalXp(0)
            .build();
        
        Hunter savedHunter = hunterRepository.save(hunter);
        return HunterMapper.toDTO(savedHunter);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        // 1. Create an authentication token with the user's raw credentials.
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                request.getName(),
                request.getPassword()
        );

        // 2. Use the AuthenticationManager to validate the credentials.
        //    This is where Spring Security does its magic (calls UserDetailsService, checks password).
        Authentication authentication = authenticationManager.authenticate(authToken);

        // 3. If successful, set the Authentication in the SecurityContext.
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 4. GENERATE THE REAL JWT by calling your token provider.
        String token = tokenProvider.createToken(authentication);

        // 5. Get the user details from the authenticated principal object.
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        HunterDTO hunterDTO = hunterRepository.findById(userDetails.getId())
                .map(HunterMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Logged in user not found with ID: " + userDetails.getId()));

        // 6. Return the response with the REAL token.
        return new LoginResponse(token, hunterDTO);
    }
}