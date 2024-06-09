package it.polimi.demo.networking.socket.client.serverToClientMessages;

import it.polimi.demo.model.ModelView;
import it.polimi.demo.observer.Listener;

import java.io.Serial;
import java.rmi.RemoteException;

public class msgIllegalMoveBecauseOf extends SocketServerGenericMessage {

    @Serial
    private static final long serialVersionUID = 8555800278098017630L;
    private ModelView gamemodel;
    private String message;


    public msgIllegalMoveBecauseOf(ModelView gamemodel, String message) {
        super("Illegal Move Because Of");
        this.gamemodel = gamemodel;
        this.message = message;
    }


    @Override
    public void perform(Listener lis) throws RemoteException {
        lis.illegalMoveBecauseOf(gamemodel, message);
    }
}
