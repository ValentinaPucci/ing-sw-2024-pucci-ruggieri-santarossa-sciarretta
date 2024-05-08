package it.polimi.demo.networking;

import it.polimi.demo.DefaultValues;
import it.polimi.demo.controller.ControllerInterfaces.GameControllerInterface;
import it.polimi.demo.controller.ControllerInterfaces.MainControllerInterface;
import it.polimi.demo.controller.GameController;
import it.polimi.demo.controller.MainController;
import it.polimi.demo.model.GameModel;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.exceptions.GameNotStartedException;
import it.polimi.demo.listener.GameListener;
import it.polimi.demo.view.GameDetails;
import it.polimi.demo.view.GameView;
import it.polimi.demo.view.PlayerDetails;
import org.fusesource.jansi.Ansi;

import java.rmi.RemoteException;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.fusesource.jansi.Ansi.ansi;

public class ServerImpl implements Server, GameListener {
    /**
     * The executor service used to run all the ping-pong threads.
     */
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    /**
     * This scheduler is used to handle paused games.
     */
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * game_controller is created and put inside main_controller.
     * We could also avoid to having it as an attribute, but it is
     * far more convenient to have it here.
     */
    protected GameModel model;
    protected MainController main_controller;
    protected GameControllerInterface game_controller;

    // Here we exploit the interface
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
        // it is not necessary to add listener here, we do it in
        // either createGame() or joinGame() methods
        System.out.println("A client is registering to the server...");
        executor.submit(this.pingpongThread);
    }

    /**
     * This method is called when a player wants to join a game.
     * It will also handle the case where a player is reconnecting
     * to a game in which he was already playing.
     *
     */
    @Override
    public void addPlayerToGame(int gameID, String nickname)
            throws RemoteException, GameNotStartedException {

        System.out.println("A client is joining game " + gameID + " with username " + nickname + "...");
        MainController.getControllerInstance().joinGame(this, nickname, gameID).startIfFull();
    }

    /**
     * This method is called when a client player wants to create a new game.
     * The gameID is obtained from the maximum gameID of the game list.
     *
     */
    @Override
    public void create(String nickname, int numberOfPlayers) throws RemoteException, GameNotStartedException {

        // We get the main controller instance before doing any other action
        main_controller = MainController.getControllerInstance();

        int gameID = main_controller.getGames().keySet().stream()
                .max(Comparator.naturalOrder())
                .orElse(0) + 1;

        System.out.println("A client with username " + nickname + " is creating game " + gameID + "...");

        this.model = new GameModel(gameID, numberOfPlayers, new Player(nickname));

        game_controller = main_controller.createGame(this, nickname, numberOfPlayers, gameID);

        // The player that creates the game is the first player to join it
        playerIndex = 0;
        // Triggers the update of the player list on the client
        playerJoinedGame();
    }

    /**
     * When the client asks for the list of games, the server will trigger on the current ServerImpl a fake update of
     * the GameList class.
     */
    @Override
    public void getGamesList() throws RemoteException {

        main_controller = MainController.getControllerInstance();

        if (main_controller != null) {
            List<GameDetails> gamesDetails = main_controller.getGamesDetails();
            if (gamesDetails != null && this.client != null) {
                this.client.updateGamesList(gamesDetails);
            } else {
                if (gamesDetails == null) {
                    System.err.println("Games details returned by main controller is null.");
                }
                if (this.client == null) {
                    System.err.println("Client is null.");
                }
            }
        } else {
            System.err.println("Main controller is null.");
        }
    }

    @Override
    public void newGame() throws RemoteException {
        this.client.updateGamesList(MainController.getControllerInstance().getGamesDetails());
    }

    @Override
    public void updatedGame() throws RemoteException {
        this.client.updateGamesList(MainController.getControllerInstance().getGamesDetails());
    }

    @Override
    public void removedGame() throws RemoteException {
        this.client.updateGamesList(MainController.getControllerInstance().getGamesDetails());

        if(this.model != null && MainController.getControllerInstance().getGames().get(this.model.getGameId()) == null){
            //this.model.removeListener(this);
            this.model = null;

            this.client.showError("The game you were waiting for has been removed.");
        }
    }

    @Override
    public void playerJoinedGame() {
        try {
            if (this.client == null) {
                System.err.println("Client is null. Cannot update players list.");
                return;
            }

            if (this.model == null) {
                System.err.println("Model is null. Cannot retrieve nicknames.");
                return;
            }

            List<String> allNicknames = this.model.getAllNicknames();
            if (allNicknames == null) {
                System.err.println("Nicknames list is null. Cannot update players list.");
                return;
            }

            this.client.updatePlayersList(allNicknames);
        } catch (RemoteException e) {
            System.err.println("Error while updating players list: " + e.getMessage());
        }
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

        MainController.getControllerInstance().getGames().remove(model.getGameId());
        //this.model.removeListener(this);
        this.model = null;
        try {
            this.client.gameEnded(gameView);
        } catch (RemoteException ignored) {}
    }

    @Override
    public void pong() throws RemoteException {
        pingpongThread.pongReceived();
    }
}
