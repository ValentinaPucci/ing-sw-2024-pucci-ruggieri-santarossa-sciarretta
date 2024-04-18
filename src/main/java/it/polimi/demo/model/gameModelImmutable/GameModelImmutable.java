package it.polimi.demo.model.gameModelImmutable;

import it.polimi.demo.model.*;
import it.polimi.demo.listener.ListenersHandler;
import it.polimi.demo.model.board.CommonBoard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.chat.Chat;
import it.polimi.demo.model.enumerations.Coordinate;
import it.polimi.demo.model.enumerations.GameStatus;

import java.io.Serializable;
import java.util.*;

public class GameModelImmutable implements Serializable {
    private  final Map<Integer, Integer> leader_board;
    private final Integer gameId;
    private final Integer current_player;
    private final Chat chat;
    private final GameStatus status;
    private final transient ListenersHandler listenersHandler;
    private final List<Player> players;
    private final int num_players;
    private final CommonBoard common_board;
    private final ConcreteDeck resource_deck;
    private final ConcreteDeck gold_deck;
    private final ConcreteDeck objective_deck;
    private final ConcreteDeck starter_deck;
    private final int[] final_scores;
    private final List<Player> winners;
    public GameModelImmutable(CommonBoard common_board, List<Player> players) {

        Random random = new Random();
        gameId = random.nextInt(1000000);
        status = GameStatus.WAIT;
        chat = new Chat();
        listenersHandler = new ListenersHandler();

        this.players = players;
        this.num_players = players.size();
        this.final_scores = new int[num_players];
        this.common_board = common_board;
        this.resource_deck = common_board.getResourceConcreteDeck();
        this.gold_deck = common_board.getGoldConcreteDeck();
        this.starter_deck = common_board.getStarterConcreteDeck();
        this.objective_deck = common_board.getObjectiveConcreteDeck();
        this.leader_board = new HashMap<>();
        this.current_player = -1;
        this.winners = new ArrayList<>();
    }

    public GameModelImmutable(GameModel modelToCopy) {
        this.gameId = modelToCopy.getGameId();
        this.current_player = modelToCopy.getCurrentPlayer();
        this.chat = modelToCopy.getChat();
        this.status = modelToCopy.getStatus();
        this.listenersHandler = modelToCopy.getListenersHandler();
        this.players = modelToCopy.getPlayers();
        this.num_players = modelToCopy.getNumPlayers();
        this.common_board = modelToCopy.getCommonBoard();
        this.resource_deck = modelToCopy.getResourceDeck();
        this.gold_deck = modelToCopy.getGoldDeck();
        this.objective_deck = modelToCopy.getObjectiveDeck();
        this.starter_deck = modelToCopy.getStarterDeck();
        this.final_scores = modelToCopy.getFinalScores();
        this.winners = modelToCopy.getWinners();
        this.leader_board = new HashMap<>();
    }

    public Integer getGameId() {
        return this.gameId;
    }

    public Integer getCurrentPlayer() {
        return this.current_player;
    }

    public Chat getChat() {
        return this.chat;
    }

    public GameStatus getStatus() {
        return this.status;
    }

    public ListenersHandler getListenersHandler() {
        return this.listenersHandler;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public int getNumPlayers() {
        return this.num_players;
    }

    public CommonBoard getCommonBoard() {
        return this.common_board;
    }

    public ConcreteDeck getResourceDeck() {
        return this.resource_deck;
    }

    public ConcreteDeck getGoldDeck() {
        return this.gold_deck;
    }

    public ConcreteDeck getObjectiveDeck() {
        return this.objective_deck;
    }

    public ConcreteDeck getStarterDeck() {
        return this.starter_deck;
    }

    public int[] getFinalScores() {
        return this.final_scores;
    }

    public List<Player> getWinners() {
        return this.winners;
    }


}