package it.polimi.demo.network.socket.client.ServerToClientMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.ModelView;

import java.io.Serial;
import java.rmi.RemoteException;

public class msgGameStarted extends S2CGenericMessage {

    @Serial
    private static final long serialVersionUID = 6895920175638029792L;
    private ModelView model;


    public msgGameStarted(ModelView model) {
        super("Game Started");
        this.model = model;
    }

    /**
     * Method to perform action on the listener.
     * @param lis
     * @throws RemoteException
     */
    @Override
    public void perform(Listener lis) throws RemoteException {
        //logMessage();
        lis.gameStarted(model);
    }
}
