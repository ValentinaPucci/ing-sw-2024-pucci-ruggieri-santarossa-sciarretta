package it.polimi.demo.networking;

import it.polimi.demo.DefaultValues;
import it.polimi.demo.listener.UIListener;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.model.exceptions.GameNotStartedException;
import it.polimi.demo.model.exceptions.InvalidChoiceException;
import it.polimi.demo.networking.Socket.ServerProxy;
import it.polimi.demo.view.*;
import it.polimi.demo.view.UI.GUI.GraphicalGameUI;
import it.polimi.demo.view.UI.GUI.GraphicalStartUI;
import it.polimi.demo.view.UI.TUI.TextualGameUI;
import it.polimi.demo.view.UI.TUI.TextualStartUI;
import it.polimi.demo.view.UI.GameUI;
import it.polimi.demo.view.UI.StartUI;
import it.polimi.demo.view.UI.TUI.TextualUtils;
import it.polimi.demo.view.UI.UIType;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static it.polimi.demo.networking.PrintAsync.printAsync;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * It is instantiated either by startRMI or by startSocket methods inside the AppClient class.
 * ClientImpl implements UIListener. As an example, the real mechanism through which the user is able
 * to create a game is by calling the createGame method in TUI class, which reach createGame in ClientImpl
 * thanks to UIListener interface!! The same holds for joinGame, etc.
 */
public class ClientImpl extends UnicastRemoteObject implements Client, Runnable, UIListener {

    // Here we exploit the interface
    private final Server server;

    // UI interfaces
    private final StartUI startUI;
    private final GameUI gameUI;

    // Utilities
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private boolean pingReceived;

    public ClientImpl(Server server, UIType uiType) throws RemoteException {

        this.server = server;

        switch (uiType) {
            case TUI -> {
                this.startUI = new TextualStartUI();
                this.gameUI = new TextualGameUI();
            }
            case GUI -> {
                this.startUI = new GraphicalStartUI();
                this.gameUI = new GraphicalGameUI();
            }
            default -> throw new RuntimeException("UI type not supported");
        }

        initialize();
    }

    /**
     * Generic initializer for client server connection, i.e.
     * independent form the connection type (RMI or SOCKET).
     * @throws RemoteException if the connection fails
     */
    public void initialize() throws RemoteException {
        this.server.register(this);
        startUI.addListener(this);
    }

    /**
     * In fact, ClientImpl delegates the run to the StartUI. This fact is crucial,
     * because the StartUI is the one that actually starts the game.
     */
    @Override
    public void run() {
        scheduler.scheduleWithFixedDelay(() -> {
            if (!pingReceived) {
                System.err.println("\nConnection lost, exiting...");
                exit();
            }
            pingReceived = false;
        },
                DefaultValues.pingpongTimeout,
                DefaultValues.connectionLostTimeout,
                TimeUnit.MILLISECONDS);

        System.out.println("Starting StartUI...");
        startUI.run();
    }

    @Override
    public void refreshStartUI() {
        try {
            server.getGamesList();
        } catch (RemoteException e) {
            System.err.println("Network error while requesting games list.");
        }
    }

    /**
     * The client delegates the creation of the game to the server.
     * @param nickname of the player
     * @param numberOfPlayers number of players in the game
     */
    @Override
    public void createGame(String nickname, int numberOfPlayers) {
        if (server == null) {
            System.err.println("Server is null. Cannot create game.");
            return;
        }

        if (nickname == null || nickname.isEmpty()) {
            System.err.println("Nickname cannot be null or empty.");
            return;
        }

        if (numberOfPlayers <= 0) {
            System.err.println("Number of players must be greater than zero.");
            return;
        }

        try {
            // Here we create the game
            server.create(nickname, numberOfPlayers);
        } catch (GameNotStartedException e) {
            try {
                showError(e.getMessage());
            } catch (RemoteException ignored) {
                System.err.println("Error while showing error message.");
            }
        } catch (RemoteException e) {
            System.err.println("Network error while creating game.");
        }
    }

