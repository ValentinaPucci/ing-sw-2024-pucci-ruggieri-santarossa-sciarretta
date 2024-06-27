package it.polimi.demo.model;

import it.polimi.demo.observer.ObserverManager;
import it.polimi.demo.Constants;
import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.board.CommonBoard;
import it.polimi.demo.model.board.PersonalBoard;
import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.objectiveCards.ObjectiveCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.cards.gameCards.StarterCard;
import it.polimi.demo.model.chat.Chat;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Coordinate;
import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.exceptions.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static it.polimi.demo.model.enumerations.Coordinate.NE;
import static it.polimi.demo.model.enumerations.Orientation.BACK;
import static it.polimi.demo.model.enumerations.Orientation.FRONT;

/**
 * This class represents the model of the game. It contains all the logic of the game.
 * It is responsible for managing the players, the game status, the chat, the game board, and the game cards.
 * It also manages the connection and disconnection of players, the game status, and the game logic.
 * It notifies the listeners about the game state changes.
 */
public class Model implements Serializable {

    @Serial
    private static final long serialVersionUID = 4920324247277456226L;

    // the first list let us keep the order of players. It is immutable!!
    // aux_order_player.size() always >= players_connected.size()
    private final List<Player> aux_order_players;

    // the second one is used as a queue and let us know which player
    // is connected (and actively playing).
    private final LinkedList<Player> players_connected;
    private final Random random = new Random();
    private final CommonBoard common_board;
    private final int gameId;
    private int num_required_players_to_start;
    private GameStatus status;
    private Chat chat;
    private final LinkedHashMap<Player, Integer> leaderboard;
    private final ObserverManager observers;
    private ArrayList<Integer> last_chosen_card;
    private Coordinate last_coord;
    private Orientation last_chosen_orientation;
    private Player first_player = null;


    /**
     * Constructor for the model.
     */
    public Model() {
        aux_order_players = new ArrayList<>();
        players_connected = new LinkedList<>();
        last_chosen_card = new ArrayList<>(3);
        last_chosen_card.add(0);
        last_chosen_card.add(0);
        last_chosen_card.add(0);
        last_coord = null;
        last_chosen_orientation = null;
        common_board = new CommonBoard();
        gameId = -1;
        num_required_players_to_start = -1; // invalid value on purpose
        status = GameStatus.WAIT;
        leaderboard = new LinkedHashMap<>();
        observers = new ObserverManager();
    }

    /**
     * Constructor for the model.
     * @param gameID The ID of the game.
     * @param numberOfPlayers The number of players required to start the game.
     */
    public Model(int gameID, int numberOfPlayers) {
        aux_order_players = new ArrayList<>();
        players_connected = new LinkedList<>();
        last_chosen_card = new ArrayList<>();
        last_chosen_card.add(0);
        last_chosen_card.add(0);
        last_chosen_card.add(0);
        last_coord = null;
        last_chosen_orientation = null;
        common_board = new CommonBoard();
        num_required_players_to_start = numberOfPlayers;
        gameId = gameID;
        status = GameStatus.WAIT;
        chat = new Chat();
        leaderboard = new LinkedHashMap<>();
        observers = new ObserverManager();
    }

    //------------------------------------methods for players-----------------------

    /**
     * Retrieves the list of players participating in the game even if they are connected or not.
     *
     * @return The list of players.
     */
    public List<Player> getAllPlayers() {
        return aux_order_players;
    }

    /**
     * Retrieves the list of players connected to the game.
     *
     * @return The list of players connected to the game.
     */
    public LinkedList<Player> getPlayersConnected() {
        return players_connected;
    }

    /**
     * set the player as ready to start
     * @param p player to set as ready
     */
    public synchronized void setPlayerAsReadyToStart(Player p) {
        p.setAsReadyToStart();
        observers.notify_PlayerIsReadyToStart(this, p.getNickname());
        if (arePlayersReadyToStartAndEnough()) {
            extractFirstPlayerToPlay();
            initializeGame();
            setStatus(GameStatus.FIRST_ROUND);
        }
    }

