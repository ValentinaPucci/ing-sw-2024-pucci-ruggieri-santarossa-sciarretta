package it.polimi.demo.network.socket.client.ServerToClientMessages;

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

    /**
     * Method to perform action on the listener.
     * @param lis
     * @throws RemoteException
     */
    @Override
    public void perform(Listener lis) throws RemoteException {
        //logMessage();
        lis.gameEnded(gamemodel);
    }
}
