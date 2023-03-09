package edu.neuroginarium.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@SequenceGenerator(allocationSize = 1, name = "game_round_seq", sequenceName = "game_round_seq")
public class GameRound {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_round_seq")
    private Long id;

    private Long associationCreatorId;

    private String association;

    @Enumerated(EnumType.STRING)
    private GameRoundStatus status = GameRoundStatus.STARTED;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    Long cardId;

    public void votingMade() {
        this.setStatus(GameRoundStatus.VOTING_MADE);
    }

    public void finish() {
        this.setStatus(GameRoundStatus.FINISHED);
    }
}