    /**
     * extract the first player to play from the list of connected players
     * and put it at the end of the list.
     */
    public synchronized void extractFirstPlayerToPlay() {
        Player first_player = players_connected.get(random.nextInt(players_connected.size()));
        this.first_player = first_player;
        aux_order_players.remove(first_player);
        aux_order_players.addFirst(first_player);
        players_connected.remove(first_player);
        players_connected.addFirst(first_player);
    }


    /**
     * determine if all players are ready to start and if there are enough players to start
     * @return true if all players are ready to start and if there are enough players to start
     */
    public boolean arePlayersReadyToStartAndEnough() {
        List<Player> p = players_connected.stream().filter(Player::getReadyToStart).toList();
        return p.containsAll(players_connected) && p.size() == num_required_players_to_start;
    }

    /**
     * Retrieves the player with the specified nickname.
     * @param nick nickname of the player
     * @return player with the given nickname, or null if not found
     */
    public Player getIdentityOfPlayer(String nick) {
        for (Player player : players_connected) {
            if (player.getNickname().equals(nick)) {
                return player;
            }
        }
        return null;
    }

    /**
     * setter for the number of players required to start the game
     * @param n number of players required to start the game
     */
    public void setNumPlayersToPlay(int n) {
        num_required_players_to_start = n;
    }

    /**
     * getter for the number of players required to start the game
     * @return the number of players required to start the game
     */
    public int getNumPlayersToPlay() {
        return num_required_players_to_start;
    }

    public ArrayList<Integer> getLastChosenCardAndPosition(){
        return this.last_chosen_card;
    }

    public Orientation getLastChosenOrientation(){
        return this.last_chosen_orientation;
    }

    public Coordinate getLastCoordinate(){
        return this.last_coord;
    }

    public Player getFirstPlayer() {
        return first_player;
    }

    /**
     * Adds a new player to the game. Recall that if a player was previously playing,
     * then we are able to retrieve him/her from the auxiliary ordered list.
     * Thus, this method is meant as an adder of new players, not as an adder of disconnected players.
     * Statical offer.
     */
    public void addPlayer(Player p) {
        aux_order_players.add(p);
        players_connected.offer(p);
        observers.notify_playerJoined(this);
    }

    /**
     * Removes the player with the specified nickname from the game.
     * It is meant as a brute force routine, because in general a player is
     * only disconnected, not removed from the game.
     * @param p The nickname of the player to remove.
     */
    public void removePlayer(Player p) {
        players_connected.remove(p);
        aux_order_players.remove(p);
    }

    //-------------------------chat and messages---------------------------------------------

    /**
     * getter for the chat
     * @return the chat
     */
    public Chat getChat() {
        return this.chat;
    }

    /**
     * Sends a message to the chat.
     * @param nick nickname of the player sending the message
     * @param message the message to send
     * @throws ActionPerformedByAPlayerNotPlayingException If the player is not connected to the game.
     */
    public void sendMessage(String nick, Message message) throws ActionPerformedByAPlayerNotPlayingException {
        chat.addMessage(message);
        observers.notify_messageSent(this, nick, message);
    }

    //-------------------------managing status---------------------------------------------

    /**
     * Gets the game id
     *
     * @return gameId
     */
    public int getGameId() {
        return this.gameId;
    }

    /**
     * Gets the game status
     *
     * @return the game status
     */
    public GameStatus getStatus() {
        return this.status;
    }

