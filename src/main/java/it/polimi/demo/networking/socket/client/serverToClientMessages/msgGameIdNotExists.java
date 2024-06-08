package it.polimi.demo.networking.socket.client.serverToClientMessages;

import it.polimi.demo.observer.Listener;

import java.io.Serial;
import java.rmi.RemoteException;


public class msgGameIdNotExists extends SocketServerGenericMessage {
    @Serial
    private static final long serialVersionUID = 1096228453655961021L;
    private int gameid;


    public msgGameIdNotExists(int gameid) {
        this.gameid = gameid;
    }

    @Override
    public void perform(Listener lis) throws RemoteException {
        lis.gameIdNotExists(gameid);
    }
}
