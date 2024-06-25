package it.polimi.demo.network.socket.client.ServerToClientMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.ModelView;

import java.io.IOException;
import java.io.Serial;
import java.rmi.RemoteException;

public class msgPlayerJoined extends S2CGenericMessage {
    @Serial
    private static final long serialVersionUID = 8811857756384930907L;
    private ModelView gamemodel;

    public msgPlayerJoined(ModelView gamemodel) {
        super("Player Joined");
        this.gamemodel = gamemodel;
    }

    /**
     * Method to perform action on the listener.
     * @param lis
     * @throws RemoteException
     */
    @Override
    public void perform(Listener lis) throws IOException, InterruptedException {
        lis.playerJoined(gamemodel);
    }
}
