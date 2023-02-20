package edu.neuroginarium.repository;

import edu.neuroginarium.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
