package it.polimi.demo.networking.rmi;

import it.polimi.demo.networking.rmi.remoteInterfaces.MainControllerInterface;
import it.polimi.demo.networking.rmi.remoteInterfaces.VirtualServer;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import it.polimi.demo.model.DefaultValues;
import java.util.Map;
import java.util.Scanner;

//
public class RmiServer extends UnicastRemoteObject implements VirtualServer {

    final List<MainControllerInterface> virtualClients;

    protected RmiServer() throws RemoteException {
        this.virtualClients = new ArrayList<>();
    }

    public void login(MainControllerInterface vc){
        this.virtualClients.add(vc);
    }

    public void send(String message) throws RemoteException {
        System.out.println("Server received: " + message);
        for(MainControllerInterface vc: virtualClients){
            vc.receive(message);
        }
    }

    public static void main(String args[]){
        try{
            new RmiServer().startServer();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void startServer() throws RemoteException {
// Bind the remote object's stub in the registry
//DO NOT CALL Registry registry = LocateRegistry.getRegistry();
        Registry registry = LocateRegistry.createRegistry(DefaultValues.PORT);
        try {
            registry.bind("ServerTest", this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Server ready");
    }
}
