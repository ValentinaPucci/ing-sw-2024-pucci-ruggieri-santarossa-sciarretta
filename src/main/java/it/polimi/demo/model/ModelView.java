package it.polimi.demo.model;

import it.polimi.demo.model.board.CommonBoard;
import it.polimi.demo.model.cards.gameCards.StarterCard;
import it.polimi.demo.model.cards.objectiveCards.ObjectiveCard;
import it.polimi.demo.model.chat.Chat;
import it.polimi.demo.model.enumerations.Coordinate;
import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.model.enumerations.Orientation;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * Represents a serialized view of the game model that contains essential information for clients.
 * This class provides getters to access various aspects of the game state.
 */
public class ModelView implements Serializable {

    @Serial
    private static final long serialVersionUID = 7726507870920841215L;

    private final ArrayList<Player> aux_order_players;
    private final LinkedList<Player> players_connected;
    private final Integer gameId;
    private final CommonBoard common_board;
    private final GameStatus actual_status;
    private final String current_player_nickname;
    private final LinkedHashMap<Player, Integer> leaderboard;
    private final Chat chat;
    private final List<StarterCard> starter_cards;
    private final List<ObjectiveCard> objective_cards;
    private final ArrayList<Integer> last_chosen_card;
    private final Orientation last_chosen_orientation;
    private final Coordinate last_coordinate;
    private final Player first_player;

    /**
     * Constructs a ModelView object based on the provided game model.
     * @param model The game model from which to create the view.
     */
    public ModelView(Model model) {
        this.gameId = model.getGameId();
        this.aux_order_players = new ArrayList<>(model.getAllPlayers());
        this.players_connected = new LinkedList<>(model.getPlayersConnected());
        this.common_board = model.getCommonBoard();
        this.actual_status = model.getStatus();
        this.current_player_nickname = model.getPlayersConnected().peek().getNickname();
        this.chat = model.getChat();
        this.leaderboard = model.getLeaderboard();
        this.starter_cards = new ArrayList<>(model.getStarterCardsToChoose(current_player_nickname));
        this.objective_cards = new ArrayList<>(model.getPersonalObjectiveCardsToChoose(current_player_nickname));
        this.last_chosen_card = model.getLastChosenCardAndPosition();
        this.last_chosen_orientation = model.getLastChosenOrientation();
        this.last_coordinate = model.getLastCoordinate();
        this.first_player = model.getFirstPlayer();
    }

    /**
     * Retrieves all players in the game.
     * @return An ArrayList of all players.
     */
    public ArrayList<Player> getAllPlayers() {
        return aux_order_players;
    }

    /**
     * Retrieves the current game status.
     * @return The current GameStatus.
     */
    public GameStatus getStatus() {
        return actual_status;
    }

    /**
     * Retrieves the nickname of the current player.
     * @return The nickname of the current player.
     */
    public String getCurrentPlayerNickname() {
        return current_player_nickname;
    }

    /**
     * Retrieves the game ID.
     * @return The game ID.
     */
    public Integer getGameId() {
        return gameId;
    }

    /**
     * Retrieves the linked list of players currently connected.
     * @return A LinkedList of players connected.
     */
    public LinkedList<Player> getPlayersConnected() {
        return players_connected;
    }

    /**
     * Retrieves the common board of the game.
     * @return The CommonBoard object representing the game board.
     */
    public CommonBoard getCommonBoard() {
        return common_board;
    }

    /**
     * Retrieves the player entity by nickname.
     * @param nickname The nickname of the player to retrieve.
     * @return The Player object associated with the nickname.
     * @throws IllegalArgumentException if the player with the given nickname is not found.
     */
    public Player getPlayerEntity(String nickname) {
        for (Player player : players_connected) {
            if (player.getNickname().equals(nickname)) {
                return player;
            }
        }
        throw new IllegalArgumentException("Player not found: " + nickname);
    }

    /**
     * Retrieves the chat instance of the game.
     * @return The Chat instance representing the game chat.
     */
    public Chat getChat() {
        return chat;
    }

    /**
     * Retrieves the leaderboard map of players and their scores.
     * @return A Map of players and their scores.
     */
    public LinkedHashMap<Player, Integer> getLeaderBoard() {
        return leaderboard;
    }

    /**
     * Retrieves the starter cards available to a specific player.
     * @param nick The nickname of the player whose starter cards to retrieve.
     * @return A List of StarterCard objects available to the player.
     */
    public List<StarterCard> getStarterCards(String nick) {
        if (nick.equals(current_player_nickname)) {
            return starter_cards;
        } else {
            return null;
        }
    }

    /**
     * Retrieves the objective cards available to a specific player.
     * @param nick The nickname of the player whose objective cards to retrieve.
     * @return A List of ObjectiveCard objects available to the player.
     */
    public List<ObjectiveCard> getObjectiveCards(String nick) {
        if (nick.equals(current_player_nickname)) {
            return objective_cards;
        } else {
            return null;
        }
    }

    /**
     * Retrieves the classification (sorted list of players) based on their final scores.
     * @return A List of Player objects sorted by final score in descending order.
     */
    public List<Player> getClassification() {
        players_connected.sort(Comparator.comparing(Player::getFinalScore, Comparator.reverseOrder()));
        return players_connected;
    }

    /**
     * Retrieves the last chosen card and position.
     * @return An ArrayList containing the last chosen card and position.
     */
    public ArrayList<Integer> getLastChosenCardAndPosition(){
        return last_chosen_card;
    }

    /**
     * Retrieves the last chosen orientation.
     * @return The last chosen Orientation.
     */
    public Orientation getLastChosenOrientation(){
        return last_chosen_orientation;
    }

    /**
     * Retrieves the last coordinate.
     * @return The last Coordinate.
     */
    public Coordinate getLastCoordinate(){
        return last_coordinate;
    }

    /**
     * Retrieves the first player of the game.
     * @return The first Player object.
     */
    public Player getFirstPlayer(){
        return first_player;
    }
}
