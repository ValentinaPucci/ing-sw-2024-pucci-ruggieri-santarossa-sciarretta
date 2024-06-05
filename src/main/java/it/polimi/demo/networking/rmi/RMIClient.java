package it.polimi.demo.networking.rmi;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.DefaultValues;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.*;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.networking.GameListenerHandlerClient;
import it.polimi.demo.networking.HeartbeatSender;
import it.polimi.demo.view.flow.CommonClientActions;
import it.polimi.demo.view.flow.Flow;
import it.polimi.demo.networking.remoteInterfaces.GameControllerInterface;
import it.polimi.demo.networking.remoteInterfaces.MainControllerInterface;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


import static it.polimi.demo.networking.PrintAsync.printAsync;
import static it.polimi.demo.networking.PrintAsync.printAsyncNoLine;

// todo: implement differently
public class RMIClient implements CommonClientActions {

    private static MainControllerInterface requests;

    private GameControllerInterface gameController = null;

    private static GameListener modelInvokedEvents;

    private String nickname;

    private int game_id;

    private boolean initialized = false;

    private final GameListenerHandlerClient gameListenersHandler;

    private Registry registry;

    private Flow flow;

    private HeartbeatSender rmiHeartbeat;

    public RMIClient(Flow flow) {
        gameListenersHandler = new GameListenerHandlerClient(flow);
        this.flow = flow;
        rmiHeartbeat = new HeartbeatSender(flow, this);
        rmiHeartbeat.start();
        connect();
    }

    public void connect() {
        int attempt = 1;
        boolean retry;

        do {
            try {
                registry = LocateRegistry.getRegistry(DefaultValues.serverIp, DefaultValues.Default_port_RMI);
                requests = (MainControllerInterface) registry.lookup(DefaultValues.Default_servername_RMI);
                modelInvokedEvents = (GameListener) UnicastRemoteObject.exportObject(gameListenersHandler, 0);
                printAsync("Client RMI ready");
                retry = false;
            } catch (Exception e) {
                handleConnectionError(e, attempt);
                retry = ++attempt <= DefaultValues.num_of_attempt_to_connect_toServer_before_giveup;
            }
        } while (retry);
    }

