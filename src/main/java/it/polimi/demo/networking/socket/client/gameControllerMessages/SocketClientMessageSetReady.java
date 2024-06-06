package it.polimi.demo.networking.socket.client.gameControllerMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.networking.remoteInterfaces.GameControllerInterface;
import it.polimi.demo.networking.remoteInterfaces.MainControllerInterface;
import it.polimi.demo.networking.socket.client.SocketClientGenericMessage;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * SocketClientMessageSetReady class.
 * Extends SocketClientGenericMessage and is used to send a message to the server
 * indicating that a player is ready to start the game.
 */
public class SocketClientMessageSetReady extends SocketClientGenericMessage implements Serializable {

    /**
     * Constructor of the class.
     * @param nick the player's nickname
     */
    public SocketClientMessageSetReady(String nick) {
        this.nick = nick;
        this.isMessageForMainController = false;
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
        return null;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param gameController the game controller interface
     * @throws RemoteException if there is an error in remote communication
     */
    @Override
    public void execute(GameControllerInterface gameController) throws RemoteException {
        System.out.println("GameController sent says: " + gameController.getPlayerEntity(nick));
        gameController.playerIsReadyToStart(this.nick);
    }
}
