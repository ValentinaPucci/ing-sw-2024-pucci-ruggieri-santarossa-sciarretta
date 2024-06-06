package it.polimi.demo.networking.socket.client.mainControllerMessages;

import it.polimi.demo.listener.Listener;
import it.polimi.demo.networking.rmi.remoteInterfaces.GameControllerInterface;
import it.polimi.demo.networking.rmi.remoteInterfaces.MainControllerInterface;
import it.polimi.demo.networking.socket.client.SocketClientGenericMessage;

import java.rmi.RemoteException;

/**
 * SocketClientMessageReconnect class.
 * Extends SocketClientGenericMessage and is used to send a message to the server
 * indicating the request to reconnect to the last game joined.
 */
public class SocketClientMessageReconnect extends SocketClientGenericMessage {

    private int idGame;

    /**
     * Constructor of the class.
     * @param nick the player's nickname
     * @param idGame the ID of the game to reconnect to
     */
    public SocketClientMessageReconnect(String nick, int idGame) {
        this.idGame = idGame;
        this.nick = nick;
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
        return mainController.reconnect(lis, nick, idGame);
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
