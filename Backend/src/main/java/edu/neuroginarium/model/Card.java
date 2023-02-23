package edu.neuroginarium.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Entity
@Accessors(chain = true)
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;

    //    @OneToOne
//    @JoinColumn(name = "player_id", nullable = false)
//    private Player player;
    private Long playerId;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    private CardStatus status = CardStatus.IN_CARD_DECK;

    public void giveCardToPlayer() {
        if (this.status == CardStatus.ON_HANDS) {
            return;
        }
        this.setStatus(CardStatus.ON_HANDS);
    }

    public void putCardOnTable() {
        if (this.status == CardStatus.ON_TABLE) {
            return;
        }
        this.setStatus(CardStatus.ON_TABLE);
    }
}
