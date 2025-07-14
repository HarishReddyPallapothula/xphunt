package studio.contrarian.xphunt.app.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import studio.contrarian.xphunt.app.dto.HunterDTO;
import studio.contrarian.xphunt.app.dto.UpdateHunterRequest;
import studio.contrarian.xphunt.app.service.HunterService;
import studio.contrarian.xphunt.auth.model.CustomUserDetails;
import studio.contrarian.xphunt.exception.ErrorResponse;

@RestController
@RequestMapping("/api/hunters")
@Tag(name = "Hunter API", description = "Endpoints for retrieving and managing hunter profiles.")
@SecurityRequirement(name = "bearerAuth")
public class HunterController {

    private final HunterService hunterService;

    public HunterController(HunterService hunterService) {
        this.hunterService = hunterService;
    }

    @GetMapping("/me")
    @Operation(summary = "Get current hunter's profile", description = "Retrieves the full profile details for the currently authenticated hunter.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved hunter profile",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HunterDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT is missing or invalid",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not Found - Authenticated user's profile could not be found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<HunterDTO> getMyProfile( @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails currentUser) {
        Long hunterId = currentUser.getId();
        HunterDTO hunterDetails = hunterService.getHunterDetails(hunterId);
        return ResponseEntity.ok(hunterDetails);
    }

    /*@GetMapping("/{id}")
    public ResponseEntity<HunterDTO> getHunterById(@PathVariable Long id) {
        HunterDTO hunterDetails = hunterService.getHunterDetails(id);
        return ResponseEntity.ok(hunterDetails);
    }*/

    @PutMapping("/me")
    @Operation(summary = "Update current hunter's profile", description = "Allows the authenticated hunter to update parts of their profile.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HunterDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid data provided in the request body",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT is missing or invalid",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<HunterDTO> updateMyProfile(@RequestBody UpdateHunterRequest request, @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetails currentUser) {
        Long hunterId = currentUser.getId();
        HunterDTO updatedHunter = hunterService.updateHunter(hunterId, request);
        return ResponseEntity.ok(updatedHunter);
    }
}