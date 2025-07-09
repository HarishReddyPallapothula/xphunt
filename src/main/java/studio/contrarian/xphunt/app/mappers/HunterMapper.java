package studio.contrarian.xphunt.app.mappers;

import studio.contrarian.xphunt.app.dto.HunterDTO;
import studio.contrarian.xphunt.app.dto.HunterRoomSimpleDTO;
import studio.contrarian.xphunt.app.dto.HunterSimpleDTO;
import studio.contrarian.xphunt.app.model.Hunter;
import studio.contrarian.xphunt.app.model.HunterRoom;

import java.util.stream.Collectors;

public class HunterMapper {

    public static HunterSimpleDTO toSimple(Hunter hunter) {
        return new HunterSimpleDTO(hunter.getId(), hunter.getName(), hunter.getTotalXp());
    }

    public static HunterRoomSimpleDTO toHunterRoomSimpleDTO(HunterRoom hunterRoom) {
        if (hunterRoom == null || hunterRoom.getHunter() == null) {
            return null;
        }
        Hunter hunter = hunterRoom.getHunter();
        return new HunterRoomSimpleDTO(
                hunter.getId(),
                hunter.getName(),
                hunterRoom.getXpInRoom()
        );
    }

    public static HunterDTO toDTO(Hunter hunter) {
        return new HunterDTO(
                hunter.getId(),
                hunter.getName(),
                hunter.getTotalXp(),
                hunter.getHunterRooms().stream()
                        // The stream now maps each HunterRoom link to a RoomMembershipDTO
                        .map(RoomMapper::toRoomMembershipDTO)
                        .collect(Collectors.toList()),
                hunter.getClaimedTasks().stream()
                        .map(TaskMapper::toSimple)
                        .collect(Collectors.toList())
        );
    }

}

