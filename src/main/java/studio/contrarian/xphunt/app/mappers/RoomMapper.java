package studio.contrarian.xphunt.app.mappers;

import studio.contrarian.xphunt.app.dto.RoomDTO;
import studio.contrarian.xphunt.app.dto.RoomMembershipDTO;
import studio.contrarian.xphunt.app.dto.RoomSimpleDTO;
import studio.contrarian.xphunt.app.model.HunterRoom;
import studio.contrarian.xphunt.app.model.Room;

import java.util.stream.Collectors;

public class RoomMapper {

    public static RoomSimpleDTO toSimple(Room room) {
        return new RoomSimpleDTO(room.getId(), room.getName());
    }

    public static RoomMembershipDTO toRoomMembershipDTO(HunterRoom hunterRoom) {
        if (hunterRoom == null || hunterRoom.getRoom() == null) {
            return null;
        }
        Room room = hunterRoom.getRoom();
        return new RoomMembershipDTO(
                room.getId(),
                room.getName(),
                hunterRoom.getXpInRoom()
        );
    }

    public static RoomDTO toDTO(Room room) {
        return new RoomDTO(
                room.getId(),
                room.getName(),
                room.getInviteCode(),
                room.getHunterRooms().stream()
                        .map(HunterMapper::toHunterRoomSimpleDTO)
                        .collect(Collectors.toList()),
                room.getTasks().stream().map(TaskMapper :: toDTO).collect(Collectors.toList())
        );
    }
}
