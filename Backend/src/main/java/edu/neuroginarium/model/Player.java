package edu.neuroginarium.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@SequenceGenerator(allocationSize = 1, name = "player_seq", sequenceName = "player_seq")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_seq")
    private Long id;

    private Long userId;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    private int points;
}
