package edu.neuroginarium.service;

import edu.neuroginarium.exception.InternalException;
import edu.neuroginarium.exception.NotFoundException;
import edu.neuroginarium.exception.PlayersCntIsMaxException;
import edu.neuroginarium.model.*;
import edu.neuroginarium.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final CardRepository cardRepository;
    private final CardService cardService;
    private final GameRoundRepository gameRoundRepository;
    private final ModeratorQueueOrderItemRepository moderatorQueueOrderItemRepository;
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
        setModeratorQueueOrder(game);
        addInitialRound(game);
        game.setStatus(GameStatus.STARTED);
        gameRepository.save(game);
    }

    private void setModeratorQueueOrder(Game game) {
        AtomicInteger order = new AtomicInteger();
        game.getPlayers().forEach(player ->
                moderatorQueueOrderItemRepository.save(new ModeratorQueueOrderItem()
                        .setPlayerId(player.getId())
                        .setOrder(order.getAndIncrement())
                        .setGameId(game.getId())
                ));
    }

    private void addInitialRound(Game game) {
        var round = new GameRound().setGame(game)
                .setAssociationCreatorId(
                        findPlayerIdByGameIdAndOrder(game.getId(), ModeratorQueueOrderItem.INITIAL_ORDER)
                );
        gameRoundRepository.save(round);
    }

    private Long findPlayerIdByGameIdAndOrder(Long gameId, int order) {
        var optModeratorQueueOrderItem = moderatorQueueOrderItemRepository
                .findByGameIdAndOrder(gameId, order);
        if (optModeratorQueueOrderItem.isEmpty()) {
            throw new NotFoundException("ModeratorQueueOrderItem not found for gameId = " + gameId +
                    " and order = " + order);
        }
        return optModeratorQueueOrderItem.get().getPlayerId();
    }

    private void setCardsPlayerId(Game game, List<Card> cards) {
        List<Player> players = game.getPlayers().stream().toList();
        int cardsForPlayerCnt = cards.size() / game.getPlayersCnt();
        int cardsI = 0;
        for (int i = 0; i < game.getPlayersCnt(); ++i) {
            for (int j = 0; j < cardsForPlayerCnt; ++j) {
                if (j < CardService.CARDS_ON_HANDS_CNT) {
                    cards.get(cardsI).giveCardToPlayer();
                }
                cardRepository.save(cards.get(cardsI++).setPlayerId(players.get(i).getId()));
            }
        }
    }

    @Scheduled(cron = "*/2 * * * *")
    private void startNotStartedReadyGames() {
        gameRepository.findAllByPlayersCntGreaterThanEqual(Game.MIN_PLAYERS_CNT)
                .forEach(this::startGame);
    }

    public List<Card> getPlayerCards(Long gameId, Long playerId) {
        var optGame = gameRepository.findById(gameId);
        if (optGame.isEmpty()) {
            throw new NotFoundException(optGame.getClass(), gameId);
        }
        if (!gameContainsPlayerWithId(optGame.get(), playerId)) {
            throw new InternalException("GAME[" + gameId + "] doesn't contain PLAYER[" + playerId + "]");
        }
        return cardRepository.findAllByPlayerId(playerId);
    }

    private Boolean gameContainsPlayerWithId(Game game, Long playerId) {
        return game.getPlayers().stream()
                .map(Player::getId)
                .collect(Collectors.toSet())
                .contains(playerId);
    }

    public GameRound getCurrentRound(Long gameId) {
        var optGameRound = gameRoundRepository.findByStatusNot(GameRoundStatus.FINISHED);
        if (optGameRound.isEmpty()) {
            throw new InternalException("all GAME_ROUNDs of GAME[" + gameId + "] are finished");
        }
        return optGameRound.get();
    }

    public void makeAssociation(Long roundId, String association, Long cardId) {
        giveCard(cardId);
        var round = gameRoundRepository.findByIdOrThrow(roundId);
        round.setAssociation(association);

        addGeneratedCardOnTable(association, round.getGame());

        round.setCardId(cardId);
        round.setStatus(GameRoundStatus.ASSOCIATION_GIVEN);
        gameRoundRepository.save(round);
    }

    private void addGeneratedCardOnTable(String association, Game game) {
        var extraCard = cardService.getCardByAssociation(association, game);
        extraCard.putCardOnTable();
        cardRepository.save(extraCard);
    }

    public void giveCard(Long cardId) {
        var card = cardRepository.findByIdOrThrow(cardId);
        card.putCardOnTable();
    }
}
