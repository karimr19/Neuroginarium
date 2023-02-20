package edu.neuroginarium.service;

import edu.neuroginarium.model.Card;
import edu.neuroginarium.model.Game;
import edu.neuroginarium.repository.CardRepository;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CardService {
    private final static Integer GAME_ROUNDS_CNT = 3;
    private final static Integer CARDS_FOR_PLAYER_CNT = 6 + 3;
    // TODO поменять baseUrl
    private final static String SERVER_URL = "http://localhost:8080";
    private final CardRepository cardRepository;

    private final WebClient webClient;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofMillis(5000))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

        webClient = WebClient.builder()
                .baseUrl(SERVER_URL)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    public List<Card> generateCards(Game game) {
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec =
                (WebClient.UriSpec<WebClient.RequestBodySpec>) webClient.get();
        int cardsCnt = game.getPlayersCnt() * CARDS_FOR_PLAYER_CNT;
        WebClient.RequestBodySpec bodySpec = uriSpec.uri("/pictures/" + cardsCnt);
        var headersSpec = bodySpec.bodyValue("");
        Mono<String> response = headersSpec.exchangeToMono(r -> {
            if (r.statusCode().equals(HttpStatus.OK)) {
                return r.bodyToMono(String.class);
            } else if (r.statusCode().is4xxClientError()) {
                return Mono.just("Error response");
            } else {
                return r.createException()
                        .flatMap(Mono::error);
            }
        });
        String content = response.block();
        var images = content.split(",");

        return Arrays.stream(images)
                .map(image -> new Card().setGame(game)
                        .setImage(image))
                .collect(Collectors.toList());
        // gameRepo.save(game) ???
    }
}
