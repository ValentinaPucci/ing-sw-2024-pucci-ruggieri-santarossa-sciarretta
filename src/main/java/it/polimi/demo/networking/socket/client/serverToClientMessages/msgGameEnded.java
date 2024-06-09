package it.polimi.demo.networking.socket.client.serverToClientMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.ModelView;

import java.io.Serial;
import java.rmi.RemoteException;


public class msgGameEnded extends SocketServerGenericMessage {
    @Serial
    private static final long serialVersionUID = 3481171846738649670L;
    private ModelView gamemodel;


    public msgGameEnded(ModelView gamemodel) {
        super("Game Ended");
        this.gamemodel = gamemodel;
    }

    @Override
    public void perform(Listener lis) throws RemoteException {
        logMessage();
        lis.gameEnded(gamemodel);
    }
}
