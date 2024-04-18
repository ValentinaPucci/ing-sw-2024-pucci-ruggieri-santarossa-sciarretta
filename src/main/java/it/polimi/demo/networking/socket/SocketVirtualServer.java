package it.polimi.demo.networking.socket;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SocketVirtualServer  {
    public void connect(SocketVirtualClient client);

    public void add(Integer number);

    public void reset();
}
