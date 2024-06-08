package it.polimi.demo.networking.socket.client.serverToClientMessages;

import it.polimi.demo.observer.Listener;

import java.io.Serial;
import java.rmi.RemoteException;

public class msgGenericErrorWhenEntryingGame extends SocketServerGenericMessage{

    @Serial
    private static final long serialVersionUID = 6589522081938255852L;
    private String why;

    public msgGenericErrorWhenEntryingGame(String why) {
        this.why=why;
    }

    @Override
    public void perform(Listener lis) throws RemoteException {
        lis.genericErrorWhenEnteringGame(why);
    }
}
