package edu.neuroginarium.controller;

import edu.neuroginarium.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;

    public Long findGame(@RequestParam(name = "userId") Long userId) {
        return gameService.findGame(userId);
    }
}
