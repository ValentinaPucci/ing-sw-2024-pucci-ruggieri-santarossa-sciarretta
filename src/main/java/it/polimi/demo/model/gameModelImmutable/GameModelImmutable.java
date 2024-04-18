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

    private final Map<PlayerIC, Integer> leaderBoard;
    private final Integer gameId;
    private final int index_current_player;
    private final List<PlayerIC> players;
    private final ChatIC chat;
    private final GameStatus status;
    private final ListenersHandler listener_handler;
    private final List<Integer> final_scores;
    private final Integer first_finished_player = -1;
    private final List<PlayerIC> winners;
    private final CommonBoardIC common_board;
    private final PlayerIC first_player;

    /**
     * This is the constructor which is called by the view and in the Observer pattern
     * @param model_to_copy is the GameModel instance from which we create the immutable version
     */
    public GameModelImmutable(GameModel model_to_copy) {
        this.leaderBoard = new HashMap<>(model_to_copy.getLeaderboard());
        this.players = new ArrayList<>(model_to_copy.getPlayers());
        this.common_board = model_to_copy.getCommonBoard();
        this.gameId = model_to_copy.getGameId();
        this.index_current_player = model_to_copy.getIndexCurrentPlayer();
        this.chat = model_to_copy.getChat();
        this.status = model_to_copy.getStatus();
        this.listener_handler = model_to_copy.getListenersHandler();
        this.final_scores = model_to_copy.getFinalScores();
        this.winners = new ArrayList<>(model_to_copy.getWinners());
        this.first_player = model_to_copy.getFirstPlayer();
    }

    public Map<PlayerIC, Integer> getLeaderBoard() {
        return leaderBoard;
    }

    public Integer getGameId() {
        return gameId;
    }

    public int getIndexCurrentPlayer() {
        return index_current_player;
    }

    public List<PlayerIC> getPlayers() {
        return players;
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

    public List<Integer> getFinalScores() {
        return final_scores;
    }

    public Integer getFirstFinishedPlayer() {
        return first_finished_player;
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
        return players.get(index_current_player).getHandIC();
    }

    public String getNicknameCurrentPlayer() {
        return players.get(index_current_player).getNickname();
    }

    public PlayerIC getPlayerEntity(String nickname) {
        for (PlayerIC player : players) {
            if (player.getNickname().equals(nickname)) {
                return player;
            }
        }
        return null;
    }

    public PlayerIC getCurrentPlayerEntity() {
        return players.get(index_current_player);
    }

}