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
import it.polimi.demo.networking.rmi.remoteInterfaces.GameControllerInterface;
import it.polimi.demo.networking.rmi.remoteInterfaces.MainControllerInterface;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


import static it.polimi.demo.networking.PrintAsync.printAsync;
import static it.polimi.demo.networking.PrintAsync.printAsyncNoLine;

/**
 * RMIClient Class <br>
 * Handle all the network communications between RMIClient and RMIServer <br>
 * From the first connection, to the creation, joining, leaving, grabbing and positioning messages through the network<br>
 * by the RMI Network Protocol
 */
public class RMIClient implements CommonClientActions {
    /**
     * The remote object returned by the registry that represents the main controller
     */
    private static MainControllerInterface requests;
    /**
     * The remote object returned by the RMI server that represents the connected game
     */
    private GameControllerInterface gameController = null;
    /**
     * The remote object on which the server will invoke remote methods
     */
    private static GameListener modelInvokedEvents;
    /**
     * The nickname associated to the socket (!=null only when connected in a game)
     */
    private String nickname;
    private int game_id;
    /**
     * The remote object on which the server will invoke remote methods
     */
    private final GameListenerHandlerClient gameListenersHandler;
    /**
     * Registry of the RMI
     */
    private Registry registry;
    /**
     * Flow to notify network error messages
     */
    private Flow flow;

    private HeartbeatSender rmiHeartbeat;


    /**
     * Create, start and connect a RMI Client to the server
     *
     * @param flow for visualising network error messages
     */
    public RMIClient(Flow flow) {
        super();
        gameListenersHandler = new GameListenerHandlerClient(flow);
        connect();

        this.flow = flow;

        rmiHeartbeat = new HeartbeatSender(flow,this);
        rmiHeartbeat.start();
    }

    public void connect() {
        boolean retry = false;
        int attempt = 1;
        int i;

        do {
            try {
                registry = LocateRegistry.getRegistry(DefaultValues.serverIp, DefaultValues.Default_port_RMI);
                requests = (MainControllerInterface) registry.lookup(DefaultValues.Default_servername_RMI);

                modelInvokedEvents = (GameListener) UnicastRemoteObject.exportObject(gameListenersHandler, 0);

                printAsync("Client RMI ready");
                retry = false;

            } catch (Exception e) {
                if (!retry) {
                    printAsync("[ERROR] CONNECTING TO RMI SERVER: \n\tClient RMI exception: " + e + "\n");
                }
                printAsyncNoLine("[#" + attempt + "]Waiting to reconnect to RMI Server on port: '" + DefaultValues.Default_port_RMI + "' with name: '" + DefaultValues.Default_servername_RMI + "'");

                i = 0;
                while (i < DefaultValues.seconds_between_reconnection) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    printAsyncNoLine(".");
                    i++;
                }
                printAsyncNoLine("\n");

                if (attempt >= DefaultValues.num_of_attempt_to_connect_toServer_before_giveup) {
                    printAsyncNoLine("Give up!");
                    try {
                        System.in.read();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.exit(-1);
                }
                retry = true;
                attempt++;
            }
        } while (retry);

    }

    /**
     * Send heartbeats to the RMI server
     * If sending a message takes more than {@link DefaultValue#timeoutConnection_millis} millis, the socket
     * will be considered no longer connected to the server
     */
    /*
    @SuppressWarnings("BusyWait")
    @Override
    public void run() {
        //For the heartbeat
        while (!Thread.interrupted()) {
            try {
                Timer timer = new Timer();
                TimerTask task = new TaskOnNetworkDisconnection(flow);
                timer.schedule( task, DefaultValue.timeoutConnection_millis);

                //send heartbeat so the server knows I am still online
                heartbeat();

                timer.cancel();
            } catch (RemoteException e) {
                return;
            }
            try {
                Thread.sleep(DefaultValue.secondToWaitToSend_heartbeat);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }*/

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
        nickname = nick;
        game_id = gameController.getGameId();
    }

    @Override
    public void joinFirstAvailableGame(String nick) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultValues.serverIp, DefaultValues.Default_port_RMI);
        requests = (MainControllerInterface) registry.lookup(DefaultValues.Default_servername_RMI);
        gameController = requests.joinFirstAvailableGame(modelInvokedEvents, nick);
        if (gameController != null) {
            nickname = nick;
            game_id = gameController.getGameId();
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
     * Request the reconnection of a player @param nick to a game @param idGame
     *
     * @param nick of the player who wants to be reconnected
     * @param idGame of the game to be reconnected
     * @throws RemoteException
     * @throws NotBoundException
     */
    @Override
    public void reconnect(String nick, int idGame) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultValues.serverIp, DefaultValues.Default_port_RMI);
        requests = (MainControllerInterface) registry.lookup(DefaultValues.Default_servername_RMI);
        gameController = requests.reconnect(modelInvokedEvents, nick, idGame);
        nickname = nick;
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

    /**
     * Ask the server if it is currently my turn
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean isMyTurn() throws RemoteException {
        return gameController.isThisMyTurn(nickname);
    }

    /**
     * Send a heartbeat to the server
     *
     * @throws RemoteException
     */
    @Override
    public void heartbeat() throws RemoteException {
        if (gameController != null) {
           // System.out.println("Nick in heartbeat: " + nickname);
            gameController.addPing(nickname, modelInvokedEvents);
        }
    }

}
