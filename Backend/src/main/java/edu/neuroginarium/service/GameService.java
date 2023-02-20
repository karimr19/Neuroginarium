package edu.neuroginarium.service;

import edu.neuroginarium.exception.PlayersCntIsMaxException;
import edu.neuroginarium.model.Card;
import edu.neuroginarium.model.Game;
import edu.neuroginarium.model.GameStatus;
import edu.neuroginarium.model.Player;
import edu.neuroginarium.repository.CardRepository;
import edu.neuroginarium.repository.GameRepository;
import edu.neuroginarium.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final CardRepository cardRepository;
    private final CardService cardService;
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
        var game = new Game()
                .setIsAutoGame(isAutoGame)
                .setStatus(GameStatus.CREATED)
                .setCreationDateTime(LocalDateTime.now())
                .setToken(token);
        gameRepository.save(game);
        return game;
    }

    private Player buildPlayer(Long userId, Game game) {
        game.setPlayersCnt(game.getPlayersCnt() + 1);
        if(game.getIsAutoGame() && game.getPlayersCnt() == Game.MAX_PLAYERS_CNT) {
            startGame(game);
        }
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
        startGame(game);
    }

    private void startGame(Game game) {
        List<Card> cards = cardService.generateCards(game);
        setCardsPlayerId(game, cards);
        game.setStatus(GameStatus.STARTED);
        gameRepository.save(game);
    }

    private void setCardsPlayerId(Game game, List<Card> cards) {
        List<Player> players = game.getPlayers().stream().toList();
        int cardsForPlayerCnt = cards.size() / game.getPlayersCnt();
        int cardsI = 0;
        for (int i = 0; i < game.getPlayersCnt(); ++i) {
            for (int j = 0; j < cardsForPlayerCnt; ++j) {
                cardRepository.save(cards.get(cardsI++).setPlayerId(players.get(i).getId()));
            }
        }
    }

    @Scheduled(cron = "*/2 * * * *")
    private void startNotStartedReadyGames() {
        gameRepository.findAllByPlayersCntGreaterThanEqual(Game.MIN_PLAYERS_CNT)
                .forEach(this::startGame);
    }
}
