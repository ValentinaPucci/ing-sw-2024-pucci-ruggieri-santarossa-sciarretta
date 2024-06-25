package it.polimi.demo.network.socket.client.ServerToClientMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.ModelView;

import java.io.Serial;
import java.rmi.RemoteException;

public class msgMessageSent extends S2CGenericMessage {
    @Serial
    private static final long serialVersionUID = 8655827387792587552L;
    private String nick;
    private Message msg;
    private ModelView gameModel;

    public msgMessageSent(ModelView gameModel, String nickname, Message msg) {
        super("Message Sent");
        this.nick = nickname;
        this.gameModel = gameModel;
        this.msg = msg;
    }


    /**
     * Method to perform action on the listener.
     * @param lis
     * @throws RemoteException
     */
    @Override
    public void perform(Listener lis) throws RemoteException {
        lis.messageSent(gameModel, nick, msg);
    }
}
