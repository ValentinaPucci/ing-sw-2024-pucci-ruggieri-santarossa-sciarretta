package it.polimi.demo.model.gameModelImmutable;

import it.polimi.demo.model.GameModel;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.board.CommonBoard;
import it.polimi.demo.model.cards.gameCards.StarterCard;
import it.polimi.demo.model.cards.objectiveCards.ObjectiveCard;
import it.polimi.demo.model.chat.Chat;
import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.model.interfaces.*;

import java.io.Serializable;
import java.util.*;

public class GameModelImmutable implements Serializable {

    private final ArrayList<PlayerIC> aux_order_players;
    private final LinkedList<PlayerIC> players_connected;
    private final Integer gameId;
    private final String initial_player_nickname;
    private final CommonBoard common_board;
    private final int num_required_players_to_start;
    private final GameStatus actual_status;
    private final String current_player_nickname;
    // private final List<Player> winners;
    private final Map<Player, Integer> leaderboard;
    private final Chat chat;
    private final List<StarterCard> starter_cards;
    private final List<ObjectiveCard> objective_cards;

    public GameModelImmutable(GameModel model) {
        this.gameId = model.getGameId();
        this.aux_order_players = new ArrayList<>(model.getAllPlayers());
        this.players_connected = new LinkedList<>(model.getPlayersConnected());
        this.initial_player_nickname = model.getAllPlayers().getFirst().getNickname();
        this.common_board = model.getCommonBoard();
        this.num_required_players_to_start = model.getNumPlayersToPlay();
        this.actual_status = model.getStatus();
        this.current_player_nickname = model.getPlayersConnected().peek().getNickname();
        this.chat = model.getChat();
        this.leaderboard = model.getLeaderboard();
        this.starter_cards = new ArrayList<>(model.getStarterCardsToChoose(current_player_nickname));
        this.objective_cards = new ArrayList<>(model.getPersonalObjectiveCardsToChoose(current_player_nickname));
    }

    public ArrayList<PlayerIC> getAllPlayers(){
        return aux_order_players;
    }

    public GameStatus getStatus() {
        return actual_status;
    }

    public String getCurrentPlayerNickname() {
        return current_player_nickname;
    }

    public Integer getGameId() {
        return gameId;
    }


    public LinkedList<PlayerIC> getPlayersConnected() {
        return players_connected;
    }

    public CommonBoard getCommonBoard() {
        return common_board;
    }

    public PlayerIC getPlayerEntity(String nickname) {
        for (PlayerIC player : players_connected) {
            if (player.getNickname().equals(nickname)) {
                return player;
            }

        }
        // Handle case where player is not found
        throw new IllegalArgumentException("Player not found: " + nickname);
    }

    public Chat getChat() {
        return chat;
    }

    public Map<Player, Integer> getLeaderBoard() {
        return leaderboard;
    }

    public List<StarterCard> getStarterCards(String nick) {
        if (nick.equals(current_player_nickname)) {
            return starter_cards;
        }
        else
            return null;

    }

    public List<ObjectiveCard> getObjectiveCards(String nick) {
        if (nick.equals(current_player_nickname)) {
            return objective_cards;
        }
        else
            return null;
    }

    public List<PlayerIC> getClassification() {
        players_connected.sort(Comparator.comparing(PlayerIC::getFinalScore,Comparator.reverseOrder()));
        return players_connected;
    }
}