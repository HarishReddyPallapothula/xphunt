package studio.contrarian.xphunt.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import studio.contrarian.xphunt.app.model.HunterRoom;
import studio.contrarian.xphunt.app.model.HunterRoomId;

import java.util.List;
import java.util.Optional;

public interface HunterRoomRepository extends JpaRepository<HunterRoom, HunterRoomId> {
    Optional<HunterRoom> findByRoomIdAndHunterId(Long roomId, Long hunterId);

    // For getting the leaderboard
    List<HunterRoom> findByRoomIdOrderByXpInRoomDesc(Long roomId);
}
