package studio.contrarian.xphunt.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import studio.contrarian.xphunt.app.model.Room;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByInviteCode(String inviteCode);
}
