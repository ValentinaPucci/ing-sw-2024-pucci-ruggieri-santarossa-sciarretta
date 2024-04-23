package it.polimi.demo.networking.rmi;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.DefaultValues;
import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.networking.ConnectionType;
import it.polimi.demo.view.UIType;
import javafx.application.Application;

import java.io.IOException;
import java.io.Serializable;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


import static it.polimi.demo.networking.ConnectionType.RMI;
import static it.polimi.demo.networking.PrintAsync.printAsync;
import static it.polimi.demo.view.UIType.GUI;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * RMI client to communicate with the remote server using RMI.
 * Implements the MainControllerInterface to receive messages from the server.
 */
public class RmiClient extends UnicastRemoteObject implements VirtualClient, Serializable {

    private static ConnectionType connectionType;
    private static UIType uiType;
    private static String ip;
    private static int port;

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


    /**
     * Requests the creation of a game on the server.
     *
     * @param nick        the nickname of the player creating the game
     * @param num_players the number of players in the game
     * @throws RemoteException if a remote exception occurs
     * @throws NotBoundException if the requested object is not bound in the registry
     */
    @Override
    public void createGame(String nick, int num_players) throws RemoteException, NotBoundException {
        connectToRegistry();
        gameController = requests.createGame(modelInvokedEvents, nick, num_players);
        nickname = nick;
    }

    /**
     * Requests to join the first available game on the server.
     *
     * @param nick the nickname of the player joining the game
     * @throws RemoteException if a remote exception occurs
     * @throws NotBoundException if the requested object is not bound in the registry
     */
    @Override
    public void joinFirstAvailable(String nick) throws RemoteException, NotBoundException {
        connectToRegistry();
        gameController = requests.joinFirstAvailableGame(modelInvokedEvents, nick);
        nickname = nick;
    }

    /**
     * Requests to join a specific game on the server.
     *
     * @param nick   the nickname of the player joining the game
     * @param idGame the ID of the game to join
     * @throws RemoteException if a remote exception occurs
     * @throws NotBoundException if the requested object is not bound in the registry
     */
    @Override
    public void joinGame(String nick, int idGame) throws RemoteException, NotBoundException {
        connectToRegistry();
        gameController = requests.joinGame(modelInvokedEvents, nick, idGame);
        nickname = nick;
    }

    /**
     * Requests to reconnect to a specific game on the server.
     *
     * @param nick   the nickname of the player reconnecting
     * @param idGame the ID of the game to reconnect to
     * @throws RemoteException if a remote exception occurs
     * @throws NotBoundException if the requested object is not bound in the registry
     */
    @Override
    public void reconnect(String nick, int idGame) throws RemoteException, NotBoundException {
        connectToRegistry();
        gameController = requests.reconnect(modelInvokedEvents, nick, idGame);
        nickname = nick;
    }

    /**
     * Requests to leave a game on the server.
     *
     * @param nick   the nickname of the player leaving the game
     * @param idGame the ID of the game to leave
     * @throws IOException if an I/O exception occurs
     * @throws NotBoundException if the requested object is not bound in the registry
     */
    @Override
    public void leave(String nick, int idGame) throws IOException, NotBoundException {
        connectToRegistry();
        requests.leaveGame(modelInvokedEvents, nick, idGame);
        gameController = null;
        nickname = null;
    }