    /**
     * Sets the status of the game and notifies listeners accordingly.
     *
     * @param status The status to be set for the game.
     * @throws NotReadyToRunException If attempting to set the game status to "RUNNING" but the lobby
     * does not have at least the minimum number of players or the current player index is not set.
     */
    public void setStatus(GameStatus status) {
        // Set the game status and notify listeners based on the new status
        this.status = status;

        switch (status) {
            case FIRST_ROUND -> {
                observers.notify_GameStarted(this);
                observers.notify_nextTurn(this);
            }

            case SECOND_LAST_ROUND -> {
                observers.notify_secondLastRound(this);
            }

            case LAST_ROUND -> {
                observers.notify_lastRound(this);
            }

            case ENDED -> {
                observers.notify_GameEnded(this);
            }
        }
    }

    //-------------------------game logic management---------------------------------------------

    /**
     * initialize the game calling the respective method in common_board. It may be helpful whenever
     * we need to use a certain constructor of this class instead of another.
     */
    public void initializeGame() {
        common_board.setPlayerCount(aux_order_players.size());
        common_board.initializeBoard();
        // @Deprecated
        // Only for test usage: test if the game ends when the resource card deck or the gold card deck finishes.
        //common_board.initializeBoardTestFinishingCard();
        dealCards();
    }

    /**
     * Choose the game card from the hand of the player.
     * @param p The player who is choosing the game card.
     * @param which_card The index of the card in the player's hand.
     */
    public void chooseCardFromHand(Player p, int which_card) {
        if (getStatus() == GameStatus.FIRST_ROUND)
            p.setChosenObjectiveCard(p.getSecretObjectiveCards().get(which_card));
        else {
            if (p.getHand().get(which_card) instanceof GoldCard)
                p.setChosenGameCard((GoldCard) p.getHand().get(which_card));
            else
                p.setChosenGameCard(p.getHand().get(which_card));
            observers.notify_cardChosen(this, which_card);
        }
    }

    /**
     * Deals cards to players from the common board's decks.
     * Each player receives:
     * - 1 starter card from the starter deck
     * - 2 resource cards from the resource deck
     * - 1 gold card from the gold deck
     * - 2 secret objective cards from the objective deck
     */
    public void dealCards() {
        // Deal cards to players, recall that at this stage
        // aux_order_players and players_connected are the same
        for (Player player : players_connected) {

            StarterCard starterCard1 = null;
            StarterCard starterCard2 = null;

            if (!common_board.getStarterConcreteDeck().isEmpty()) {
                starterCard1 = (StarterCard) common_board.getStarterConcreteDeck().pop();
            }
            if (!common_board.getStarterConcreteDeck().isEmpty()) {
                starterCard2 = (StarterCard) common_board.getStarterConcreteDeck().pop();
            }

            player.setStarterCardToChose(starterCard1, starterCard2);

            // Deal 2 resource cards
            for (int i = 0; i < 2; i++) {
                if (!common_board.getResourceConcreteDeck().isEmpty()) {
                    ResourceCard resourceCard = (ResourceCard) common_board.getDecks().getFirst().pop();
                    player.addToHand(resourceCard);
                }
            }

            // Deal 1 gold card
            if (!common_board.getGoldConcreteDeck().isEmpty()) {
                GoldCard goldCard = (GoldCard) common_board.getGoldConcreteDeck().pop();
                player.addToHand(goldCard);
            }

            // Deal 2 secret objective cards
            ObjectiveCard objectiveCard1 = null;
            ObjectiveCard objectiveCard2 = null;

            if (!common_board.getObjectiveConcreteDeck().isEmpty()) {
                objectiveCard1  = (ObjectiveCard) common_board.getObjectiveConcreteDeck().pop();
            }

            if (!common_board.getObjectiveConcreteDeck().isEmpty()) {
                objectiveCard2 = (ObjectiveCard) common_board.getObjectiveConcreteDeck().pop();
            }

            player.setSecretObjectives(objectiveCard1, objectiveCard2);
        }
    }

