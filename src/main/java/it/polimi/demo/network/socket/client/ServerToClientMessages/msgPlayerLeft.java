package it.polimi.demo.network.socket.client.ServerToClientMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.ModelView;

import java.io.Serial;
import java.rmi.RemoteException;

public class msgPlayerLeft extends S2CGenericMessage {
    @Serial
    private static final long serialVersionUID = -5445509954606519L;
    private ModelView gamemodel;
    private String nick;

    public msgPlayerLeft(ModelView gamemodel, String nick) {
        super("Player Left");
        this.gamemodel = gamemodel;
        this.nick=nick;
    }

    /**
     * Method to perform action on the listener.
     * @param lis
     * @throws RemoteException
     */
    @Override
    public void perform(Listener lis) throws RemoteException {
        lis.playerLeft(gamemodel,nick);
    }
}
