package edu.neuroginarium.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@SequenceGenerator(allocationSize = 1, name = "game_seq", sequenceName = "game_seq")
public class Game {
    public static final int MAX_PLAYERS_CNT = 7;
    public static final int MIN_PLAYERS_CNT = 4;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_seq")
    private Long id;

    private Boolean isAutoGame;

    private int playersCnt;

    @Enumerated(EnumType.STRING)
    private GameStatus status;

    @OneToMany(mappedBy = "game")
    private Set<Player> players;

    private LocalDateTime creationDateTime;

    private int round;

    @Nullable
    private String token;

    @OneToMany(mappedBy = "game")
    private Set<Card> cards;

    @OneToMany(mappedBy = "game")
    private Set<GameRound> rounds;

    public void startNewRound() {
        this.setRound(round + 1);
    }

    public void finish() {
        this.setStatus(GameStatus.FINISHED);
    }
}
