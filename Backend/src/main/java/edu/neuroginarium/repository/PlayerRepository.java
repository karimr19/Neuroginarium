package edu.neuroginarium.repository;

import edu.neuroginarium.model.Game;
import edu.neuroginarium.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findAllByGame(Game game);
}
