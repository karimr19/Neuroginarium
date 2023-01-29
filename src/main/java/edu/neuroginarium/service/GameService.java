package edu.neuroginarium.service;

import edu.neuroginarium.repository.exception.PlayersCntIsMaxException;
import edu.neuroginarium.model.Game;
import edu.neuroginarium.model.GameStatus;
import edu.neuroginarium.model.Player;
import edu.neuroginarium.repository.GameRepository;
import edu.neuroginarium.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    public Long findGame(Long userId) {
        Long createdGameId = gameRepository.findOldestCreatedGameId();
        var optGame = gameRepository.findById(createdGameId);
        Game game = optGame.orElseGet(() -> buildGame(true, null));
        buildPlayer(userId, game);
        return createdGameId;
    }

    public String createGame(Long userId) {
        String gameToken = UUID.randomUUID().toString();
        Game game = buildGame(false, gameToken);
        buildPlayer(userId, game);
        return gameToken;
    }

    private Game buildGame(boolean isAutoGame, String token) {
        return new Game()
                .setIsAutoGame(isAutoGame)
                .setStatus(GameStatus.CREATED)
                .setCreationDateTime(LocalDateTime.now())
                .setToken(token);
    }

    private Player buildPlayer(Long userId, Game game) {
        game.setPlayersCnt(game.getPlayersCnt() + 1);
        return playerRepository.save(new Player()
                .setGame(game)
                .setUserId(userId));
    }

    public void joinGame(Long userId, String token) {
        Game game = gameRepository.findByTokenOrThrow(token);
        validatePlayersCnt(game);
        buildPlayer(userId, game);
    }

    private void validatePlayersCnt(Game game) {
        if (game.getPlayersCnt() == Game.MAX_PLAYERS_CNT) {
            throw new PlayersCntIsMaxException(game.getToken());
        }
    }

    public void startGame(String gameToken) {
        Game game = gameRepository.findByTokenOrThrow(gameToken);
        game.setStatus(GameStatus.STARTED);
        gameRepository.save(game);
    }
}
