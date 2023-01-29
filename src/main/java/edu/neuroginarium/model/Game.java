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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isAutoGame;

    private int playersCnt;

    private GameStatus status;

    @OneToMany(mappedBy = "game")
    private Set<Player> players;

    private LocalDateTime creationDateTime;

    @Nullable
    private String token;
}
