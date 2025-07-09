package studio.contrarian.xphunt.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import studio.contrarian.xphunt.app.dto.HunterDTO;
import studio.contrarian.xphunt.app.dto.UpdateHunterRequest;
import studio.contrarian.xphunt.app.service.HunterService;
import studio.contrarian.xphunt.auth.model.CustomUserDetails;

@RestController
@RequestMapping("/api/hunters")
public class HunterController {

    private final HunterService hunterService;

    public HunterController(HunterService hunterService) {
        this.hunterService = hunterService;
    }

    // This is a placeholder. In a real app with Spring Security,
    // you would get the authenticated user's ID from the SecurityContext.


    /**
     * GET /api/hunters/me
     * Gets the profile of the currently authenticated user, including their rooms and XP.
     */
    @GetMapping("/me")
    public ResponseEntity<HunterDTO> getMyProfile(@AuthenticationPrincipal CustomUserDetails currentUser) {
        Long hunterId = currentUser.getId();
        HunterDTO hunterDetails = hunterService.getHunterDetails(hunterId);
        return ResponseEntity.ok(hunterDetails);
    }

    /**
     * GET /api/hunters/{id}
     * Gets the public profile of any user by their ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HunterDTO> getHunterById(@PathVariable Long id) {
        HunterDTO hunterDetails = hunterService.getHunterDetails(id);
        return ResponseEntity.ok(hunterDetails);
    }

    /**
     * PUT /api/hunters/me
     * Updates the profile of the currently authenticated user.
     */
    @PutMapping("/me")
    public ResponseEntity<HunterDTO> updateMyProfile(@RequestBody UpdateHunterRequest request, @AuthenticationPrincipal CustomUserDetails currentUser) {
        Long hunterId = currentUser.getId();
        HunterDTO updatedHunter = hunterService.updateHunter(hunterId, request);
        return ResponseEntity.ok(updatedHunter);
    }
}