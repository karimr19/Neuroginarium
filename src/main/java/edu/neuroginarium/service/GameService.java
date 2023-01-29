package edu.neuroginarium.service;

import edu.neuroginarium.model.Game;
import edu.neuroginarium.model.Player;
import edu.neuroginarium.repository.GameRepository;
import edu.neuroginarium.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    public Long findGame(Long userId) {
        Long createdGameId = gameRepository.findOldestCreatedGameId();
        Game game = gameRepository.findById(createdGameId).get();
        var player = new Player()
                .setGame(game)
                .setUserId(userId);
        playerRepository.save(player);
        return createdGameId;
    }
}
