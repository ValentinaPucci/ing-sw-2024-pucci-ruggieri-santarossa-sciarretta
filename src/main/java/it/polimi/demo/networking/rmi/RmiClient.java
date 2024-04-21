package it.polimi.demo.networking.rmi;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.DefaultValues;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.networking.rmi.remoteInterfaces.GameControllerInterface;
import it.polimi.demo.networking.rmi.remoteInterfaces.MainControllerInterface;
import it.polimi.demo.networking.rmi.remoteInterfaces.VirtualClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


import static it.polimi.demo.networking.PrintAsync.printAsync;
import static it.polimi.demo.networking.PrintAsync.printAsyncNoLine;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * RMI client to communicate with the remote server using RMI.
 * Implements the MainControllerInterface to receive messages from the server.
 */
public class RmiClient extends UnicastRemoteObject implements VirtualClient, Serializable {

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
     * The nickname associated to the client (!=null only when connected in a game)
     */
    private static String nickname = "Paul";

    /**
     * Registry of the RMI
     */
    private Registry registry;

    protected RmiClient() throws RemoteException {
    }


    @Override
    public void login(MainControllerInterface cc) throws RemoteException {

    }

    @Override
    public void send(String message) throws RemoteException {

    }

    /**
     * Request the creation of a Game to the server
     *
     * @param nick of the player who wants to create a game
     * @throws RemoteException
     * @throws NotBoundException
     */
    @Override
    public void createGame(String nick, int num_players) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultValues.SERVER_NAME, DefaultValues.PORT);
        requests = (MainControllerInterface) registry.lookup(DefaultValues.Default_servername_RMI);
        gameController = requests.createGame(modelInvokedEvents, nick, num_players);
        nickname = nick;
    }

    /**
     * Request to join a server game (first game available)
     *
     * @param nick of the player who wants to join a game
     * @throws RemoteException
     * @throws NotBoundException
     */
    @Override
    public void joinFirstAvailable(String nick) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultValues.SERVER_NAME, DefaultValues.PORT);
        requests = (MainControllerInterface) registry.lookup(DefaultValues.Default_servername_RMI);
        gameController = requests.joinFirstAvailableGame(modelInvokedEvents, nick);
        nickname = nick;
    }



    /**
     * Request to join a specific server game
     *
     * @param nick of the player who wants to join a specific game
     * @throws RemoteException
     * @throws NotBoundException
     */
    public void joinGame(String nick, int idGame) throws RemoteException, NotBoundException {

        registry = LocateRegistry.getRegistry(DefaultValues.SERVER_NAME, DefaultValues.PORT);
        requests = (MainControllerInterface) registry.lookup(DefaultValues.Default_servername_RMI);
        gameController = requests.joinGame(modelInvokedEvents, nick, idGame);

        nickname = nick;

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
        registry = LocateRegistry.getRegistry(DefaultValues.SERVER_NAME, DefaultValues.PORT);
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

        registry = LocateRegistry.getRegistry(DefaultValues.SERVER_NAME, DefaultValues.PORT);
        requests = (MainControllerInterface) registry.lookup(DefaultValues.Default_servername_RMI);

        requests.leaveGame(modelInvokedEvents, nick, idGame);
        gameController = null;
        nickname = null;
    }


    /**
     * Send a message to the server
     *
     * @param msg message to send
     * @throws RemoteException
     */
    @Override
    public void sendMessage(Message msg) throws RemoteException {
        gameController.sendMessage(msg);
    }

    /**
     * Notify the server that a client is ready to start
     *
     * @throws RemoteException
     */
    @Override
    public void setAsReady() throws RemoteException {
        if (gameController != null) {
            gameController.setPlayerAsReadyToStart(nickname);
        }
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

    @Override
    public void drawCard(String player_nickname, int index) throws IOException {
        gameController.drawCard(player_nickname, index);
    }

    @Override
    public void placeCard(ResourceCard card_chosen, int x, int y) throws IOException, GameEndedException {
        gameController.placeCard(card_chosen, gameController.getPlayerEntity(nickname), x, y);
    }

    @Override
    public void heartbeat() throws RemoteException {

    }

    public static void main(String[] args) {
        try {
            // Ottieni il riferimento al registro RMI
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Ottieni il riferimento al server RMI utilizzando il nome binding specificato durante la registrazione
            MainControllerInterface server = (MainControllerInterface) registry.lookup("Server");

            // Chiamata al metodo remoto del server
            String response = server.sayHello();

            // Visualizza la risposta del server
            System.out.println("Risposta dal server: " + response);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Inserisci il numero di giocatori: ");
            int numberOfPlayers = scanner.nextInt();
            server.createGame(modelInvokedEvents, nickname, numberOfPlayers);


        } catch (Exception e) {
            System.err.println("Errore nel client RMI: " + e.toString());
            e.printStackTrace();
        }
    }
}
