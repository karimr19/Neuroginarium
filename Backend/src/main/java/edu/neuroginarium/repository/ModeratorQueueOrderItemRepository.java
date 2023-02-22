package edu.neuroginarium.repository;

import edu.neuroginarium.model.ModeratorQueueOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModeratorQueueOrderItemRepository extends JpaRepository<ModeratorQueueOrderItem, Long> {
    Optional<ModeratorQueueOrderItem> findByGameIdAndOrder(Long gameId, int order);
}
