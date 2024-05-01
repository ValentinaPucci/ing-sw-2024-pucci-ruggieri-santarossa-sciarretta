package it.polimi.demo.networking;

import it.polimi.demo.DefaultValues;
import it.polimi.demo.listener.UIListener;
import it.polimi.demo.model.exceptions.GameNotStartedException;
import it.polimi.demo.model.exceptions.InvalidChoiceException;
import it.polimi.demo.view.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static it.polimi.demo.networking.PrintAsync.printAsync;
import static org.fusesource.jansi.Ansi.ansi;

public class ClientImpl extends UnicastRemoteObject implements Client, Runnable, UIListener{
    private final Server server;
    private final StartUI startUI;
    private final GameUI gameUI;
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private boolean pingReceived;

    public ClientImpl(Server server, UIType uiType) throws RemoteException {
        this.server = server;

        switch (uiType){
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

    public void initialize() throws RemoteException {
        this.server.register(this);
        startUI.addListener(this);
    }

    @Override
    public void run() {
        scheduler.scheduleWithFixedDelay(() -> {
            if(!pingReceived){
                System.err.println("\nConnection lost, exiting...");
                exit();
            }

            pingReceived = false;
        }, DefaultValues.pingpongTimeout, DefaultValues.connectionLostTimeout, TimeUnit.MILLISECONDS);

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

    @Override
    public void createGame( String nickname, int numberOfPlayers) {
        try {
            this.server.create(nickname, numberOfPlayers);
        } catch (GameNotStartedException e) {
            try {
                this.showError(e.getMessage());
            } catch (RemoteException ignored) {}
        } catch (RemoteException e) {
            System.err.println("Network error while creating game.");
        }
    }

    @Override
    public void joinGame(int gameID, String username) {
        try {
            this.server.addPlayerToGame(gameID, username);
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
        //closeConnection(); --> x socket
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

    @Override
    public void performTurn(int column) {
        try {
            this.server.performTurn(column);
        } catch (RemoteException e) {
            System.err.println("Network error while performing turn.");
        }
    }

    @Override
    public void ping() throws RemoteException {
        pingReceived = true;

        this.server.pong();
    }
}
