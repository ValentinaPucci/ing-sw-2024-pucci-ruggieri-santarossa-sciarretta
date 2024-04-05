package it.polimi.ingsw.model.gameModelImmutable;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.listener.ListenersHandler;
import it.polimi.ingsw.model.board.CommonBoard;
import it.polimi.ingsw.model.cards.gameCards.ResourceCard;
import it.polimi.ingsw.model.chat.Chat;
import it.polimi.ingsw.model.enumerations.Coordinate;
import it.polimi.ingsw.model.enumerations.GameStatus;

import java.io.Serializable;
import java.util.*;

public class GameModelImmutable implements Serializable {
    private  final Map<Integer, Integer> leader_board;
    private final Integer gameId;
    private final Integer current_player;
    private final Chat chat;
    private final GameStatus status;
    private final transient ListenersHandler listenersHandler;
    private final Queue<Player> player_queue;
    private final List<Player> players;
    private final int num_players;
    private final boolean game_over;
    private final boolean second_last_turn;
    private final CommonBoard common_board;
    private final ConcreteDeck resource_deck;
    private final ConcreteDeck gold_deck;
    private final ConcreteDeck objective_deck;
    private final ConcreteDeck starter_deck;
    private final int[] final_scores;
    private final Player[] winners;
    private final Coordinate coordinate;
    private final ResourceCard already_placed_card;
    private final int from_where_draw;
    private final int from_which_deckindex;
    private final int col;
    private final int row;
    public GameModelImmutable(CommonBoard common_board, List<Player> players) {

        Random random = new Random();
        gameId = random.nextInt(1000000);
        status = GameStatus.WAIT;
        chat = new Chat();
        listenersHandler = new ListenersHandler();

        this.player_queue = new LinkedList<>();
        this.players = players;
        this.num_players = players.size();
        this.game_over = false;
        this.second_last_turn = false;
        this.final_scores = new int[num_players];
        this.common_board = common_board;
        this.resource_deck = common_board.getResourceConcreteDeck();
        this.gold_deck = common_board.getGoldConcreteDeck();
        this.starter_deck = common_board.getStarterConcreteDeck();
        this.objective_deck = common_board.getObjectiveConcreteDeck();
        this.leader_board = new HashMap<>();
        this.already_placed_card = null;


        this.current_player = -1;
        for (Player player : players) {
            this.player_queue.offer(player);
        }
        this.coordinate = Coordinate.NE;
        this.from_where_draw = 0;
        this.from_which_deckindex = 0;
        this.col = 0;
        this.row = 0;;
        this.winners = new Player[0];
    }

    public GameModelImmutable(GameModel modelToCopy) {

        this.gameId = modelToCopy.getGameId();
        this.current_player = modelToCopy.getCurrentPlayer();
        this.chat = modelToCopy.getChat();
        this.status = modelToCopy.getStatus();
        this.listenersHandler = modelToCopy.getListenersHandler();
        this.player_queue = modelToCopy.getPlayerQueue();
        this.players = modelToCopy.getPlayers();
        this.num_players = modelToCopy.getNumPlayers();
        this.game_over = modelToCopy.isGameOver();
        this.second_last_turn = modelToCopy.isSecondLastTurn();
        this.common_board = modelToCopy.getCommonBoard();
        this.resource_deck = modelToCopy.getResourceDeck();
        this.gold_deck = modelToCopy.getGoldDeck();
        this.objective_deck = modelToCopy.getObjectiveDeck();
        this.starter_deck = modelToCopy.getStarterDeck();
        this.final_scores = modelToCopy.getFinalScores();
        this.winners = modelToCopy.getWinners();
        this.coordinate = modelToCopy.getCoordinate();
        this.already_placed_card = modelToCopy.getAlreadyPlacedCard();
        this.from_where_draw = modelToCopy.getFromWhereDraw();
        this.from_which_deckindex = modelToCopy.getFromWhichDeckIndex();
        this.leader_board = new HashMap<>();
        this.col = modelToCopy.getCol();
        this.row = modelToCopy.getRow();

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

    public Queue<Player> getPlayerQueue() {
        return this.player_queue;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public int getNumPlayers() {
        return this.num_players;
    }

    public boolean isGameOver() {
        return this.game_over;
    }

    public boolean isSecondLastTurn() {
        return this.second_last_turn;
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

    public Player[] getWinners() {
        return this.winners;
    }

    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    public ResourceCard getAlreadyPlacedCard() {
        return this.already_placed_card;
    }

    public int getFromWhereDraw() {
        return this.from_where_draw;
    }

    public int getFromWhichDeckIndex() {
        return this.from_which_deckindex;
    }

    public int getCol() {
        return this.col;
    }

    public int getRow() {
        return this.row;
    }

}