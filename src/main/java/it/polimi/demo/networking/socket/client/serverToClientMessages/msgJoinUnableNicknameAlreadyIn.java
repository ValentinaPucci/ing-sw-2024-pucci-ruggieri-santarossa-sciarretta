package it.polimi.demo.networking.socket.client.serverToClientMessages;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.Player;

import java.io.Serial;
import java.rmi.RemoteException;

public class msgJoinUnableNicknameAlreadyIn extends SocketServerGenericMessage {


    @Serial
    private static final long serialVersionUID = 940485874886681298L;
    private Player wantedToJoin;

    public msgJoinUnableNicknameAlreadyIn(Player wantedToJoin) {
        super("Join Unable Nickname Already In");
        this.wantedToJoin = wantedToJoin;
    }

    @Override
    public void perform(Listener lis) throws RemoteException {
        lis.joinUnableNicknameAlreadyIn(wantedToJoin);
    }
}
