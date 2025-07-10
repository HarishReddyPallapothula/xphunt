package studio.contrarian.xphunt.app.service;

import studio.contrarian.xphunt.app.dto.*;


import java.util.List;

public interface RoomService {

        RoomDTO createRoom(CreateRoomRequest request, Long creatorId);


        RoomDTO joinRoom(String inviteCode, Long hunterId);

        TaskDTO addTaskToRoom(Long roomId, CreateTaskRequest request, Long creatorId);


        TaskDTO claimTask(Long roomId, Long taskId, Long hunterId);


        List<TaskEventDTO> getRoomEvents(Long roomId);


        List<HunterRoomSimpleDTO> getRoomLeaderboard(Long roomId, Long hunterId);

        RoomDTO getRoomById(Long roomId, Long hunterId);
        RoomDTO updateRoom(Long roomId, UpdateRoomRequest request, Long userId);
        void leaveRoom(Long roomId, Long userId);

}
