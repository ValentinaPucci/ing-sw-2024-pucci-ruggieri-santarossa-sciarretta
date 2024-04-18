package it.polimi.demo.model;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.listener.ListenersHandler;
import it.polimi.demo.model.board.CommonBoard;
import it.polimi.demo.model.board.PersonalBoard;
import it.polimi.demo.model.cards.Card;
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

public class GameModel {

    private Map<Player, Integer> leaderBoard;
    private Integer gameId;
    private int index_current_player;
    private List<Player> players;
    private Chat chat;
    private GameStatus status;
    private ListenersHandler listener_handler;
    private List<Integer> final_scores;
    private Integer first_finished_player = -1;
    private List<Player> winners;
    private CommonBoard common_board;
    private Player first_player = null;

    public GameModel() {
        leaderBoard = new HashMap<>();
        Random random = new Random();
        gameId = random.nextInt(1000000);
        index_current_player = -1;
        players = new ArrayList<>();
        chat = new Chat();
        status = GameStatus.WAIT;
        listener_handler = new ListenersHandler();
        final_scores = new ArrayList<>();
        winners = new ArrayList<>();
        common_board = new CommonBoard();
    }

    public GameModel(List<Player> players, CommonBoard common_board, Integer gameId) {
        this.players = new ArrayList<>(players);
        this.common_board = common_board;
        this.gameId = gameId;
        this.index_current_player = -1;
        this.chat = new Chat();
        this.status = GameStatus.WAIT;
        this.listener_handler = new ListenersHandler();
    }

    //------------------------------------methods for players-----------------------

    /**
     * @return the number of players
     */
    public int getNumPlayers() {
        return players.size();
    }

    /**
     * Finds the index of the player with the specified nickname.
     * @return The index of the player in the players list, or -1 if not found.
     */
    public int getPlayerIndex(Player p1) {
        // Iterate through the players list
        for (int i = 0; i < players.size(); i++) {
            Player p2 = players.get(i);
            // Check if the player's nickname matches the specified nickname
            if (p2.equals(p1)) {
                return i; // Return the index of the matching player
            }
        }
        return -1; // Return -1 if the player with the specified nickname is not found
    }

    /**
     * Retrieves the number of players who are currently connected.
     *
     * @return The number of connected players.
     */
    public int getNumOnlinePlayers() {
        return (int) players.stream().filter(Player::getIsConnected).count();
    }

    public boolean areOnlinePlayersEnough() {
        return getNumOnlinePlayers() >= DefaultValues.MinNumOfPlayer;
    }

    /**
     * Checks if the player in turn is online.
     *
     * @return True if the player in turn is online, otherwise false.
     */
    private boolean isTheCurrentPlayerOnline() {
        return players.get(index_current_player).getIsConnected();
    }

    /**
     * Retrieves the first player of the game.
     *
     * @return The first player.
     */
    public Player getFirstPlayer() {
        return first_player;
    }

    /**
     * Sets the first player of the game.
     *
     * @param player The player to set as the first player.
     */
    public void setFirstPlayer(Player player) {
        this.first_player = player;
    }

    /**
     * Retrieves the list of players participating in the game.
     *
     * @return The list of players.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Sets the current playing player to the specified player.
     *
     * @param player The player to set as the current player.
     */
    public void setCurrentPlayer(Player player) {
        this.index_current_player = getPlayerIndex(player);
    }

    /**
     * Retrieves the index of the current playing player.
     *
     * @return The index of the current playing player.
     */
    public int getIndexCurrentPlayer() {
        return this.index_current_player;
    }

    /**
     * Adds a player to the game.
     *
     * @param player The player to add to the game.
     * @throws PlayerAlreadyConnectedException If the player is already connected to the game.
     * @throws MaxPlayersLimitException If the game is already full and cannot accept more players.
     */
    public void addPlayer(Player player) {
        // Checking if the game is already full
        if (players.size() >= DefaultValues.MaxNumOfPlayer) {
            // Notifying listeners that the game is full
            // and throwing an exception
            listener_handler.notify_JoinUnableGameFull(player, this);
            throw new MaxPlayersLimitException();
        }

        // Checking if the player is not already in the game
        // Note that .contains calls .equals, which we override in Player class
        // Thus, this is the same as checking if there is already a player with the same nickname
        if (players.contains(player)) {
            // Notifying listeners that the player is already connected
            // and throwing an exception
            listener_handler.notify_JoinUnableNicknameAlreadyIn(player);
            throw new PlayerAlreadyConnectedException();
        } else {
            // Adding the player to the game and notifying listeners
            players.add(player);
            listener_handler.notify_playerJoined(this);
        }
    }

