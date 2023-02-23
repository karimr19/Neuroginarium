package edu.neuroginarium.repository;

import edu.neuroginarium.exception.NotFoundException;
import edu.neuroginarium.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findAllByPlayerId(Long playerId);

    default Card findByIdOrThrow(Long cardId) {
        var optCard = findById(cardId);
        if (optCard.isEmpty()) {
            throw new NotFoundException(Card.class, cardId);
        }
        return optCard.get();
    }
}
