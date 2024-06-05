package it.polimi.demo.networking.socket.client.gameControllerMessages;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.networking.remoteInterfaces.GameControllerInterface;
import it.polimi.demo.networking.remoteInterfaces.MainControllerInterface;
import it.polimi.demo.networking.socket.client.SocketClientGenericMessage;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * SocketClientMessageSendMessage class.
 * Extends SocketClientGenericMessage and is used to send a new chat message from the client to the server.
 */
public class SocketClientMessageSendMessage extends SocketClientGenericMessage implements Serializable {

    private Message msg;
    private String nick;

    /**
     * Constructor of the class.
     * @param msg the chat message to be sent
     */
    public SocketClientMessageSendMessage(String nick, Message msg) {
        this.nick = nick;
        this.msg = msg;
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
    public GameControllerInterface execute(GameListener lis, MainControllerInterface mainController) throws RemoteException {
        return null;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param gameController the game controller interface
     * @throws RemoteException if there is an error in remote communication
     * @throws GameEndedException if the game has ended
     */
    @Override
    public void execute(GameControllerInterface gameController) throws RemoteException, GameEndedException {
        gameController.sendMessage(nick, msg);
    }
}