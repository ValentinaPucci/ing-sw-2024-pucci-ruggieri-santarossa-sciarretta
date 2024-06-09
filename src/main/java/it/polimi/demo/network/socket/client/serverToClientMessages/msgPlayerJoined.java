package it.polimi.demo.network.socket.client.serverToClientMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.ModelView;

import java.io.IOException;
import java.io.Serial;

public class msgPlayerJoined extends SocketServerGenericMessage {
    @Serial
    private static final long serialVersionUID = 8811857756384930907L;
    private ModelView gamemodel;

    public msgPlayerJoined(ModelView gamemodel) {
        this.gamemodel = gamemodel;
    }

    @Override
    public void perform(Listener lis) throws IOException, InterruptedException {
        lis.playerJoined(gamemodel);
    }
}