    private void handleConnectionError(Exception e, int attempt) {
        if (attempt == 1) {
            printAsync("[ERROR] CONNECTING TO RMI SERVER: \n\tClient RMI exception: " + e + "\n");
        }
        printRetryMessage(attempt);
        waitAndPrintDots(DefaultValues.seconds_between_reconnection);
        if (attempt >= DefaultValues.num_of_attempt_to_connect_toServer_before_giveup) {
            printAsyncNoLine("Give up!");
            try {
                System.in.read();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.exit(-1);
        }
    }

    private void printRetryMessage(int attempt) {
        printAsyncNoLine("[#" + attempt + "]Waiting to reconnect to RMI Server on port: '" + DefaultValues.Default_port_RMI + "' with name: '" + DefaultValues.Default_servername_RMI + "'");
    }

    private void waitAndPrintDots(int seconds) {
        for (int i = 0; i < seconds; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            printAsyncNoLine(".");
        }
        printAsyncNoLine("\n");
    }

    /**
     * Request the creation of a Game to the server
     *
     * @param nick of the player who wants to create a game
     * @throws RemoteException if the reference could not be accessed
     * @throws NotBoundException if the reference could not be accessed
     */
    @Override
    public void createGame(String nick, int num_of_players) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultValues.serverIp, DefaultValues.Default_port_RMI);
        requests = (MainControllerInterface) registry.lookup(DefaultValues.Default_servername_RMI);
        gameController = requests.createGame(modelInvokedEvents, nick, num_of_players);
        nickname = nick;
        game_id = gameController.getGameId();
    }

    /**
     * Request to join a specific server game
     *
     * @param nick of the player who wants to join a specific game
     * @throws RemoteException
     * @throws NotBoundException
     */
    @Override
    public void joinGame(String nick, int idGame) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultValues.serverIp, DefaultValues.Default_port_RMI);
        requests = (MainControllerInterface) registry.lookup(DefaultValues.Default_servername_RMI);
        gameController = requests.joinGame(modelInvokedEvents, nick, idGame);
        if (gameController != null) {
            nickname = nick;
            game_id = gameController.getGameId();
            if (gameController.getNumConnectedPlayers() == gameController.getNumPlayersToPlay()) {
                initialized = true;
            }
        }
    }

    @Override
    public void joinFirstAvailableGame(String nick) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultValues.serverIp, DefaultValues.Default_port_RMI);
        requests = (MainControllerInterface) registry.lookup(DefaultValues.Default_servername_RMI);
        gameController = requests.joinFirstAvailableGame(modelInvokedEvents, nick);
        if (gameController != null) {
            nickname = nick;
            game_id = gameController.getGameId();
            if (gameController.getNumConnectedPlayers() == gameController.getNumPlayersToPlay()) {
                initialized = true;
            }
        }
    }

    /**
     * Notify the server that a socket is ready to start
     *
     * @throws RemoteException
     */
    @Override
    public void setAsReady() throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultValues.serverIp, DefaultValues.Default_port_RMI);
        requests = (MainControllerInterface) registry.lookup(DefaultValues.Default_servername_RMI);
        requests.setAsReady(modelInvokedEvents, nickname, game_id);
    }

    @Override
    public void placeStarterCard(Orientation orientation) throws RemoteException, GameEndedException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultValues.serverIp, DefaultValues.Default_port_RMI);
        requests = (MainControllerInterface) registry.lookup(DefaultValues.Default_servername_RMI);
        requests.placeStarterCard(modelInvokedEvents, nickname, orientation, game_id);
    }

    @Override
    public void chooseCard(int which_card) throws RemoteException, NotBoundException, GameEndedException {
        registry = LocateRegistry.getRegistry(DefaultValues.serverIp, DefaultValues.Default_port_RMI);
        requests = (MainControllerInterface) registry.lookup(DefaultValues.Default_servername_RMI);
        requests.chooseCard(modelInvokedEvents, nickname, which_card, game_id);
    }

    @Override
    public void placeCard(int x, int y, Orientation orientation) throws RemoteException, NotBoundException, GameEndedException {
        registry = LocateRegistry.getRegistry(DefaultValues.serverIp, DefaultValues.Default_port_RMI);
        requests = (MainControllerInterface) registry.lookup(DefaultValues.Default_servername_RMI);
        requests.placeCard(modelInvokedEvents, nickname, x, y, orientation, game_id);
    }

    @Override
    public void drawCard(int index) throws RemoteException, GameEndedException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultValues.serverIp, DefaultValues.Default_port_RMI);
        requests = (MainControllerInterface) registry.lookup(DefaultValues.Default_servername_RMI);
        requests.drawCard(modelInvokedEvents, nickname, index, game_id);
    }

    /**
     * Send a message to the server
     *
     * @param msg message to send
     * @throws RemoteException
     */
    @Override
    public void sendMessage(String nick, Message msg) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultValues.serverIp, DefaultValues.Default_port_RMI);
        requests = (MainControllerInterface) registry.lookup(DefaultValues.Default_servername_RMI);
        requests.sendMessage(modelInvokedEvents, nick, msg, game_id);
    }

    /**
     * Send a heartbeat to the server
     *
     * @throws RemoteException
     */
    @Override
    public void heartbeat() throws RemoteException, NotBoundException {
        if (initialized) {
            registry = LocateRegistry.getRegistry(DefaultValues.serverIp, DefaultValues.Default_port_RMI);
            requests = (MainControllerInterface) registry.lookup(DefaultValues.Default_servername_RMI);
            requests.addPing(modelInvokedEvents, nickname, game_id);
        }
    }

    /**
     * Request to leave a game without the possibility to be reconnected
     * Calling leave means that the player wants to quit forever the game
     *
     * @param nick of the player that wants to leave
     * @param idGame of the game to leave
     * @throws IOException
     * @throws NotBoundException
     */
    @Override
    public void leave(String nick, int idGame) throws IOException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultValues.serverIp, DefaultValues.Default_port_RMI);
        requests = (MainControllerInterface) registry.lookup(DefaultValues.Default_servername_RMI);
        requests.leaveGame(modelInvokedEvents, nick, idGame);
        gameController = null;
        nickname = null;
    }
}
