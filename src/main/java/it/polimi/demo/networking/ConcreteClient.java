package it.polimi.demo.networking;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.DefaultValues;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;
import it.polimi.demo.networking.ControllerInterfaces.GameControllerInterface;
import it.polimi.demo.networking.ControllerInterfaces.MainControllerInterface;
import it.polimi.demo.view.TUI;
import it.polimi.demo.view.UI;
import it.polimi.demo.view.UIType;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static it.polimi.demo.networking.PrintAsync.printAsync;

public class ConcreteClient implements VirtualClient, GameListener, Runnable {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static MainControllerInterface client_requests;
    private GameControllerInterface gameController;
    private static UI ui;
    private String nickname;
    // todo: heartbeats handling!!!!
    private boolean heartbeat_received = false;

    public ConcreteClient(UIType uiType) {

        switch (uiType){
            case TUI -> {
                printAsync("TUI + " + uiType + " CHOSEN");
                ui = new TUI();
            }
            case GUI -> {
                printAsync("GUI + " + uiType + " CHOSEN");
                // todo: ui = new GUI();
            }
            default -> throw new RuntimeException("UI type not supported");
        }


        //initialize();
    }

//    public void initialize() throws RemoteException {
//        this.server.register(this);
//        startUI.addListener(this);
//    }

    // **************************************** run method ****************************************

