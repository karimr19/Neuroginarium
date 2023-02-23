package edu.neuroginarium.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Entity
@Accessors(chain = true)
public class GameRound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long associationCreatorId;

    private String association;

    private GameRoundStatus status = GameRoundStatus.STARTED;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    Long cardId;
}
