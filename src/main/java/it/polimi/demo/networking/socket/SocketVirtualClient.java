package it.polimi.demo.networking.socket;
import java.rmi.Remote;
import java.rmi.RemoteException;

//IMPLEMENTA I METODI DEL SOCKET SERVER

public interface SocketVirtualClient {
    public void showUpdate(Integer number);
    public void reportError(String details);
}

