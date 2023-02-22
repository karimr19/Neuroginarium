package edu.neuroginarium.repository;

import edu.neuroginarium.model.GameRound;
import edu.neuroginarium.model.GameRoundStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRoundRepository extends JpaRepository<GameRound, Long> {
    Optional<GameRound> findByStatusNot(GameRoundStatus status);
}
