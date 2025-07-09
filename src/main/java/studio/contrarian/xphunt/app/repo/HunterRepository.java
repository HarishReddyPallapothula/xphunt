package studio.contrarian.xphunt.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import studio.contrarian.xphunt.app.model.Hunter;

import java.util.Optional;

public interface HunterRepository extends JpaRepository<Hunter, Long> {
    Optional<Hunter> findByName(String name);
}