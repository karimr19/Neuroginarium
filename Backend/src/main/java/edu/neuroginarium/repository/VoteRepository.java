package edu.neuroginarium.repository;

import edu.neuroginarium.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findAllByRoundId(Long roundId);
}