    /**
     * Removes the player with the specified nickname from the game.
     *
     * @param nick The nickname of the player to remove.
     */
    public void removePlayer(String nick) {

        players.stream()
                .filter(player -> player.getNickname().equals(nick))
                .findFirst()
                .ifPresent(playerToRemove -> {
                    players.remove(playerToRemove);
                    listener_handler.notify_playerLeft(this, nick);
                });

        if (this.status.equals(GameStatus.RUNNING)) {
            long connectedPlayersCount = players.stream()
                    .filter(Player::getIsConnected)
                    .count();
            if (connectedPlayersCount <= 1) {
                // Not enough players to keep playing
                this.setStatus(GameStatus.ENDED);
            }
        }
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
        //If every player is ready, the game starts
        return players.stream().filter(Player::getReadyToStart)
                .count() == players.size() && players.size() >= DefaultValues.MinNumOfPlayer;
    }

    /**
     * @param nick nickname of the player
     * @return player with the given nickname, or null if not found
     */
    public Player getPlayerEntity(String nick) {
        return players.stream()
                .filter(x -> x.getNickname().equals(nick))
                .findFirst()
                .orElse(null);
    }

    /**
     * Set the index of the player playing the first turn
     *
     * @param index of the player
     */
    public void setFirstTurnIndex(int index) {
        first_player = players.get(index);
    }

    /**
     * Retrieves the index of the player who took the first turn in the game.
     *
     * @return The index of the player who took the first turn.
     */
    public Integer getFirstTurnIndex() {
        return this.getPlayerIndex(first_player);
    }

