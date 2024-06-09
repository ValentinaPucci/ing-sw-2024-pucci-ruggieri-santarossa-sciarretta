package it.polimi.demo.networking.socket.client.serverToClientMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.ModelView;

import java.io.IOException;
import java.io.Serial;

public class msgPlayerIsReadyToStart extends SocketServerGenericMessage {
    @Serial
    private static final long serialVersionUID = -1529885453195001299L;
    private ModelView model;
    private String nick;

    public msgPlayerIsReadyToStart(ModelView model, String nick) {
        super("Player is ready to start");
        this.model = model;
        this.nick = nick;
    }

    @Override
    public void perform(Listener lis) throws IOException, InterruptedException {
        lis.playerIsReadyToStart(model, nick);
    }
}