    /**
     * Place the starter card of the player p on the board.
     * @param p The player who is placing the starter card.
     * @param o The orientation of the starter card.
     * @throws GameEndedException If the game has already ended.
     */
    public void placeStarterCard(Player p, Orientation o) throws GameEndedException {

        PersonalBoard personal_board = p.getPersonalBoard();

        if (o == Orientation.FRONT) {
            p.setStarterCard(p.getStarterCardToChose().getFirst());

        }
        else {
            p.setStarterCard(p.getStarterCardToChose().get(1));
        }

        personal_board.placeStarterCard(p.getStarterCard());

        if (players_connected.stream()
                .filter(q -> q.getStarterCard() != null)
                .toList()
                .size() == players_connected.size())
            setStatus(GameStatus.RUNNING);
        observers.notify_starterCardPlaced(this, o, p.getNickname());
        nextTurn();
    }

    /**
     * Place a ** Resource ** card on the board.
     * @param card_chosen The card to place.
     * @param p The player who is placing the card.
     * @param x The x-coordinate of the cell where the card is to be placed.
     * @param y The y-coordinate of the cell where the card is to be placed.
     * @throws GameEndedException If the game has already ended.
     */
    public void placeCard(ResourceCard card_chosen, Player p, int x, int y) throws GameEndedException {

        PersonalBoard personal_board = p.getPersonalBoard();
        int old_points = p.getCurrentPoints();
        boolean admissible_move;

        last_chosen_card.set(0, card_chosen.getId());
        last_chosen_card.set(1, x);
        last_chosen_card.set(2, y);
        last_chosen_orientation = card_chosen.orientation;

        if (!personal_board.board[x][y].is_full) {
            observers.notify_illegalMove(this);
        }
        else {
            if (25 <= x && x <= 26 && 25 <= y && y <= 26) {
                StarterCard already_placed_card = p.getStarterCard();
                Coordinate coord = already_placed_card.getCoordinateAt(x, y);
                admissible_move = personal_board.placeCardAt(already_placed_card, card_chosen, coord);
                if (!admissible_move) {
                    observers.notify_illegalMove(this);
                }
                else {
                    // remove the card from the player's hand
                    this.last_coord = coord;
                    observers.notify_successMove(this, coord);
                    p.getHand().remove(p.getChosenGameCard());
                    getCommonBoard().movePlayer(aux_order_players.indexOf(p), p.getCurrentPoints() - old_points);
                    observers.notify_cardPlaced(this, x, y, card_chosen.orientation);
                    if (getStatus() == GameStatus.LAST_ROUND) {
                        if (aux_order_players.getLast().equals(p)) {
                            declareWinners();
                            setStatus(GameStatus.ENDED);
                        }
                        else
                            nextTurn();
                    }
                }
            }
            else {
                ResourceCard already_placed_card = personal_board.board[x][y].getCornerFromCell().reference_card;
                Coordinate coord = personal_board.board[x][y].getCornerFromCell().getCoordinate();
                admissible_move = personal_board.placeCardAt(already_placed_card, card_chosen, coord);
                if (!admissible_move) {
                    observers.notify_illegalMove(this);
                }
                else {
                    this.last_coord = coord;
                    observers.notify_successMove(this, coord);
                    // remove the card from the player's hand
                    p.getHand().remove(p.getChosenGameCard());
                    getCommonBoard().movePlayer(aux_order_players.indexOf(p), p.getCurrentPoints() - old_points);
                    observers.notify_cardPlaced(this, x, y, card_chosen.orientation);
                    if (getStatus() == GameStatus.LAST_ROUND) {
                        if (aux_order_players.getLast().equals(p)) {
                            declareWinners();
                            setStatus(GameStatus.ENDED);
                        }
                        else
                            nextTurn();
                    }
                }
            }
        }
    }

