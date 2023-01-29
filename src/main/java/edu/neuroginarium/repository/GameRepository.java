package edu.neuroginarium.repository;

import edu.neuroginarium.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GameRepository extends JpaRepository<Game, Long> {
    @Query(
            value = "SELECT g.id FROM Game g" +
                    "WHERE g.is_autogame = false AND g.status = 'CREATED'" +
                    "ORDER BY g.creation_datetime",
            nativeQuery = true)
    Long findOldestCreatedGameId();
}
