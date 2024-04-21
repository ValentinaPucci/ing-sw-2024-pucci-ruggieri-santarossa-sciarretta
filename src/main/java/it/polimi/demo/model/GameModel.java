package it.polimi.demo.model;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.listener.ListenersHandler;
import it.polimi.demo.model.board.CommonBoard;
import it.polimi.demo.model.board.PersonalBoard;
import it.polimi.demo.model.cards.Card;
import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.objectiveCards.ObjectiveCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.cards.gameCards.StarterCard;
import it.polimi.demo.model.chat.Chat;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Coordinate;
import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.model.exceptions.*;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.interfaces.PlayerIC;

import java.util.*;

import static it.polimi.demo.networking.PrintAsync.printAsync;
//TODO: Check how first_finished_player is set.

public class GameModel {

    // the first list let us keep the order of players.
    // aux_order_player.size() always >= players_connected.size()
    private List<Player> aux_order_players;
    // the second one is used as a queue and let us know which player
    // is connected (and actively playing).
    private LinkedList<Player> players_connected;

    private ListenersHandler listener_handler;
    private CommonBoard common_board;

    private Integer gameId;
    private GameStatus status;
    private Chat chat;
    private Player first_finishing_player = null;
    private List<Player> winners;
    private Map<Player, Integer> leaderboard;

    public GameModel() {
        aux_order_players = new ArrayList<>();
        players_connected = new LinkedList<>();
        listener_handler = new ListenersHandler();
        common_board = new CommonBoard();

        Random random = new Random();
        gameId = random.nextInt(1000000);
        status = GameStatus.WAIT;
        chat = new Chat();
        winners = new ArrayList<>();
        leaderboard = new HashMap<>();
    }

    //------------------------------------methods for players-----------------------

    /**
     * Checks if there are enough players online to start the game.
     *
     * @return True if there are enough players online, otherwise false.
     */
    public boolean areConnectedPlayersEnough() {
        return players_connected.size() >= DefaultValues.MinNumOfPlayer;
    }

    /**
     * Checks if the player in turn is online.
     *
     * @return True if the player in turn is online, otherwise false.
     */
    public boolean isTheCurrentPlayerConnected() {
        return players_connected.peek() != null;
    }

    /**
     * Retrieves the beginner player of the game.
     *
     * @return The first player.
     */
    public Player getBeginnerPlayer() {
        return aux_order_players.getFirst();
    }

    /**
     * Retrieves the list of players participating in the game even if they are connected or not.
     *
     * @return The list of players.
     */
    public List<Player> getAllPlayers() {
        return aux_order_players;
    }

    public LinkedList<Player> getPlayersConnected() {
        return players_connected;
    }

    /**
     * @param p is set as ready, then everyone is notified
     */
    public void setPlayerAsReadyToStart(Player p) {
        p.setAsReadyToStart();
        listener_handler.notify_PlayerIsReadyToStart(this, p.getNickname());
    }

    /**
     * @return true if there are enough players to start, and if every one of them is ready
     */
    public boolean arePlayersReadyToStartAndEnough() {
        List<Player> p = aux_order_players.stream().filter(Player::getReadyToStart).toList();
        // If every player is ready, the game starts
        return p.containsAll(aux_order_players) && p.size() >= DefaultValues.MinNumOfPlayer;
    }