    /**
     * Place a ** Gold ** card on the board.
     * @param card_chosen The card to place.
     * @param p The player who is placing the card.
     * @param x The x-coordinate of the cell where the card is to be placed.
     * @param y The y-coordinate of the cell where the card is to be placed.
     * @throws GameEndedException If the game has already ended.
     */
    public void placeCard(GoldCard card_chosen, Player p, int x, int y) throws GameEndedException {

        PersonalBoard personal_board = p.getPersonalBoard();
        int old_points = p.getCurrentPoints();
        boolean admissible_move;

        last_chosen_card.set(0, card_chosen.getId());
        last_chosen_card.set(1, x);
        last_chosen_card.set(2, y);
        last_chosen_orientation = card_chosen.orientation;

        if (!personal_board.board[x][y].is_full) {
            observers.notify_illegalMove(this);
        }
        else {
            if (25 <= x && x <= 26 && 25 <= y && y <= 26) {
                StarterCard already_placed_card = p.getStarterCard();
                Coordinate coord = already_placed_card.getCoordinateAt(x, y);
                admissible_move = personal_board.placeCardAt(already_placed_card, card_chosen, coord);
                if (!admissible_move) {
                    observers.notify_illegalMove(this);
                }
                else {
                    this.last_coord = coord;
                    observers.notify_successMove(this, coord);
                    // remove the card from the player's hand
                    p.getHand().remove(p.getChosenGameCard());
                    getCommonBoard().movePlayer(aux_order_players.indexOf(p), p.getCurrentPoints() - old_points);
                    observers.notify_cardPlaced(this, x, y, card_chosen.orientation);
                    if (getStatus() == GameStatus.LAST_ROUND) {
                        if (aux_order_players.getLast().equals(p)) {
                            declareWinners();
                            setStatus(GameStatus.ENDED);
                        }
                        else
                            nextTurn();
                    }
                }
            }
            else {
                ResourceCard already_placed_card = personal_board.board[x][y].getCornerFromCell().reference_card;
                Coordinate coord = personal_board.board[x][y].getCornerFromCell().getCoordinate();
                admissible_move = personal_board.placeCardAt(already_placed_card, card_chosen, coord);
                if (!admissible_move) {
                    observers.notify_illegalMove(this);
                }
                else {
                    this.last_coord = coord;
                    // remove the card from the player's hand
                    observers.notify_successMove(this, coord);
                    p.getHand().remove(p.getChosenGameCard());
                    getCommonBoard().movePlayer(aux_order_players.indexOf(p), p.getCurrentPoints() - old_points);
                    observers.notify_cardPlaced(this, x, y, card_chosen.orientation);
                    if (getStatus() == GameStatus.LAST_ROUND) {
                        if (aux_order_players.getLast().equals(p)) {
                            declareWinners();
                            setStatus(GameStatus.ENDED);
                        }
                        else
                            nextTurn();
                    }
                }
            }
        }
    }