    /**
     * Run the client
     */
    // todo: write an original run
    @Override
    public void run() {

        scheduler.scheduleWithFixedDelay(() -> {
            if (!heartbeat_received){
                System.err.println("\nConnection lost, exiting...");
                try {
                    leave(nickname, gameController.getGameId());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            heartbeat_received = false;
        },
                DefaultValues.secondsToWaitReconnection,
                DefaultValues.timeoutConnection_millis,
                TimeUnit.MILLISECONDS);

        System.out.println("Starting StartUI...");
        // here we handle the UI
        // todo: implement a proper run method in both TUI and GUI
        ui.run();
    }

    // auxiliary methods

    public void setClientRequests(MainControllerInterface client_requests) {
        ConcreteClient.client_requests = client_requests;
    }

    //--------------------Overrides of VirtualClient interface-------------------------------------------------

    // subsection: MainControllerInterface

    /**
     * Requests the creation of a game on the server.
     *
     * @param nick        the nickname of the player creating the game
     * @param num_players the number of players in the game
     * @throws RemoteException if a remote exception occurs
     */
    @Override
    public void createGame(String nick, int num_players) throws RemoteException {
        gameController = client_requests.createGame(this, nick, num_players);
        nickname = nick;
    }

    /**
     * Requests to join the first available game on the server.
     *
     * @param nick the nickname of the player joining the game
     * @throws RemoteException if a remote exception occurs
     */
    @Override
    public void joinFirstAvailable(String nick) throws RemoteException {
        gameController = client_requests.joinFirstAvailableGame(this, nick);
        nickname = nick;
    }

    /**
     * Requests to join a specific game on the server.
     *
     * @param nick   the nickname of the player joining the game
     * @param idGame the ID of the game to join
     * @throws RemoteException if a remote exception occurs
     */
    @Override
    public void joinGame(String nick, int idGame) throws RemoteException {
        gameController = client_requests.joinGame(this, nick, idGame);
        nickname = nick;
    }

    /**
     * Requests to reconnect to a specific game on the server.
     *
     * @param nick   the nickname of the player reconnecting
     * @param idGame the ID of the game to reconnect to
     * @throws RemoteException if a remote exception occurs
     */
    @Override
    public void reconnect(String nick, int idGame) throws RemoteException {
        gameController = client_requests.reconnect(this, nick, idGame);
        nickname = nick;
    }

    /**
     * Requests to leave a game on the server.
     *
     * @param nick   the nickname of the player leaving the game
     * @param idGame the ID of the game to leave
     * @throws IOException if an I/O exception occurs
     */
    @Override
    public void leave(String nick, int idGame) throws IOException {
        client_requests.leaveGame(this, nick, idGame);
        gameController = null;
        nickname = null;
    }

    // subsection: GameControllerInterface

    /**
     * Send a message to the server
     *
     * @param msg message to send
     * @throws RemoteException if the connection fails
     */
    @Override
    public void sendMessage(Message msg) throws RemoteException {
        gameController.sendMessage(msg);
    }

    /**
     * Notify the server that a client is ready to start
     *
     * @throws RemoteException if the connection fails
     */
    @Override
    public void setAsReady() throws RemoteException {
        if (gameController != null) {
            gameController.setPlayerAsReadyToStart(nickname);
        }
    }

    /**
     * Check if this is my turn
     * @return true if it's my turn, false else
     * @throws RemoteException if the connection fails
     */
    @Override
    public boolean isMyTurn() throws RemoteException {
        return gameController.isMyTurn(nickname);
    }

    /**
     * Draw a card from the deck/table
     * @param player_nickname The nickname of the player who wants to draw a card
     *
     * @param index The index indicating which card to draw:
     *                    1: Resource Deck
     *                    2: First Resource Card on the table
     *                    3: Second Resource Card on the table
     *                    4: Gold Deck
     *                    5: First Gold Card on the table
     *                    6: Second Gold Card on the table
     *
     * @throws IOException if the connection fails
     */
    @Override
    public void drawCard(String player_nickname, int index) throws IOException {
        gameController.drawCard(player_nickname, index);
    }

    /**
     * Place a resource card on the personal board of the player
     * @param card_chosen the card to place
     * @param x the x coordinate on the personal board
     * @param y the y coordinate on the personal board
     * @throws IOException if the connection fails
     */
    @Override
    public void placeCard(ResourceCard card_chosen, int x, int y) throws IOException{
        gameController.placeCard(card_chosen, gameController.getPlayerEntity(nickname), x, y);
    }

    /**
     * Place a gold card on the personal board of the player
     * @param card_chosen the card to place
     * @param x the x coordinate on the personal board
     * @param y the y coordinate on the personal board
     * @throws IOException if the connection fails
     */
    @Override
    public void placeCard(GoldCard card_chosen, int x, int y) throws IOException{
        gameController.placeCard(card_chosen, gameController.getPlayerEntity(nickname), x, y);
    }

    /**
     * Send a PING to the server
     *
     * @throws RemoteException if the connection fails
     */
    @Override
    public void addPing() throws RemoteException {
        if (gameController != null) {
            gameController.addPing(nickname, this);
        }
    }

    // **************** I do not know why I did this, but maybe it will work ****************

    @Override
    public void newPlayerHasJoined(GameModelImmutable gameModel) throws RemoteException {
        
    }

    @Override
    public void playerAbandoningGame(GameModelImmutable game_model, String nickname) throws RemoteException {

    }

    @Override
    public void failedJoinFullGame(Player player, GameModelImmutable game_model) throws RemoteException {

    }

    @Override
    public void playerReconnected(GameModelImmutable game_model, String nickname_reconnected) throws RemoteException {

    }

    @Override
    public void failedJoinInvalidNickname(Player player_trying_to_join) throws RemoteException {

    }

    @Override
    public void invalidGameId(int game_id) throws RemoteException {

    }

    @Override
    public void genericErrorWhenEnteringGame(String why) throws RemoteException {

    }

    @Override
    public void playerReadyForStarting(GameModelImmutable game_model, String nickname) throws IOException {

    }

    @Override
    public void commonObjectiveCardsExtracted(GameModelImmutable gameModel) throws RemoteException {

    }

    @Override
    public void resourceCardExtractedFromDeck(GameModelImmutable gameModel) throws RemoteException {

    }

    @Override
    public void resourceCardExtractedFromTable(GameModelImmutable gameModel) throws RemoteException {

    }

    @Override
    public void goldCardExtractedFromDeck(GameModelImmutable gameModel) throws RemoteException {

    }

    @Override
    public void goldCardExtractedFromTable(GameModelImmutable gameModel) throws RemoteException {

    }

    @Override
    public void objectiveCardExtractedFromEmptyDeck(GameModelImmutable gameModel) throws RemoteException {

    }

    @Override
    public void resourceCardExtractedFromEmptyDeck(GameModelImmutable gameModel) throws RemoteException {

    }

    @Override
    public void resourceCardExtractedFromEmptyTable(GameModelImmutable gameModel) throws RemoteException {

    }

    @Override
    public void goldCardExtractedFromEmptyDeck(GameModelImmutable gameModel) throws RemoteException {

    }

    @Override
    public void goldCardExtractedFromEmptyTable(GameModelImmutable gameModel) throws RemoteException {

    }

    @Override
    public void cardPlacedOnPersonalBoard(GameModelImmutable gameModel) throws RemoteException {

    }

    @Override
    public void gameStarted(GameModelImmutable game_model) throws RemoteException {

    }

    @Override
    public void gameEnded(GameModelImmutable game_model) throws RemoteException {

    }

    @Override
    public void sentMessage(GameModelImmutable game_model, Message msg) throws RemoteException {

    }

    @Override
    public void playerDisconnected(GameModelImmutable gameModel, String nickname) throws RemoteException {

    }

    @Override
    public void nextTurn(GameModelImmutable game_model) throws RemoteException {

    }

    @Override
    public void playerHasMovedOnCommonBoard(GameModelImmutable game_model) throws RemoteException {

    }

    @Override
    public void onlyOnePlayerConnected(GameModelImmutable gameModel, int secondsToWaitUntilGameEnded) throws RemoteException {

    }

    @Override
    public void lastRound(GameModelImmutable game_model) throws RemoteException {

    }

    @Override
    public void secondLastRound(GameModelImmutable game_model) throws RemoteException {

    }

    @Override
    public void gameIdNotExists(int gameId) throws RemoteException {

    }
}
