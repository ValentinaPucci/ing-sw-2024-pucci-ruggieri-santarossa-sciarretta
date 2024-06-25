package it.polimi.demo.network.socket.client.ServerToClientMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.ModelView;

import java.io.IOException;
import java.io.Serial;
import java.rmi.RemoteException;

public class msgPlayerIsReadyToStart extends S2CGenericMessage {
    @Serial
    private static final long serialVersionUID = -1529885453195001299L;
    private ModelView model;
    private String nick;

    public msgPlayerIsReadyToStart(ModelView model, String nick) {
        super("Player is ready to start");
        this.model = model;
        this.nick = nick;
    }

    /**
     * Method to perform action on the listener.
     * @param lis
     * @throws RemoteException
     */
    @Override
    public void perform(Listener lis) throws IOException, InterruptedException {
        lis.playerIsReadyToStart(model, nick);
    }
}