    /**
     * Draw a card based on the provided index on the board (by the socket)
     * @param index The index indicating which card to draw:
     *              1: Resource Deck
     *              2: First Resource Card on the table
     *              3: Second Resource Card on the table
     *              4: Gold Deck
     *              5: First Gold Card on the table
     *              6: Second Gold Card on the table
     * @throws IllegalArgumentException If the index is less than 1 or greater than 6.
     */
    public void drawCard(Player p, int index) throws GameEndedException {

        // auxiliary variables for special cases at the end of the game
        boolean illegal_draw = false;

        switch (index) {
            case 1:
                // Draw from Resource Deck
                if (!common_board.getResourceConcreteDeck().isEmpty())
                    p.getHand().add((ResourceCard) common_board.drawFromConcreteDeck(0));
                else {
                    illegal_draw = true;
                    observers.notify_illegalMoveBecauseOf(this, "Resource Deck is empty!");
                }
                break;
            case 2:
                // Draw first Resource Card from table
                if (common_board.getTableCards()[0][0] != null)
                    p.getHand().add((ResourceCard) common_board.drawFromTable(0, 0, 0));
                else {
                    illegal_draw = true;
                    observers.notify_illegalMoveBecauseOf(this, "Resource Table is empty!");
                }
                break;
            case 3:
                // Draw second Resource Card from table
                if (common_board.getTableCards()[0][1] != null)
                    p.getHand().add((ResourceCard) common_board.drawFromTable(0, 1, 0));
                else {
                    illegal_draw = true;
                    observers.notify_illegalMoveBecauseOf(this, "Resource Table is empty!");
                }
                break;
            case 4:
                // Draw from Gold Deck
                if (!common_board.getGoldConcreteDeck().isEmpty())
                    p.getHand().add((GoldCard) common_board.drawFromConcreteDeck(1));
                else {
                    illegal_draw = true;
                    observers.notify_illegalMoveBecauseOf(this, "Gold Deck is empty!");
                }
                break;
            case 5:
                // Draw first Gold Card from table
                if (common_board.getTableCards()[1][0] != null)
                    p.getHand().add((GoldCard) common_board.drawFromTable(1, 0, 1));
                else {
                    illegal_draw = true;
                    observers.notify_illegalMoveBecauseOf(this, "Gold Table is empty!");
                }
                break;
            case 6:
                // Draw second Gold Card from table
                if (common_board.getTableCards()[1][1] != null)
                    p.getHand().add((GoldCard) common_board.drawFromTable(1, 1, 1));
                else {
                    illegal_draw = true;
                    observers.notify_illegalMoveBecauseOf(this, "Gold Table is empty!");
                }
                break;
        }

        if (!illegal_draw) {
            observers.notify_cardDrawn(this, index);
            // at the end of every player round, we check for her/his points
            if (getStatus() == GameStatus.SECOND_LAST_ROUND && aux_order_players.getLast().equals(p))
                setStatus(GameStatus.LAST_ROUND);
            else if (p.getCurrentPoints() >= Constants.num_points_for_second_last_round) {
                if (aux_order_players.getLast().equals(p)) {
                    setStatus(GameStatus.LAST_ROUND);
                }
                else if (getStatus() == GameStatus.RUNNING)
                    setStatus(GameStatus.SECOND_LAST_ROUND);
            }
            nextTurn();
        }
        else if (common_board.getResourceConcreteDeck().isEmpty() && common_board.getGoldConcreteDeck().isEmpty()) {
            setStatus(GameStatus.LAST_ROUND);
            nextTurn();
        }
    }

    /**
     * Proceeds to the next turn of the game.
     * Throws a GameEndedException if the game has already ended or a GameNotStartedException if the game has not yet started.
     *
     * @throws GameEndedException    If the game has already ended.
     * @throws GameNotStartedException If the game has not yet started.
     */
    public void nextTurn() throws GameEndedException, GameNotStartedException {

        if (status.equals(GameStatus.ENDED)) {
            throw new GameEndedException();
        }
        else if (status.equals(GameStatus.WAIT)) {
            throw new GameNotStartedException();
        }

        // Proceeding to the next turn means changing the current player,
        // namely the peek of the queue.
        Player q = players_connected.poll();
        players_connected.offer(q);

        observers.notify_nextTurn(this);
    }

    /**
     * Calculates the final scores for all players based on their personal boards, chosen objective cards,
     * and common objectives.
     * Updates the final scores for each player and stores them in the {@code final_scores} array.
     */
    public void calculateFinalScores() {
        // Iterate over all players to calculate their final scores
        for (Player p : aux_order_players) {
            // Calculate the final score for the current player
            p.setFinalScore(p.getPersonalBoard().getPoints() +
                    p.getChosenObjectiveCard().calculateScore(p.getPersonalBoard()) +
                    common_board.getCommonObjectives().get(0).calculateScore(p.getPersonalBoard()) +
                    common_board.getCommonObjectives().get(1).calculateScore(p.getPersonalBoard()));
        }
    }