    /**
     * @param nick nickname of the player
     * @return player with the given nickname, or null if not found
     */
    public Player getPlayerEntity(String nick) {
        return aux_order_players.stream()
                .filter(x -> x.getNickname().equals(nick))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves the index of the first player who finished the game.
     *
     * @return The index of the first player who finished the game, or -1 if no player has finished yet.
     */
    public Player getFirstFinishingPlayer() {
        return this.first_finishing_player;
    }


    /**
     * It sets the first player that finishes.
     * @param p
     */
    public void setFirst_finished_player(Player p){ this.first_finishing_player = p;}


    /**
     * Adds a new player to the game. Recall that if a player was previously playing,
     * then we are able to retrieve him/her from the auxiliary ordered list.
     * Thus, this method is meant as an adder of new players, not as an adder of disconnected players
     * @param p player to add
     */
    public void addPlayer(Player p) {

        if (aux_order_players.contains(p)) {
            listener_handler.notify_JoinUnableNicknameAlreadyIn(p);
            throw new PlayerAlreadyConnectedException();
        }
        else if (aux_order_players.size() >= DefaultValues.MaxNumOfPlayer) {
            listener_handler.notify_JoinUnableGameFull(p, this);
            throw new MaxPlayersLimitException();
        }
        else {
            aux_order_players.add(p);
        }
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

        if (this.status.equals(GameStatus.RUNNING)) {
            if (players_connected.size() < DefaultValues.MinNumOfPlayer) {
                // Not enough players to keep playing
                this.setStatus(GameStatus.ENDED);
            }
        }
    }

    //-------------------------listeners---------------------------------------------

    /**
     * Retrieves the ListenersHandler object associated with the game.
     *
     * @return The ListenersHandler object used for managing game event listeners.
     */
    public ListenersHandler getListenersHandler() {
        return this.listener_handler;
    }

    /**
     * @param lis adds the listener to the list
     */
    public void addListener(GameListener lis) {
        listener_handler.addListener(lis);
    }

    /**
     * @param lis removes listener from list
     */
    public void removeListener(GameListener lis) {
        listener_handler.removeListener(lis);
    }

    /**
     * @return the list of listeners
     */
    public Set<GameListener> getListeners() {
        return listener_handler.getListeners();
    }

    //-------------------------chat and messages---------------------------------------------

    /**
     * Retrieves the chat object associated with the game.
     *
     * @return The chat object used for communication among players.
     */
    public Chat getChat() {
        return this.chat;
    }

     /**
     * Sends a message to the game's chat.
     * If the sender of the message is not a player in the game, an exception is thrown.
     *
     * @param m The message to be sent.
     * @throws ActionPerformedByAPlayerNotPlayingException If the sender of the message is not a player in the game.
     */
    public void sendMessage(Message m) throws ActionPerformedByAPlayerNotPlayingException {
        if (players_connected.contains(m.getSender())) {
            // Add the message to the chat
            chat.addMsg(m);
            // Notify listeners about the sent message
            listener_handler.notify_SentMessage(this, chat.getLastMessage());
        } else {
            // Throw an exception if the sender is not a player in the game
            throw new ActionPerformedByAPlayerNotPlayingException();
        }
    }

    //-------------------------connection/disconnection management---------------------------------------------

    /**
     * Sets the player p as disconnected, it removes p from the players_connected list.
     * @param p player to disconnect
     */
    public void setPlayerAsDisconnected(Player p) {

        if (players_connected.contains(p)) {
            p.setAsNotConnected();
            p.setAsNotReadyToStart();
            players_connected.remove(p);
            listener_handler.notify_playerDisconnected(this, p.getNickname());
        }

        if ((this.status.equals(GameStatus.RUNNING) ||
                this.status.equals(GameStatus.SECOND_LAST_ROUND) ||
                this.status.equals(GameStatus.LAST_ROUND))
                && aux_order_players.size() == 1) {
            listener_handler.notify_onlyOnePlayerConnected(this, DefaultValues.secondsToWaitReconnection);
        }
    }

    /**
     * It requires player.isConnected() == true.
     * Add the player, that is connected, to the players_connected list, notify that the player is connected.
     * If the player is already connected, it throws exception.
     * @param p player to set as connected
     */
    public void setPlayerAsConnected(Player p) {
        if (aux_order_players.contains(p) && p.getIsConnected()) {
            players_connected.offer(p);
            listener_handler.notify_playerJoined(this);
        }
        else {
            throw new IllegalArgumentException("Player not in the game!");
        }
    }

    /**
     *
     * @param p player is reconnected
     * @throws PlayerAlreadyConnectedException player is already in
     * @throws MaxPlayersLimitException there's already 4 players in game
     * @throws GameEndedException the game has ended
     */
    public void reconnectPlayer(Player p) throws GameEndedException {

        if (players_connected.contains(p)) {
            printAsync("ERROR: Trying to reconnect a player not offline!");
            throw new PlayerAlreadyConnectedException();
        }

        p.setAsConnected();
        connectPlayerInOrder(p);

        listener_handler.notify_playerReconnected(this, p.getNickname());
    }

    /**
     * It connects a player in the right place (by order), that had already joined the game, but was disconnected,
     * so that the game will continue with the correct order of the game (aux_order_players).
     * (It also cares about the limit case of the first element).
     * @param p
     */
    public void connectPlayerInOrder(Player p) {
        // index of previous player in aux_order_players

        Player q;
        int index_to_add = -1;

        if (!aux_order_players.contains(p)) {
            throw new IllegalArgumentException("Trying to connect a player which is not in the game!");
        }

        int index = aux_order_players.indexOf(p) - 1;

        if (index < 0)
            q = aux_order_players.getLast();
        else
            q = aux_order_players.get(index);

        for (Player s : players_connected) {
            if (s.equals(q))
                index_to_add = players_connected.indexOf(s) + 1;

        }
        if(index_to_add != -1)
            players_connected.add(index_to_add , p);

    }

    //-------------------------managing status---------------------------------------------

    /**
     * Gets the game id
     *
     * @return gameId
     */
    public Integer getGameId() {
        return this.gameId;
    }

    /**
     * Sets the game id
     *
     * @param gameId new game id
     */
    public void setGameId(Integer gameId) {
        this.gameId = gameId;
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
    public void setStatus(GameStatus status) throws NotReadyToRunException {
        // Check if the game status can be set to "RUNNING"
        boolean canSetRunning =
                status.equals(GameStatus.RUNNING) &&
                (aux_order_players.size() >= DefaultValues.MinNumOfPlayer) &&
                !players_connected.isEmpty();

        // Set the game status and notify listeners based on the new status
        if (canSetRunning) {
            this.status = status;
            switch (status) {
                case RUNNING:
                    listener_handler.notify_GameStarted(this);
                    listener_handler.notify_nextTurn(this);
                    break;
                case SECOND_LAST_ROUND:
                    listener_handler.notify_SecondLastRound(this);
                    break;
                case LAST_ROUND:
                    listener_handler.notify_LastRound(this);
                    break;
                case ENDED:
                    listener_handler.notify_GameEnded(this);
                    break;
                default:
                    break;
            }
        } else {
            throw new NotReadyToRunException();
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
        dealCards();
    }

    /**
     * Deals cards to each player, including starter cards, resource cards, gold cards and secret objective cards.
     */
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
        for (Player player : aux_order_players) {
            // Deal 1 starter card
            if (!common_board.getStarterConcreteDeck().isEmpty()) {
                StarterCard starterCard = (StarterCard) common_board.getStarterConcreteDeck().pop();
                player.setStarterCard(starterCard);
            }

            // Deal 2 resource cards
            for (int i = 0; i < 2; i++) {
                if (!common_board.getResourceConcreteDeck().isEmpty()) {
                    Card resourceCard = common_board.getResourceConcreteDeck().pop();
                    player.addToHand(resourceCard);
                }
            }

            // Deal 1 gold card
            if (!common_board.getGoldConcreteDeck().isEmpty()) {
                Card goldCard = common_board.getGoldConcreteDeck().pop();
                player.addToHand(goldCard);
            }

            // Deal 2 secret objective cards
            for (int i = 0; i < 2; i++) {
                if (!common_board.getObjectiveConcreteDeck().isEmpty()) {
                    ObjectiveCard objectiveCard1 = (ObjectiveCard) common_board.getObjectiveConcreteDeck().pop();
                    ObjectiveCard objectiveCard2 = (ObjectiveCard) common_board.getObjectiveConcreteDeck().pop();
                    player.setSecretObjectives(objectiveCard1, objectiveCard2);
                }
            }

        }
    }

    // for resource cards
    public void placeCard(ResourceCard card_chosen, Player p, int x, int y)  {
        PersonalBoard personal_board = p.getPersonalBoard();

        if (!personal_board.board[x][y].is_full) {
            throw new IllegalMoveException();
        }
        else {
            if (DefaultValues.NW_StarterCard_index[1] <= x && x <= DefaultValues.NE_StarterCard_index[1] &&
                    DefaultValues.NW_StarterCard_index[0] <= y && y <= DefaultValues.SW_StarterCard_index[0]) {
                StarterCard already_placed_card = p.getStarterCard();
                Coordinate coord = already_placed_card.getCoordinateAt(x, y);
                personal_board.placeCardAt(already_placed_card, card_chosen, coord);
            }
            else {
                ResourceCard already_placed_card = personal_board.board[x][y].getCornerFromCell().reference_card;
                Coordinate coord = personal_board.board[x][y].getCornerFromCell().getCoordinate();
                personal_board.placeCardAt(already_placed_card, card_chosen, coord);
            }
        }
    }

    // for gold cards
    public void placeCard(GoldCard card_chosen, Player p, int x, int y)  {
        PersonalBoard personal_board = p.getPersonalBoard();

        if (!personal_board.board[x][y].is_full) {
            throw new IllegalMoveException();
        }
        else {
            if (DefaultValues.NW_StarterCard_index[1] <= x && x <= DefaultValues.NE_StarterCard_index[1] &&
                    DefaultValues.NW_StarterCard_index[0] <= y && y <= DefaultValues.SW_StarterCard_index[0]) {
                StarterCard already_placed_card = p.getStarterCard();
                Coordinate coord = already_placed_card.getCoordinateAt(x, y);
                personal_board.placeCardAt(already_placed_card, card_chosen, coord);
            }
            else {
                ResourceCard already_placed_card = personal_board.board[x][y].getCornerFromCell().reference_card;
                Coordinate coord = personal_board.board[x][y].getCornerFromCell().getCoordinate();
                personal_board.placeCardAt(already_placed_card, card_chosen, coord);
            }
        }
    }

    /**
     * Draw a card based on the provided index on the board (by the client)
     * @param index The index indicating which card to draw:
     *              1: Resource Deck
     *              2: First Resource Card on the table
     *              3: Second Resource Card on the table
     *              4: Gold Deck
     *              5: First Gold Card on the table
     *              6: Second Gold Card on the table
     * @throws IllegalArgumentException If the index is less than 1 or greater than 6.
     */

    public void drawCard(Player p, int index) {
        if (index < 1 || index > 6) {
            throw new IllegalArgumentException("It is not possible to draw a card from here");
        }

        switch (index) {
            case 1:
                p.getHand().add((ResourceCard) common_board.drawFromConcreteDeck(0)); // Draw from Resource Deck
            case 2:
                p.getHand().add((ResourceCard) common_board.drawFromTable(0, 0, 0)); // Draw first Resource Card from table
            case 3:
                p.getHand().add((ResourceCard) common_board.drawFromTable(0, 1, 0)); // Draw second Resource Card from table
            case 4:
                p.getHand().add((GoldCard) common_board.drawFromConcreteDeck(1)); // Draw from Gold Deck
            case 5:
                p.getHand().add((GoldCard) common_board.drawFromTable(1, 0, 1)); // Draw first Gold Card from table
            case 6:
                p.getHand().add((GoldCard) common_board.drawFromTable(1, 1, 1)); // Draw second Gold Card from table
        }
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
     */
    public void declareWinners() {

        int maxScore;
        List<Player> ordered_players = new ArrayList<>(aux_order_players);

        calculateFinalScores();

        ordered_players.sort(Comparator.comparingInt(Player::getFinalScore).reversed());

        maxScore = aux_order_players.stream()
                .mapToInt(Player::getFinalScore)
                .max()
                .getAsInt();

        aux_order_players.stream()
                .filter(player -> player.getFinalScore() >= maxScore)
                .forEach(this.winners::add);

        for (Player orderedPlayer : ordered_players) {
            leaderboard.put(orderedPlayer, orderedPlayer.getFinalScore());
        }
    }

    /**
     * Retrieves the winner(s) of the game.
     *
     * @return A list of Player objects representing the winner(s).
     */
    public List<Player> getWinners(){
        return this.winners;
    }


    /**
     * Retrieves the leaderboard containing player indexes and their corresponding scores.
     *
     * @return The leaderboard map with player indexes as keys and scores as values.
     */
    public Map<Player, Integer> getLeaderboard() {
        return this.leaderboard;
    }

    /**
     * Proceeds to the next turn of the game.
     * Throws a GameEndedException if the game has already ended or a GameNotStartedException if the game has not yet started.
     *
     * @throws GameEndedException    If the game has already ended.
     * @throws GameNotStartedException If the game has not yet started.
     */
    public void nextTurn() throws GameEndedException, GameNotStartedException {

        if (status.equals(GameStatus.ENDED) || first_finishing_player != null) {
            throw new GameEndedException();
        } else if (!status.equals(GameStatus.FIRST_ROUND) &&
                !status.equals(GameStatus.RUNNING) &&
                !status.equals(GameStatus.SECOND_LAST_ROUND) &&
                !status.equals(GameStatus.LAST_ROUND)) {
            throw new GameNotStartedException();
        }

        while (players_connected.size() <= 1) {
            // we wait for another player to connect
            // IDEA: look for timeout in the requirements of disconnection
        }

        Player q = players_connected.poll();
        players_connected.offer(q);

        // Notify listeners about the next turn
        listener_handler.notify_nextTurn(this);
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
     * Retrieves the concrete deck containing resource cards.
     *
     * @return The concrete deck of resource cards.
     */
    public ConcreteDeck getResourceDeck() {
        return this.common_board.getResourceConcreteDeck();
    }

    /**
     * Retrieves the concrete deck containing gold cards.
     *
     * @return The concrete deck of gold cards.
     */
    public ConcreteDeck getGoldDeck() {
        return this.common_board.getGoldConcreteDeck();
    }

    /**
     * Retrieves the concrete deck containing objective cards.
     *
     * @return The concrete deck of objective cards.
     */
    public ConcreteDeck getObjectiveDeck() {
        return this.common_board.getObjectiveConcreteDeck();
    }

    /**
     * Retrieves the concrete deck containing starter cards.
     *
     * @return The concrete deck of starter cards.
     */
    public ConcreteDeck getStarterDeck() {
        return this.common_board.getStarterConcreteDeck();
    }

}

