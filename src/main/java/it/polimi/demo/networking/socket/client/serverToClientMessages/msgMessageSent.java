package it.polimi.demo.networking.socket.client.serverToClientMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.ModelView;

import java.io.Serial;
import java.rmi.RemoteException;

public class msgMessageSent extends SocketServerGenericMessage {
    @Serial
    private static final long serialVersionUID = 8655827387792587552L;
    private String nick;
    private Message msg;
    private ModelView gameModel;

    public msgMessageSent(ModelView gameModel, String nickname, Message msg) {
        this.nick = nickname;
        this.gameModel = gameModel;
        this.msg = msg;
    }


    @Override
    public void perform(Listener lis) throws RemoteException {
        lis.messageSent(gameModel, nick, msg);
    }
}
