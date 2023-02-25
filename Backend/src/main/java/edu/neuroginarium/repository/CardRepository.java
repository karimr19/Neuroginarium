package edu.neuroginarium.repository;

import edu.neuroginarium.exception.NotFoundException;
import edu.neuroginarium.model.Card;
import edu.neuroginarium.model.CardStatus;
import edu.neuroginarium.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findAllByPlayerId(Long playerId);

    Optional<Card> findFirstByPlayerIdAndStatus(Long playerId, CardStatus status);

    default Card findByIdOrThrow(Long cardId) {
        var optCard = findById(cardId);
        if (optCard.isEmpty()) {
            throw new NotFoundException(Card.class, cardId);
        }
        return optCard.get();
    }

    List<Card> findAllByGameAndStatus(Game game, CardStatus status);
}
