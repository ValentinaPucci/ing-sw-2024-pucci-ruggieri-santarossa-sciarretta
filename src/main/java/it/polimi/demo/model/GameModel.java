package it.polimi.demo.model;

import it.polimi.demo.listener.ListenersHandler;
import it.polimi.demo.DefaultValues;
import it.polimi.demo.listener.GameListener;
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

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;

import static it.polimi.demo.networking.PrintAsync.printAsync;


public class GameModel implements Serializable {

    // the first list let us keep the order of players. It is immutable!!
    // aux_order_player.size() always >= players_connected.size()
    private final List<Player> aux_order_players;
    // the second one is used as a queue and let us know which player
    // is connected (and actively playing).
    private final LinkedList<Player> players_connected;

    private final Random random = new Random();

    private Player initial_player;
    private CommonBoard common_board;
    private int gameId;
    private int num_required_players_to_start;
    private GameStatus status;
    private Chat chat;
    private List<Player> winners;
    private Map<Player, Integer> leaderboard;

    /**
     * Listener handler that handles the listeners
     */
    private ListenersHandler listeners_handler;

    public GameModel() {
        aux_order_players = new ArrayList<>();
        players_connected = new LinkedList<>();
        common_board = new CommonBoard();
        Random random = new Random();
        gameId = random.nextInt(1000000);
        num_required_players_to_start = -1; // invalid value on purpose
        status = GameStatus.WAIT;
        chat = new Chat();
        winners = new ArrayList<>();
        leaderboard = new HashMap<>();
        listeners_handler = new ListenersHandler();
    }

    public GameModel(int gameID, int numberOfPlayers, Player player) {
        aux_order_players = new ArrayList<>();
        players_connected = new LinkedList<>();
        common_board = new CommonBoard();
        num_required_players_to_start = numberOfPlayers;
        initial_player = player;
        gameId = gameID;
        status = GameStatus.WAIT;
        chat = new Chat();
        winners = new ArrayList<>();
        leaderboard = new HashMap<>();
        listeners_handler = new ListenersHandler();
    }

    //------------------------------------methods for players-----------------------

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


    public List<String> getAllNicknames() {
        return aux_order_players.stream()
                .map(Player::getNickname)
                .collect(Collectors.toList());
    }

    public LinkedList<Player> getPlayersConnected() {
        return players_connected;
    }

    /**
     * @param p is set as ready, then everyone is notified
     */
    public synchronized void setPlayerAsReadyToStart(Player p) {
        p.setAsReadyToStart();
        listeners_handler.notify_PlayerIsReadyToStart(this, p.getNickname());
        if (arePlayersReadyToStartAndEnough()) {
            extractFirstPlayerToPlay();
            initializeGame();
            setStatus(GameStatus.FIRST_ROUND);
        }
    }

    public synchronized void extractFirstPlayerToPlay() {
        Player first_player = players_connected.get(random.nextInt(players_connected.size()));
        aux_order_players.remove(first_player);
        aux_order_players.addFirst(first_player);
        players_connected.remove(first_player);
        players_connected.addFirst(first_player);
    }

    /**
     * @return true if there are enough players to start, and if every one of them is ready
     */
    public boolean arePlayersReadyToStartAndEnough() {
        List<Player> p = players_connected.stream().filter(Player::getReadyToStart).toList();
        return p.containsAll(players_connected) && p.size() == num_required_players_to_start;
    }

    /**
     * @param nick nickname of the player
     * @return player with the given nickname, or null if not found
     */
    public Player getPlayerEntity(String nick) {
        for (Player player : players_connected) {
            if (player.getNickname().equals(nick)) {
                return player;
            }
        }
        return null;
    }

    public void setNumPlayersToPlay(int n) {
        num_required_players_to_start = n;
    }

    public int getNumPlayersToPlay() {
        return num_required_players_to_start;
    }

