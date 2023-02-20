package edu.neuroginarium.controller;

import edu.neuroginarium.model.Card;
import edu.neuroginarium.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    @GetMapping
    public Long findGame(@RequestParam(name = "userId") Long userId) {
        return gameService.findGame(userId);
    }

    @PostMapping("/create")
    public String createGame(@RequestParam(name = "userId") Long userId) {
        return gameService.createGame(userId);
    }

    @PutMapping("/join")
    public void joinGame(@RequestParam(name = "userId") Long userId, @RequestParam(name = "gameToken") String token) {
        gameService.joinGame(userId, token);
    }

    @PostMapping("/start")
    public void startGame(@RequestParam(name = "gameToken") String gameToken) {
        gameService.startGame(gameToken);
    }

    @GetMapping(value = "/{id}/cards/{player_id}")
    public List<Card> getPlayerCards(@PathVariable("id") Long gameId, @PathVariable("player_id") Long playerId) {
        return gameService.getPlayerCards(gameId, playerId);
    }
}
