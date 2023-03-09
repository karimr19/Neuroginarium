package edu.neuroginarium.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@SequenceGenerator(allocationSize = 1, name = "vote_seq", sequenceName = "vote_seq")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vote_seq")
    private Long id;

    private Long playerId;

    private Long cardId;

    private Long roundId;
}
