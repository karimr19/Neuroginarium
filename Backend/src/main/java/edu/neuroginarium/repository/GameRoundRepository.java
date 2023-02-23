package edu.neuroginarium.repository;

import edu.neuroginarium.exception.NotFoundException;
import edu.neuroginarium.model.Card;
import edu.neuroginarium.model.GameRound;
import edu.neuroginarium.model.GameRoundStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRoundRepository extends JpaRepository<GameRound, Long> {
    Optional<GameRound> findByStatusNot(GameRoundStatus status);

    default GameRound findByIdOrThrow(Long gameRoundId) {
        var optRound = findById(gameRoundId);
        if (optRound.isEmpty()) {
            throw new NotFoundException(Card.class, gameRoundId);
        }
        return optRound.get();
    }
}
