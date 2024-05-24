package it.polimi.demo.model.gameModelImmutable;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.board.CommonBoard;
import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.model.interfaces.*;
import it.polimi.demo.view.PlayerDetails;

import java.io.Serializable;
import java.util.*;

public class GameModelImmutable implements Serializable {

    private final List<PlayerIC> aux_order_players;
    private final LinkedList<PlayerIC> players_connected;
    private final Integer gameId;
    private final List<GameListener> listeners;
    private final String initial_player_nickname;
    private final CommonBoard common_board;
    private final int num_required_players_to_start;
    private final GameStatus actual_status;
    private final String current_player_nickname;
    //private final List<Player> winners;
    private final Map<PlayerIC, Integer> leaderboard;
    private final List<PlayerDetails> playersData;
    private final boolean gameEnded;
    private final boolean gamePaused;
    private final ChatIC chat;

    public GameModelImmutable(GameModelInterface model) {
        this.gameId = model.getGameId();
        this.aux_order_players = new ArrayList<>(model.getAllPlayers());
        this.players_connected = new LinkedList<>(model.getPlayersConnected());
        this.listeners = new ArrayList<>(model.getListeners());
        this.initial_player_nickname = model.getAllPlayers().getFirst().getNickname();
        this.common_board = model.getCommonBoard();
        this.num_required_players_to_start = model.getNumPlayersToPlay();
        this.actual_status = model.getStatus();
        this.current_player_nickname = model.getPlayersConnected().peek().getNickname();
        // this.winners = model.getWinners();
        this.gameEnded = model.isEnded();
        this.gamePaused = model.isPaused();
        this.playersData = model.getPlayersDetails();
        this.chat = model.getChat();
        this.leaderboard = model.getLeaderBoard();
    }

    public GameStatus getStatus() {
        return actual_status;
    }

    public boolean isGamePaused() {
        return gamePaused;
    }

    public String getCurrentPlayerNickname() {
        return current_player_nickname;
    }

    public String getFirstPlayerNickname() {
        return initial_player_nickname;
    }


    public List<PlayerDetails> getPlayerDetails(){
        return playersData;
    }


//    public Map<PlayerIC, Integer> getLeaderBoard() {
//        return leaderBoard;
//    }

    public Integer getGameId() {
        return gameId;
    }

    public List<PlayerIC> getAllPlayers() {
        return aux_order_players;
    }

    public LinkedList<PlayerIC> getPlayersConnected() {
        return players_connected;
    }

    public List<GameListener> getListeners() {
        return listeners;
    }

//    public List<PlayerIC> getWinners() {
//        return winners;
//    }

    public CommonBoardIC getCommonBoard() {
        return common_board;
    }

    public PlayerIC getFirstPlayer() {
        return aux_order_players.getFirst();
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

    public ChatIC getChat() {
        return chat;
    }

    public Map<PlayerIC, Integer> getLeaderBoard() {
        return leaderboard;
    }
}