package edu.neuroginarium.repository;

import edu.neuroginarium.model.Card;
import edu.neuroginarium.model.CardStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findAllByPlayerIdAndStatus(Long playerId, CardStatus status);
}
