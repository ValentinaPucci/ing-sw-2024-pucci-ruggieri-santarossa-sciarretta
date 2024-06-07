package it.polimi.demo.networking.socket.client.serverToClientMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.ModelView;

import java.rmi.RemoteException;

/**
 * msgSentMessage class.
 * Extends SocketServerGenericMessage and is used to send a message to the client
 * indicating that a chat message has been sent.
 */
public class msgMessageSent extends SocketServerGenericMessage {
    private String nick;
    private Message msg;
    private ModelView gameModel;

    /**
     * Constructor of the class.
     * @param gameModel the immutable game model
     * @param msg the sent chat message
     */
    public msgMessageSent(ModelView gameModel, String nickname, Message msg) {
        this.nick = nickname;
        this.gameModel = gameModel;
        this.msg = msg;
    }

    /**
     * Method to execute the corresponding action for the message.
     * @param lis the game listener
     * @throws RemoteException if there is a remote exception
     */
    @Override
    public void execute(Listener lis) throws RemoteException {
        lis.messageSent(gameModel, nick, msg);
    }
}
