package it.polimi.demo.model.gameModelImmutable;

import it.polimi.demo.model.*;
import it.polimi.demo.listener.ListenersHandler;
import it.polimi.demo.model.cards.Card;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.model.interfaces.ChatIC;
import it.polimi.demo.model.interfaces.CommonBoardIC;
import it.polimi.demo.model.interfaces.PlayerIC;
import it.polimi.demo.model.interfaces.ResourceCardIC;

import java.io.Serializable;
import java.util.*;

/**
 * This class is used to create an immutable version of the GameModel
 */
public class GameModelImmutable implements Serializable {

    private final List<PlayerIC> aux_order_players;
    private final LinkedList<PlayerIC> players_connected;
    private final Integer gameId;
    private final ListenersHandler listener_handler;

    private final ChatIC chat;
    private final GameStatus status;
    private final Player first_finishing_player;
    private final List<PlayerIC> winners;
    private final CommonBoardIC common_board;
    private final PlayerIC first_player;
    private final Map<PlayerIC, Integer> leaderBoard;

    /**
     * This is the constructor which is called by the view and in the Observer pattern
     * @param model_to_copy is the GameModel instance from which we create the immutable version
     */
    public GameModelImmutable(GameModel model_to_copy) {

        this.aux_order_players = new ArrayList<>(model_to_copy.getAllPlayers());
        this.players_connected = new LinkedList<>(model_to_copy.getPlayersConnected());
        this.common_board = model_to_copy.getCommonBoard();
        this.listener_handler = model_to_copy.getListenersHandler();

        this.gameId = model_to_copy.getGameId();
        this.chat = model_to_copy.getChat();
        this.status = model_to_copy.getStatus();
        this.first_finishing_player = model_to_copy.getFirstFinishingPlayer();
        this.winners = new ArrayList<>(model_to_copy.getWinners());
        this.first_player = model_to_copy.getBeginnerPlayer();
        this.leaderBoard = new HashMap<>(model_to_copy.getLeaderboard());
    }

    public Map<PlayerIC, Integer> getLeaderBoard() {
        return leaderBoard;
    }

    public Integer getGameId() {
        return gameId;
    }

    public List<PlayerIC> getPlayers() {
        return aux_order_players;
    }

    public LinkedList<PlayerIC> getPlayersConnected() {
        return players_connected;
    }

    public ChatIC getChat() {
        return chat;
    }

    public GameStatus getStatus() {
        return status;
    }

    public ListenersHandler getListenersHandler() {
        return listener_handler;
    }

    public Player getFirstFinishedPlayer() {
        return first_finishing_player;
    }

    public List<PlayerIC> getWinners() {
        return winners;
    }

    public CommonBoardIC getCommonBoard() {
        return common_board;
    }

    public PlayerIC getFirstPlayer() {
        return first_player;
    }

    public List<ResourceCardIC> getHandCurrentPlayer() {
        assert players_connected.peek() != null;
        return players_connected.peek().getHandIC();
    }

    public String getNicknameCurrentPlayer() {
        assert players_connected.peek() != null;
        return players_connected.peek().getNickname();
    }

    public PlayerIC getPlayerEntity(String nickname) {
        for (PlayerIC player : aux_order_players) {
            if (player.getNickname().equals(nickname)) {
                return player;
            }
        }
        return null;
    }

    public PlayerIC getCurrentPlayerEntity() {
        return players_connected.peek();
    }

}