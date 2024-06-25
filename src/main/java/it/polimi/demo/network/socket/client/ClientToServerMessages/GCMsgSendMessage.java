package it.polimi.demo.network.socket.client.ClientToServerMessages;

import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.network.GameControllerInterface;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Class to send a message in the CHAT.
 */
public class GCMsgSendMessage extends GCMsg implements Serializable {

    @Serial
    private static final long serialVersionUID = -8369247984549388589L;
    private Message chat_msg;
    private String nick;

    /**
     * Constructor of the class.
     * @param nick
     * @param chat_msg
     */
    public GCMsgSendMessage(String nick, Message chat_msg) {
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