    /**
     * Connects to the RMI registry.
     *
     * @throws RemoteException if a remote exception occurs
     * @throws NotBoundException if the requested object is not bound in the registry
     */
    private void connectToRegistry() throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultValues.Server_ip, DefaultValues.Default_port_RMI);
        requests = (MainControllerInterface) registry.lookup(DefaultValues.RMI_ServerName);
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

    @Override
    public boolean isMyTurn() throws RemoteException {
        return gameController.isMyTurn(nickname);
    }

    @Override
    public void drawCard(String player_nickname, int index) throws IOException {
        gameController.drawCard(player_nickname, index);
    }

    @Override
    public void placeCard(ResourceCard card_chosen, int x, int y) throws IOException{
        gameController.placeCard(card_chosen, gameController.getPlayerEntity(nickname), x, y);
    }

    @Override
    public void placeCard(GoldCard card_chosen, int x, int y) throws IOException{
        gameController.placeCard(card_chosen, gameController.getPlayerEntity(nickname), x, y);
    }

    /**
     * Send a heartbeat to the server
     *
     * @throws RemoteException
     */
    @Override
    public void heartbeat() throws RemoteException {
        if (gameController != null) {
            gameController.addPing(nickname, modelInvokedEvents);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Inizializza ip e port
        askConnectionType(scanner);
        askArgs(scanner);
        askUIType(scanner);
        printAsync("Connecting to " + ip + ":" + port + " using " + connectionType + "...");


        switch (uiType) {
            case GUI:
                //Application.launch(GUIApplication.class, connectionType.toString());
                printAsync("GUI + " + connectionType + " CHOSEN");
                break;
            case TUI:
                printAsync("TUI + " + connectionType + " CHOSEN");
                //new GameFlow(connectionType);
                break;
        }

//        try {
//            switch (uiType) {
//                case GUI:
//                    //Application.launch(GUIApplication.class, connectionType.toString());
//                    printAsync("GUI + " + connectionType + "CHOSEN");
//                    break;
//                case TUI:
//                    printAsync("TUI + " + connectionType + "CHOSEN");
//                    //new GameFlow(connectionType);
//                    break;
//            }
//        } catch (RemoteException | NotBoundException e) {
//            System.err.println("Cannot connect to server. Exiting...");
//            System.exit(1);
//        }
    }

    private static void askConnectionType(Scanner in) {
        printAsync("Select connection type:");
        for (ConnectionType value : ConnectionType.values()) {
            printAsync((value.ordinal() + 1) + ". " + value);
        }

        int clientTypeInt = getNumericInput(in, "Invalid connection type. Please retry: ");
        connectionType = ConnectionType.values()[clientTypeInt - 1];
    }

    private static void askArgs(Scanner in) {
        System.out.print("Enter server IP (blank for localhost): ");
        ip = getInputWithMessage(in, "Invalid IP address. Please retry: ");
        if (ip.isBlank()) {
            ip = "localhost";
        }

        System.out.print("Enter server port (blank for default): ");
        String portString = in.nextLine();
        if (portString.isBlank()) {
            if (connectionType == RMI)
                port = DefaultValues.Default_port_RMI; // Set to default port
            else
                port = DefaultValues.Default_port_SOCKET; // Set to default port
        } else {
            port = Integer.parseInt(portString);
        }
    }


    private static void askUIType(Scanner in) {
        printAsync("Select UI type:");
        for (UIType value : UIType.values()) {
            printAsync((value.ordinal() + 1) + ". " + value);
        }

        int uiTypeInt = getNumericInput(in, "Invalid UI type. Please retry: ") - 1;
        uiType = UIType.values()[uiTypeInt];
    }


    private static int getNumericInput(Scanner scanner, String errorMessage) {
        int selection = -1;
        String input;
        do {
            input = scanner.nextLine();
            try {
                selection = Integer.parseInt(input);
                if (selection < 1 || selection > 2) {
                    printAsync(errorMessage);
                }
            } catch (NumberFormatException e) {
                printAsync("Invalid input");
            }
        } while (selection < 1 || selection > 2);
        return selection;
    }

    private static String getInputWithMessage(Scanner scanner, String message) {
        String input;
        do {
            input = scanner.nextLine();
            if (!input.isEmpty() && !isValidIPAddress(input)) {
                printAsync(message);
            }
        } while (!input.isEmpty() && !isValidIPAddress(input));
        return input;
    }

    private static boolean isValidIPAddress(String input) {
        List<String> parts = Arrays.asList(input.split("\\."));
        if (parts.size() != 4) {
            return false;
        }
        for (String part : parts) {
            try {
                int value = Integer.parseInt(part);
                if (value < 0 || value > 255) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }



}