    /**
     * Calculates the winner(s) of the game based on final scores.
     * Updates the leaderboard and the list of winners accordingly.
     * It is called by getWinners() method.
     */
    // todo: leaderboard to check
    public void declareWinners() {

        List<Player> aux_final_scores_tie = new ArrayList<>();

        calculateFinalScores();

        // Sort players by final score in descending order
        List<Player> orderedPlayers = new ArrayList<>(aux_order_players);
        orderedPlayers.sort(Comparator.comparingInt(Player::getFinalScore).reversed());

        // Get the maximum score
        int maxScore = orderedPlayers.getFirst().getFinalScore();

        // check for a tie
        for (Player player : orderedPlayers) {
            if (player.getFinalScore() == maxScore) {
                aux_final_scores_tie.add(player);
            }
        }

        leaderboard.clear(); // Clear the leaderboard before adding new entries

        if (aux_final_scores_tie.size() == 1) {
            for (Player orderedPlayer : orderedPlayers) {
                leaderboard.put(orderedPlayer, orderedPlayer.getFinalScore());
            }
        }
        else {
            // aux_final_scores_tie.size() >= 2
            // another filtering:
            aux_final_scores_tie.sort(Comparator.comparingInt(Player::scoreOnlyObjectiveCards).reversed());
            StringBuilder aux = new StringBuilder();
            int counter = 0;
            for (Player p : aux_final_scores_tie) {
                if (p.scoreOnlyObjectiveCards() == aux_final_scores_tie.getFirst().scoreOnlyObjectiveCards()) {
                    aux.append(p.getNickname() + " ");
                    counter++;
                }
            }
            Player p_aux = new Player(aux.toString());
            p_aux.setFinalScore(aux_final_scores_tie.getFirst().getFinalScore());
            leaderboard.put(p_aux, p_aux.getFinalScore());
            for (int i = counter; i < orderedPlayers.size(); i++) {
                leaderboard.put(orderedPlayers.get(i), orderedPlayers.get(i).getFinalScore());
            }
        }
    }

    /**
     * show the personal board of the player with the nickname
     * @param playerNickname nickname of the player
     * @param playerIndex index of the player
     */
    public void showOthersPersonalBoard(String playerNickname, int playerIndex) {
        if(this.getAllPlayers().size() <= playerIndex)
            observers.notify_illegalMoveBecauseOf(this, "Player index out of bounds");
        else
            observers.notify_showOthersPersonalBoard(this, playerIndex, playerNickname);
    }

    /**
     * getter for the leaderboard
     * @return the leaderboard
     */
    public LinkedHashMap<Player, Integer> getLeaderboard() {
        return leaderboard;
    }

    /**
     * Retrieves the common board associated with the game.
     *
     * @return The common board object.
     */
    public CommonBoard getCommonBoard() {
        return this.common_board;
    }

    /**
     * setter for an error message
     * @param error the error message
     */
    public void setErrorMessage(String error) {}

    // **************************** Listeners ********************************

    /**
     * Adds a listener to the list of listeners.
     * @param obj adds the listener to the list
     */
    public void addListener(Listener obj) {
        observers.addListener(obj);
    }


    /**
     * Removes a listener from the list of listeners.
     * @param lis removes listener from list
     */
    public void removeListener(Listener lis) {
        observers.removeListener(lis);
    }

    // aux

    /**
     * getter for the starter cards to choose
     * @param nickname of the player
     * @return the list of starter cards to choose
     */
    public List<StarterCard> getStarterCardsToChoose(String nickname) {
        return getIdentityOfPlayer(nickname).getStarterCardToChose();
    }

    /**
     * getter for the personal objective cards to choose
     * @param nickname of the player
     * @return the list of personal objective cards to choose
     */
    public List<ObjectiveCard> getPersonalObjectiveCardsToChoose(String nickname) {
        return getIdentityOfPlayer(nickname).getSecretObjectiveCards();
    }


}

