package studio.contrarian.xphunt.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import studio.contrarian.xphunt.app.dto.*;
import studio.contrarian.xphunt.app.service.RoomService;
import studio.contrarian.xphunt.auth.model.CustomUserDetails;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    private final RoomService roomService;


    /**
     * POST /api/rooms
     * Create a new room. The creator is automatically added.
     */
    @PostMapping("/create")
    public ResponseEntity<RoomDTO> createRoom(@RequestBody CreateRoomRequest request, @AuthenticationPrincipal CustomUserDetails currentUser) {
        Long creatorId = currentUser.getId();
        RoomDTO createdRoom = roomService.createRoom(request, creatorId);
        return new ResponseEntity<>(createdRoom, HttpStatus.CREATED);
    }

    /**
     * POST /api/rooms/join?inviteCode=...
     * Join an existing room using an invite code.
     */
    @PostMapping("/join")
    public ResponseEntity<RoomDTO> joinRoom(@RequestParam String inviteCode, @AuthenticationPrincipal CustomUserDetails currentUser) {
        Long hunterId = currentUser.getId();
        RoomDTO joinedRoom = roomService.joinRoom(inviteCode, hunterId);
        return ResponseEntity.ok(joinedRoom);
    }

    @PostMapping("/{roomId}/tasks/create")
    public ResponseEntity<TaskDTO> addTaskToRoom(@PathVariable Long roomId, @RequestBody CreateTaskRequest request, @AuthenticationPrincipal CustomUserDetails currentUser) {
        Long creatorId = currentUser.getId();
        TaskDTO newTask = roomService.addTaskToRoom(roomId, request, creatorId);
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }

    @GetMapping("/{roomId}/tasks/getAllTasks")
    public ResponseEntity<RoomDTO> getAllTasks(@PathVariable Long roomId, @AuthenticationPrincipal CustomUserDetails currentUser) {
        Long creatorId = currentUser.getId();
        RoomDTO task = roomService.getRoomById(roomId);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }


    @GetMapping("/{roomId}/events")
    public ResponseEntity<List<TaskEventDTO>> getRoomEvents(@PathVariable Long roomId) {
        List<TaskEventDTO> events = roomService.getRoomEvents(roomId);
        return ResponseEntity.ok(events);
    }


    @GetMapping("/{roomId}/leaderboard")
    public ResponseEntity<List<HunterRoomSimpleDTO>> getRoomLeaderboard(@PathVariable Long roomId) {
        List<HunterRoomSimpleDTO> leaderboard = roomService.getRoomLeaderboard(roomId);
        return ResponseEntity.ok(leaderboard);
    }
}