    @Override
    public void performTurn() {
        Scanner s = new Scanner(System.in);
        GameStatus actual_status = this.server.getGameStatus();
        int where_to_draw;
        int x, y;
        ResourceCard chosen_card;
        switch (actual_status){
            case GameStatus.FIRST_ROUND:
                this.server.placeStarterCard();
                printAsync("Select where you want to draw your card from: ");
                where_to_draw = TextualUtils.nextInt(s);
                this.server.drawCard(where_to_draw);

            case GameStatus.RUNNING:
                chosen_card = chooseCardFromHand();
                printAsync("Select where you want to put your selected card: ");
                x = TextualUtils.nextInt(s);
                y = TextualUtils.nextInt(s);
                this.server.placeCard(chosen_card, x, y );
                printAsync("Select from where you want to draw: ");
                where_to_draw = TextualUtils.nextInt(s);
                this.server.drawCard(where_to_draw);

            case GameStatus.SECOND_LAST_ROUND:
                chosen_card = chooseCardFromHand();
                printAsync("Select where you want to put your selected card: ");
                x = TextualUtils.nextInt(s);
                y = TextualUtils.nextInt(s);
                this.server.placeCard(chosen_card, x, y);
                printAsync("Select from where you want to draw: ");
                where_to_draw = TextualUtils.nextInt(s);
                this.server.drawCard(where_to_draw);

            case GameStatus.LAST_ROUND:
                chosen_card = chooseCardFromHand();
                printAsync("Select where you want to put your selected card: ");
                x = TextualUtils.nextInt(s);
                y = TextualUtils.nextInt(s);
                this.server.placeCard(chosen_card, x, y);

            case GameStatus.ENDED:
                this.server.calculateFinalScores();
        }
    }

    public ResourceCard chooseCardFromHand(){
        Scanner s = new Scanner(System.in);
        int choice = -1;

        while (choice < 1 || choice >3){
            System.out.print("Select a card from your hand: ");
            choice = TextualUtils.nextInt(s);
            showPlayerHand(server.getPlayerHand());
            if(choice < 1 || choice >3){
                System.out.print("Invalid input. Type 1, 2 or 3.");
            }
        }
        return server.getPlayerHand().get(choice);
    }

    private void showPlayerHand(List<ResourceCard> playerHand) {
        System.out.println("Player's Hand:");
        for (ResourceCard card : playerHand) {
            System.out.println(card); // Assuming ResourceCard has a meaningful toString() method
        }
    }

    /**
     * The client delegates the joining of the game to the server.
     * @param gameID the ID of the game to join
     * @param username the username of the player
     */
    @Override
    public void joinGame(int gameID, String username) {
        try {
            if (this.server != null) { // Check if server object is not null
                this.server.addPlayerToGame(gameID, username);
            } else {
                // Handle the case where server object is null
                System.err.println("Server object is null. Cannot join game.");
            }
        } catch (GameNotStartedException e) {
            try {
                this.showError(e.getMessage());
            } catch (RemoteException ignored) {}
        } catch (RemoteException e) {
            System.err.println("Network error while joining game.");
        } catch (InvalidChoiceException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void exit() {
        closeConnection();
        System.exit(0);
    }

    @Override
    public void updateGamesList(List<GameDetails> o) throws RemoteException {
        startUI.showGamesList(o);
    }

    @Override
    public void showError(String err) throws RemoteException {
        startUI.showError(err);
    }

    @Override
    public void updatePlayersList(List<String> o) throws RemoteException {
        if (o == null) {
            System.err.println("List of players is null. Cannot update players list.");
            return;
        }
        if (o.isEmpty()) {
            System.err.println("List of players is empty. Cannot update players list.");
            return;
        }
        startUI.showPlayersList(o);
    }

    @Override
    public void gameHasStarted() throws RemoteException {
        System.out.println("Closing StartUI...");
        startUI.close();

        System.out.println("Starting GameUI...");
        new Thread(gameUI).start();
        gameUI.addListener(this);
    }

    @Override
    public void modelChanged(GameView gameView) throws RemoteException {
        gameUI.update(gameView);
    }

    @Override
    public void gameEnded(GameView gameView) throws RemoteException {
        gameUI.gameEnded(gameView);
    }

    private void closeConnection() {
        if(this.server instanceof ServerProxy) {
            try {
                ((ServerProxy) this.server).close();
            } catch (RemoteException e) {
                throw new RuntimeException("Error while closing connection.", e);
            }
        }
    }
    @Override
    public void ping() throws RemoteException {
        pingReceived = true;
        this.server.pong();
    }
}
