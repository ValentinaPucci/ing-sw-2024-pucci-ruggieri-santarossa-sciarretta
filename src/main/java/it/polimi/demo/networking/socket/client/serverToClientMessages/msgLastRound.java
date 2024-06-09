package it.polimi.demo.networking.socket.client.serverToClientMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.ModelView;

import java.io.Serial;
import java.rmi.RemoteException;

public class msgLastRound extends SocketServerGenericMessage {
    @Serial
    private static final long serialVersionUID = -5995726049502357378L;
    private ModelView gamemodel;

    public msgLastRound(ModelView gamemodel) {
        super("Last Round");
        this.gamemodel = gamemodel;
    }

    @Override
    public void perform(Listener lis) throws RemoteException {
       lis.lastRound(gamemodel);
    }
}
