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
public class Game {
    public static final int MAX_PLAYERS_CNT = 7;
    public static final int MIN_PLAYERS_CNT = 4;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isAutoGame;

    private int playersCnt;

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
}
