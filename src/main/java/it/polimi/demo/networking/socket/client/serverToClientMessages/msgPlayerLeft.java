package it.polimi.demo.networking.socket.client.serverToClientMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.ModelView;

import java.io.Serial;
import java.rmi.RemoteException;

public class msgPlayerLeft extends SocketServerGenericMessage{
    @Serial
    private static final long serialVersionUID = -5445509954606519L;
    private ModelView gamemodel;
    private String nick;

    public msgPlayerLeft(ModelView gamemodel, String nick) {
        this.gamemodel = gamemodel;
        this.nick=nick;
    }

    @Override
    public void perform(Listener lis) throws RemoteException {
        lis.playerLeft(gamemodel,nick);
    }
}
