package it.polimi.demo.networking.socket.client.mainControllerMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.networking.remoteInterfaces.GameControllerInterface;
import it.polimi.demo.networking.remoteInterfaces.MainControllerInterface;
import it.polimi.demo.networking.socket.client.SocketClientGenericMessage;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * SocketClientMessageCreateGame class.
 * Extends SocketClientGenericMessage and is used to send a message to the server
 * indicating the request to create a new game.
 */
public class SocketClientMessageCreateGame extends SocketClientGenericMessage implements Serializable {
    int num_players;
    /**
     * Constructor of the class.
     * @param nick the player's nickname
     */
    public SocketClientMessageCreateGame(String nick, int num_players) {
        this.nick = nick;
        this.num_players = num_players;
        this.isMessageForMainController = true;
    }
    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @param mainController the main controller of the application
     * @return the game controller interface
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public GameControllerInterface execute(Listener lis, MainControllerInterface mainController) throws RemoteException {
        return mainController.createGame(lis, nick, num_players);
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param mainController the game controller interface
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(GameControllerInterface mainController) throws RemoteException {

    }
}
