package it.polimi.demo.networking;

import it.polimi.demo.DefaultValues;
import it.polimi.demo.controller.GameController;
import it.polimi.demo.controller.MainController;
import it.polimi.demo.model.GameModel;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.exceptions.GameNotStartedException;
import it.polimi.demo.model.exceptions.InvalidChoiceException;
import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;
import it.polimi.demo.view.GameView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerImpl implements Server, GameListener {
    /**
     * The executor service used to run all the ping-pong threads.
     */
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    /**
     * This scheduler is used to handle paused games.
     */
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    protected GameModel model;
    protected MainController main_controller;
    protected GameController game_controller;

    protected Client client;
    /**
     * Index of the player served by this ServerImpl.
     */
    protected int playerIndex;
    /**
     * The ping-pong thread reference used to check if the client is still connected.
     */
    private final PingPongThread pingpongThread = new PingPongThread(this);

    /**
     * This method saves the instance of the client that is registering to the server and starts the ping-pong thread.
     * It also adds this ServerImpl to the GameListListener list, so the client will be notified when the list of games changes.
     * @param client the client to register
     */
    @Override
    public void register(Client client) throws RemoteException {
        this.client = client;
        //TODO: guardare bene: ----> controller.getGames().addListener(this);
        System.out.println("A client is registering to the server...");

        executor.submit(this.pingpongThread);
    }

    /**
     * This method is called when a player wants to join a game.
     * It will also handle the case where a player is reconnecting to a game in which he was already playing.
     *
     * @see Server#addPlayerToGame(int, String)
     */
    @Override
    public void addPlayerToGame(int gameID, String nickname) throws RemoteException, GameNotStartedException, InvalidChoiceException {
        System.out.println("A client is joining game " + gameID + " with username " + nickname + "...");

        this.game_controller = main_controller.getGames().get(gameID);
        this.model = main_controller.getGames().get(gameID).getModel();
        Player p = new Player(nickname);
        if(this.model == null){
            throw new InvalidChoiceException("Game not found.");
        }

        if(model.getPlayersConnected().contains(nickname)){
            //TODO: guardare bene: ----> GameList.getInstance().removeListener(this);

            this.client.gameHasStarted();

            this.playerIndex = this.game_controller.reconnectPlayer(nickname);

            // Force the update of the client
            this.client.modelChanged(new GameView(this.model, this.playerIndex));
            this.model.addListener(this);
        } else {
            this.model.addListener(this);

            try {
                this.game_controller.addPlayer(nickname);
            } catch (GameNotStartedException e) {
                this.model.removeListener(this);
                this.model = null;
                this.game_controller = null;
                throw e;
            }

            this.playerJoinedGame();

            this.game_controller.startIfFull();
        }
    }

    /**
     * This method is called when a client player wants to create a new game.
     * The gameID is obtained from the maximum gameID of the game list.
     *
     */
    @Override
    public void create(String nickname, int numberOfPlayers) throws RemoteException, GameNotStartedException {
        int gameID = main_controller.getGames().keySet().stream()
                .max(Comparator.naturalOrder())
                .orElse(0) + 1;

        System.out.println("A client is creating game " + gameID + " with username " + nickname + "...");

        this.model = new GameModel(gameID, numberOfPlayers, new Player(nickname));

        // The player that creates the game is the first player to join it
        this.playerIndex = 0;

        this.game_controller = new GameController(this.model);

        main_controller.getGames().put(gameID, game_controller);

        this.model.addListener(this);

        // Triggers the update of the player list on the client
        this.playerJoinedGame();
    }

    /**
     * When the client asks for the list of games, the server will trigger on the current ServerImpl a fake update of
     * the GameList class.
     */
    @Override
    public void getGamesList() throws RemoteException {
        this.client.updateGamesList(main_controller.getGamesDetails());
    }


    @Override
    public void newGame() throws RemoteException {
        this.client.updateGamesList(main_controller.getGamesDetails());
    }

    @Override
    public void updatedGame() throws RemoteException {
        this.client.updateGamesList(main_controller.getGamesDetails());
    }

    @Override
    public void removedGame() throws RemoteException {
        this.client.updateGamesList(main_controller.getGamesDetails());

        if(this.model != null && main_controller.getGames().get(this.model.getGameId()) == null){
            this.model.removeListener(this);
            this.model = null;

            this.client.showError("The game you were waiting for has been removed.");
        }
    }

    @Override
    public void playerJoinedGame() throws RemoteException {
        this.client.updatePlayersList(this.model.getAllNicknames());
    }


    @Override
    public void gameIsFull() throws RemoteException {
        //TODO: gestione listeners ---> GameList.getInstance().removeListener(this);

        this.client.gameHasStarted();
    }


    @Override
    public void modelChanged() throws RemoteException {
        this.client.modelChanged(new GameView(this.model, this.playerIndex));

        // If the game is paused, it will be automatically ended after a certain amount of time.
        if(this.model.isPaused()){
            scheduler.schedule(() -> {
                if(this.model.isPaused()){
                    System.out.println("Game " + this.model.getGameId() + " has been paused for too long. It will be ended.");

                    //TODO: adjust walkover ---> executor.submit(() -> this.controller.walkover());
                }
            }, DefaultValues.walkoverTimeout, TimeUnit.MILLISECONDS);
        }
    }


    @Override
    public void gameEnded() {
        GameView gameView = new GameView(this.model, this.playerIndex);

        main_controller.getGames().remove(model.getGameId());
        this.model.removeListener(this);
        this.model = null;
        try {
            this.client.gameEnded(gameView);
        } catch (RemoteException ignored) {}
    }


    @Override
    public void performTurn(int column) throws RemoteException {
        if(this.playerIndex != this.model.getCurrentPlayerIndex()){
            this.game_controller.setError("Player " + this.model.getAux_order_players().get(this.playerIndex).getNickname() + " tried to perform a turn while it was not his turn.");
            return;
        }

        this.game_controller.performTurn(column);
    }

    @Override
    public void pong() throws RemoteException {
        pingpongThread.pongReceived();
    }


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
