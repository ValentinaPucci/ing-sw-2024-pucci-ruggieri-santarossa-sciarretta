package it.polimi.demo.network.socket.client.gameControllerMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.network.GameControllerInterface;
import it.polimi.demo.network.MainControllerInterface;
import it.polimi.demo.network.socket.client.SocketClientGameControllerMex;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Class to send a message in the CHAT.
 */
public class SocketClientMsgSendMessage extends SocketClientGameControllerMex implements Serializable {

    @Serial
    private static final long serialVersionUID = -8369247984549388589L;
    private Message chat_msg;
    private String nick;

    /**
     * Constructor of the class.
     * @param nick
     * @param chat_msg
     */
    public SocketClientMsgSendMessage(String nick, Message chat_msg) {
        this.nick = nick;
        this.chat_msg = chat_msg;
    }

    /**
     * Method to perform sendMessage of the chat on the game controller.
     * @param gameController the game controller interface
     * @throws RemoteException
     * @throws GameEndedException
     */
    @Override
    public void performOnGameController(GameControllerInterface gameController) throws RemoteException, GameEndedException {
        gameController.sendMessage(nick, chat_msg);
    }
}