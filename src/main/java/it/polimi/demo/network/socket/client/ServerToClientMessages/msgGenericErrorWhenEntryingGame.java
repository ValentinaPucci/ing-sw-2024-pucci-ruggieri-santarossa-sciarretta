package it.polimi.demo.network.socket.client.ServerToClientMessages;

import it.polimi.demo.observer.Listener;

import java.io.Serial;
import java.rmi.RemoteException;

public class msgGenericErrorWhenEntryingGame extends S2CGenericMessage {

    @Serial
    private static final long serialVersionUID = 6589522081938255852L;
    private String why;

    public msgGenericErrorWhenEntryingGame(String why) {

        super("Generic Error When Entering Game");
        this.why=why;
    }

    /**
     * Method to perform action on the listener.
     * @param lis
     * @throws RemoteException
     */
    @Override
    public void perform(Listener lis) throws RemoteException {
        //logMessage();
        lis.genericErrorWhenEnteringGame(why);
    }
}