    /**
     * Retrieves the index of the first player who finished the game.
     *
     * @return The index of the first player who finished the game, or -1 if no player has finished yet.
     */
    public Integer getFirstFinishingPlayer() {
        return this.first_finished_player;
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
     * @param obj adds the listener to the list
     */
    public void addListener(GameListener obj) {
        listener_handler.addListener(obj);
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
    public List<GameListener> getListeners() {
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
        if (players.contains(m.getSender())) {
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
     * Sets the player with the specified nickname as disconnected.
     *
     * @param nick The nickname of the player to set as disconnected.
     */
    // TODO: revision (write an original code)
    public void setAsDisconnected(String nick) throws GameEndedException {

        if (players.contains(getPlayerEntity(nick))) {
            // Set the player as not connected and not ready to start
            getPlayerEntity(nick).setAsNotConnected();
            getPlayerEntity(nick).setAsNotReadyToStart();
        }
        else
            throw new IllegalArgumentException("Player not in the game!");

        // If there is at least one player online remained, we notify the disconnection
        if (getNumOnlinePlayers() >= 1)
            listener_handler.notify_playerDisconnected(this, nick);

        // If there are at least two online players and the current player is offline, proceed to the next turn
        if (areOnlinePlayersEnough() && !isTheCurrentPlayerOnline()) {
            nextTurn();
        }

        // If the game is in progress and only one player is online,
        // notify and set a timeout for reconnection
        if ((this.status.equals(GameStatus.RUNNING) ||
                this.status.equals(GameStatus.SECOND_LAST_ROUND) ||
                this.status.equals(GameStatus.LAST_ROUND))
                && getNumOnlinePlayers() == 1) {
            listener_handler.notify_onlyOnePlayerConnected(this,
                    DefaultValues.secondsToWaitReconnection);
        }
    }

    /**
     * @param p player is reconnected
     * @throws PlayerAlreadyConnectedException player is already in
     * @throws MaxPlayersLimitException there's already 4 players in game
     * @throws GameEndedException the game has ended
     */
    public void reconnectPlayer(Player p) throws GameEndedException {

        if (isPlayerAlreadyConnected(p)) {
            printAsync("ERROR: Trying to reconnect a player not offline!");
            throw new PlayerAlreadyConnectedException();
        }

        p.setAsConnected();
        listener_handler.notify_playerReconnected(this, p.getNickname());

        if (!isTheCurrentPlayerOnline()) {
            nextTurn();
        }
    }

    public boolean isPlayerAlreadyConnected(Player p) {
        // Check if the player is already in the list of connected players
        return players.stream()
                .anyMatch(x -> x.equals(p) && x.getIsConnected());
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
    // TODO: revision (write an original code)
    public void setStatus(GameStatus status) throws NotReadyToRunException {
        // Check if the game status can be set to "RUNNING"
        boolean canSetRunning = status.equals(GameStatus.RUNNING) &&
                (players.size() >= DefaultValues.MinNumOfPlayer) &&
                index_current_player != -1;

        // Set the game status and notify listeners based on the new status
        if (canSetRunning) {
            this.status = status;
            switch (status) {
                case RUNNING:
                    listener_handler.notify_GameStarted(this);
                    listener_handler.notify_nextTurn(this);
                    break;
                case SECOND_LAST_ROUND:
                    listener_handler.notify_SecondLastTurn(this);
                    break;
                case LAST_ROUND:
                    listener_handler.notify_LastTurn(this);
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
        // Deal cards to players
        for (Player player : players) {
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

    public void placeCard(ResourceCard card_chosen, Player p, int x, int y) throws
            IllegalMoveException {
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
     * @return The drawn card.
     * @throws IllegalArgumentException If the index is less than 1 or greater than 6.
     */
    public Card drawCard(int index) {

        if (index < 1 || index > 6) {
            throw new IllegalArgumentException("It is not possible to draw a card from here");
        }

        switch (index) {
            case 1:
                return common_board.drawFromConcreteDeck(0); // Draw from Resource Deck
            case 2:
                return common_board.drawFromTable(0, 0, 0); // Draw first Resource Card from table
            case 3:
                return common_board.drawFromTable(0, 1, 0); // Draw second Resource Card from table
            case 4:
                return common_board.drawFromConcreteDeck(1); // Draw from Gold Deck
            case 5:
                return common_board.drawFromTable(1, 0, 1); // Draw first Gold Card from table
            case 6:
                return common_board.drawFromTable(1, 1, 1); // Draw second Gold Card from table
            default:
                return null;
        }
    }

    /**
     * Calculates the final scores for all players based on their personal boards, chosen objective cards,
     * and common objectives.
     * Updates the final scores for each player and stores them in the {@code final_scores} array.
     */
    public void calculateFinalScores() {
        // Iterate over all players to calculate their final scores
        for (int i = 0; i < getNumPlayers(); i++) {
            // Calculate the final score for the current player
            int finalScore = players.get(i).getPersonalBoard().getPoints() +
                    players.get(i).getChosenObjectiveCard().calculateScore(players.get(i).getPersonalBoard()) +
                    common_board.getCommonObjectives().get(0).calculateScore(players.get(i).getPersonalBoard()) +
                    common_board.getCommonObjectives().get(1).calculateScore(players.get(i).getPersonalBoard());

            // Update the final score for the current player
            // final_scores[i] is the final score of player indexed by i in players list
            this.final_scores.add(i, finalScore);
            players.get(i).setFinalScore(finalScore);
        }
    }

    /**
     * Calculates the winner(s) of the game based on final scores.
     * Updates the leaderboard and the list of winners accordingly.
     */
    public void declareWinners() {
        // Initialize variables
        int maxScore = 0;
        List<Player> ordered_players = new ArrayList<>(players);
        ordered_players.sort(Comparator.comparingInt(Player::getFinalScore).reversed());

        maxScore = players.stream()
                .mapToInt(Player::getFinalScore)
                .max()
                .getAsInt();

        for (Player player : players) {
            if (player.getFinalScore() >= maxScore) {
                this.winners.add(player);
            }
        }

        for (Player orderedPlayer : ordered_players) {
            leaderBoard.put(orderedPlayer, orderedPlayer.getFinalScore());
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
        return this.leaderBoard;
    }

    /**
     * Proceeds to the next turn of the game.
     * Throws a GameEndedException if the game has already ended or a GameNotStartedException if the game has not yet started.
     *
     * @throws GameEndedException    If the game has already ended.
     * @throws GameNotStartedException If the game has not yet started.
     */
    public void nextTurn() throws GameEndedException, GameNotStartedException {

        if (status.equals(GameStatus.ENDED) || first_finished_player != -1) {
            throw new GameEndedException();
        } else if (!status.equals(GameStatus.FIRST_ROUND) &&
                !status.equals(GameStatus.RUNNING) &&
                !status.equals(GameStatus.SECOND_LAST_ROUND) &&
                !status.equals(GameStatus.LAST_ROUND)) {
            throw new GameNotStartedException();
        }

        if (!areOnlinePlayersEnough()) {
            // we proceed, but we need to wait for the next player to reconnect
            index_current_player = (index_current_player + 1) % players.size();
        }
        else {
            index_current_player = nextPlayerConnectedIndex();
        }
        // Notify listeners about the next turn
        listener_handler.notify_nextTurn(this);
    }

    public int nextPlayerConnectedIndex() {
        int next_index = (index_current_player + 1) % players.size();
        while (!players.get(next_index).getIsConnected()) {
            next_index = (index_current_player + 1) % players.size();
        }
        return next_index;
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

    /**
     * Retrieves the final scores of the players.
     *
     * @return An array containing the final scores of the players.
     */
    public List<Integer> getFinalScores() {
        return this.final_scores;
    }

}