    /**
     * Adds a new player to the game. Recall that if a player was previously playing,
     * then we are able to retrieve him/her from the auxiliary ordered list.
     * Thus, this method is meant as an adder of new players, not as an adder of disconnected players.
     * Statical add.
     */
    public void addPlayer(Player p) {

        List<String> nicknames = this.getAllNicknames();
        String nickname = p.getNickname();

        if (nicknames.contains(nickname)) {
            listeners_handler.notify_joinUnableNicknameAlreadyIn(getPlayerEntity(nickname));
            throw new PlayerAlreadyConnectedException();
        }
        else if (aux_order_players.size() >= num_required_players_to_start ||
                aux_order_players.size() >= DefaultValues.MaxNumOfPlayer) {
            listeners_handler.notify_joinUnableGameFull(getPlayerEntity(nickname), this);
            throw new MaxPlayersLimitException();
        }
        else {
            aux_order_players.add(p);
            players_connected.offer(p);
            listeners_handler.notify_playerJoined(this);
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

        listeners_handler.notify_playerLeft(this, p.getNickname());

        if (players_connected.size() < 2) {
            listeners_handler.notify_GameEnded(this);
        }
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
     * Sends a message to the chat.
     * @param nick
     * @param message
     * @throws ActionPerformedByAPlayerNotPlayingException
     */
    public void sendMessage(String nick, Message message) throws ActionPerformedByAPlayerNotPlayingException {
        chat.addMessage(message);
        listeners_handler.notify_messageSent(this, nick, message);
    }

    //-------------------------connection/disconnection management---------------------------------------------

    /**
     * Sets the player p as disconnected, it removes p from the players_connected list.
     * @param p player to disconnect
     */
    public void setPlayerAsDisconnected(Player p) {

        if (players_connected.contains(p)) {
            p.setAsNotConnected();
            listeners_handler.notify_playerDisconnected(this, p.getNickname());
            p.setAsNotReadyToStart();
            players_connected.remove(p);
        }
    }

    /**
     * It requires player.isConnected() == true.
     * Add the player, that is connected, to the players_connected list, notify that the player is connected.
     * If the player is already connected, it throws exception. Dynamical add (connection).
     * @param p player to set as connected.
     */
    public void setPlayerAsConnected(Player p) {
        if (aux_order_players.contains(p) && !players_connected.contains(p)) {
            // Here we bypass the question 'are you ready to start?'
            p.setAsReadyToStart();
        }
        else {
            throw new IllegalArgumentException("Player not in the game!");
        }
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
                listeners_handler.notify_GameStarted(this);
                listeners_handler.notify_nextTurn(this);
            }

            case SECOND_LAST_ROUND -> {
                listeners_handler.notify_secondLastRound(this);
            }

            case LAST_ROUND -> {
                listeners_handler.notify_lastRound(this);
            }

            case ENDED -> {
                listeners_handler.notify_GameEnded(this);
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
        dealCards();
    }

    public void chooseCardFromHand(Player p, int which_card) {
        if (getStatus() == GameStatus.FIRST_ROUND)
            p.setChosenObjectiveCard(p.getSecretObjectiveCards().get(which_card));
        else {
            if (p.getHand().get(which_card) instanceof GoldCard)
                p.setChosenGameCard((GoldCard) p.getHand().get(which_card));
            else
                p.setChosenGameCard(p.getHand().get(which_card));
            listeners_handler.notify_cardChosen(this, which_card);
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

    public void placeStarterCard(Player p, Orientation o) throws GameEndedException, RemoteException {

        PersonalBoard personal_board = p.getPersonalBoard();

        if (o == Orientation.FRONT) {p.setStarterCard(p.getStarterCardToChose().get(0));
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
        listeners_handler.notify_starterCardPlaced(this, o, p.getNickname());
        nextTurn();
    }

    // for resource cards
    public void placeCard(ResourceCard card_chosen, Player p, int x, int y) throws GameEndedException {

        PersonalBoard personal_board = p.getPersonalBoard();
        int old_points = p.getCurrentPoints();
        boolean admissible_move;

        if (!personal_board.board[x][y].is_full) {
            listeners_handler.notify_illegalMove(this);
        }
        else {
            if (250 <= x && x <= 251 && 250 <= y && y <= 251) {
                StarterCard already_placed_card = p.getStarterCard();
                Coordinate coord = already_placed_card.getCoordinateAt(x, y);
                admissible_move = personal_board.placeCardAt(already_placed_card, card_chosen, coord);
                if (!admissible_move) {
                    listeners_handler.notify_illegalMove(this);
                }
                else {
                    // remove the card from the player's hand
                    p.getHand().remove(p.getChosenGameCard());
                    getCommonBoard().movePlayer(aux_order_players.indexOf(p), p.getCurrentPoints() - old_points);
                    listeners_handler.notify_cardPlaced(this, x, y, card_chosen.orientation);
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
                    listeners_handler.notify_illegalMove(this);
                }
                else {
                    // remove the card from the player's hand
                    p.getHand().remove(p.getChosenGameCard());
                    getCommonBoard().movePlayer(aux_order_players.indexOf(p), p.getCurrentPoints() - old_points);
                    listeners_handler.notify_cardPlaced(this, x, y, card_chosen.orientation);
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

    // for gold card
    public void placeCard(GoldCard card_chosen, Player p, int x, int y) throws GameEndedException {

        PersonalBoard personal_board = p.getPersonalBoard();
        int old_points = p.getCurrentPoints();
        boolean admissible_move;

        if (!personal_board.board[x][y].is_full) {
            listeners_handler.notify_illegalMove(this);
        }
        else {
            if (250 <= x && x <= 251 && 250 <= y && y <= 251) {
                StarterCard already_placed_card = p.getStarterCard();
                Coordinate coord = already_placed_card.getCoordinateAt(x, y);
                admissible_move = personal_board.placeCardAt(already_placed_card, card_chosen, coord);
                if (!admissible_move) {
                    listeners_handler.notify_illegalMove(this);
                }
                else {
                    // remove the card from the player's hand
                    p.getHand().remove(p.getChosenGameCard());
                    getCommonBoard().movePlayer(aux_order_players.indexOf(p), p.getCurrentPoints() - old_points);
                    listeners_handler.notify_cardPlaced(this, x, y, card_chosen.orientation);
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
                    listeners_handler.notify_illegalMove(this);
                }
                else {
                    // remove the card from the player's hand
                    p.getHand().remove(p.getChosenGameCard());
                    getCommonBoard().movePlayer(aux_order_players.indexOf(p), p.getCurrentPoints() - old_points);
                    listeners_handler.notify_cardPlaced(this, x, y, card_chosen.orientation);
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

        if (common_board.getResourceConcreteDeck().isEmpty() &&
                common_board.getGoldConcreteDeck().isEmpty() &&
                !getStatus().equals(GameStatus.SECOND_LAST_ROUND)) {
            setStatus(GameStatus.SECOND_LAST_ROUND);
            if (getStatus() == GameStatus.SECOND_LAST_ROUND && aux_order_players.getLast().equals(p)) {
                setStatus(GameStatus.LAST_ROUND);
            }
            return;
        }

        switch (index) {
            case 1:
                // Draw from Resource Deck
                if (!common_board.getResourceConcreteDeck().isEmpty())
                    p.getHand().add((ResourceCard) common_board.drawFromConcreteDeck(0));
                else {
                    illegal_draw = true;
                    listeners_handler.notify_illegalMoveBecauseOf(this, "Resource Deck is empty!");
                }
                break;
            case 2:
                // Draw first Resource Card from table
                if (!common_board.getResourceConcreteDeck().isEmpty())
                    p.getHand().add((ResourceCard) common_board.drawFromTable(0, 0, 0));
                else {
                    illegal_draw = true;
                    listeners_handler.notify_illegalMoveBecauseOf(this, "Resource Table is empty!");
                }
                break;
            case 3:
                // Draw second Resource Card from table
                if (!common_board.getResourceConcreteDeck().isEmpty())
                    p.getHand().add((ResourceCard) common_board.drawFromTable(0, 1, 0));
                else {
                    illegal_draw = true;
                    listeners_handler.notify_illegalMoveBecauseOf(this, "Resource Table is empty!");
                }
                break;
            case 4:
                // Draw from Gold Deck
                if (!common_board.getGoldConcreteDeck().isEmpty())
                    p.getHand().add((GoldCard) common_board.drawFromConcreteDeck(1));
                else {
                    illegal_draw = true;
                    listeners_handler.notify_illegalMoveBecauseOf(this, "Gold Deck is empty!");
                }
                break;
            case 5:
                // Draw first Gold Card from table
                if (!common_board.getGoldConcreteDeck().isEmpty())
                    p.getHand().add((GoldCard) common_board.drawFromTable(1, 0, 1));
                else {
                    illegal_draw = true;
                    listeners_handler.notify_illegalMoveBecauseOf(this, "Gold Table is empty!");
                }
                break;
            case 6:
                // Draw second Gold Card from table
                if (!common_board.getGoldConcreteDeck().isEmpty())
                    p.getHand().add((GoldCard) common_board.drawFromTable(1, 1, 1));
                else {
                    illegal_draw = true;
                    listeners_handler.notify_illegalMoveBecauseOf(this, "Gold Table is empty!");
                }
                break;
        }

        if (!illegal_draw) {
            listeners_handler.notify_cardDrawn(this, index);
            // at the end of every player round, we check for her/his points
            if (getStatus() == GameStatus.SECOND_LAST_ROUND && aux_order_players.getLast().equals(p)) {
                setStatus(GameStatus.LAST_ROUND);
            }
            else if (p.getCurrentPoints() >= DefaultValues.num_points_for_second_last_round) {
                if (aux_order_players.getLast().equals(p)) {
                    setStatus(GameStatus.LAST_ROUND);
                }
                else if (getStatus() == GameStatus.RUNNING)
                    setStatus(GameStatus.SECOND_LAST_ROUND);
            }
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

        listeners_handler.notify_nextTurn(this);
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

        if (aux_final_scores_tie.size() == 1) {
            winners.addAll(orderedPlayers);
        }
        else {
            // aux_final_scores_tie.size() >= 2
            // another filtering:
            aux_final_scores_tie.sort(Comparator.comparingInt(Player::scoreOnlyObjectiveCards).reversed());
            StringBuilder aux = new StringBuilder(aux_final_scores_tie.getFirst().getNickname());
            int counter = 0;
            for (Player p : aux_final_scores_tie) {
                if (p.scoreOnlyObjectiveCards() == aux_final_scores_tie.getFirst().scoreOnlyObjectiveCards()) {
                    aux.append(p.getNickname());
                    counter++;
                }
            }
            winners.clear();
            Player p_aux = new Player(aux.toString());
            p_aux.setFinalScore(aux_final_scores_tie.getFirst().getFinalScore());
            winners.add(p_aux);
            for (int i = counter; i < orderedPlayers.size(); i++) {
                winners.add(orderedPlayers.get(i));
            }
        }

        // Populate the leaderboard
        leaderboard.clear(); // Clear the leaderboard before adding new entries
        for (Player player : orderedPlayers) {
            leaderboard.put(player, player.getFinalScore());
        }
    }

    public Map<Player, Integer> getLeaderboard() {
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

    public void setErrorMessage(String error) {

    }

    // **************************** Listeners ********************************

    /**
     * @param obj adds the listener to the list
     */
    public void addListener(GameListener obj) {
        listeners_handler.addListener(obj);
    }

    /**
     * @param lis removes listener from list
     */
    public void removeListener(GameListener lis) {
        listeners_handler.removeListener(lis);
    }

    // aux

    public List<StarterCard> getStarterCardsToChoose(String nickname) {
        return getPlayerEntity(nickname).getStarterCardToChose();
    }

    public List<ObjectiveCard> getPersonalObjectiveCardsToChoose(String nickname) {
        return getPlayerEntity(nickname).getSecretObjectiveCards();
    }
}

