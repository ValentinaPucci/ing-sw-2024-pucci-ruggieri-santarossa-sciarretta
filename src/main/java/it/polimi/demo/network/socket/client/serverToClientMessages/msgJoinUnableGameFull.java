package it.polimi.demo.network.socket.client.serverToClientMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.ModelView;

import java.io.Serial;
import java.rmi.RemoteException;

public class msgJoinUnableGameFull extends SocketServerGenericMessage {
    @Serial
    private static final long serialVersionUID = -4670478946104542181L;
    private Player p;
    private ModelView gamemodel;


    public msgJoinUnableGameFull(Player p, ModelView gamemodel) {
        super("Join Unable Game Full");
        this.p = p;
        this.gamemodel = gamemodel;
    }

    @Override
    public void perform(Listener lis) throws RemoteException {
        // lis.JoinUnableGameFull(p,gamemodel);
    }
}
