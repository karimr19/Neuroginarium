package edu.neuroginarium.repository;

import edu.neuroginarium.model.GameRound;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRoundRepository extends JpaRepository<GameRound, Long> {
}
