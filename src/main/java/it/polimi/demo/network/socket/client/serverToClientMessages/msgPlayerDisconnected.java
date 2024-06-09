package it.polimi.demo.network.socket.client.serverToClientMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.ModelView;

import java.io.Serial;
import java.rmi.RemoteException;

public class msgPlayerDisconnected extends SocketServerGenericMessage {
    @Serial
    private static final long serialVersionUID = 888520400066911096L;
    private String nick;
    private ModelView gameModel;

    public msgPlayerDisconnected(ModelView gameModel, String nick) {
        super("Player Disconnected");
        this.nick = nick;
        this.gameModel=gameModel;
    }

    @Override
    public void perform(Listener lis) throws RemoteException {
        lis.playerDisconnected(gameModel,nick);
    }
}
