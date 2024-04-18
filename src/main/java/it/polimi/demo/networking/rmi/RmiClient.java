package it.polimi.demo.networking.rmi;

import it.polimi.demo.controller.MainController;
import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.DefaultValues;
import it.polimi.demo.networking.rmi.remoteInterfaces.GameControllerInterface;
import it.polimi.demo.networking.rmi.remoteInterfaces.MainControllerInterface;
import it.polimi.demo.networking.rmi.remoteInterfaces.VirtualServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RmiClient implements MainControllerInterface, Serializable  {

    private VirtualServer vs;

    private void startClient() throws Exception {
// Getting the registry
        Registry registry;
        registry = LocateRegistry.getRegistry(DefaultValues.SERVER_NAME, DefaultValues.PORT);
// Looking up the registry for the remote object
        this.vs = (VirtualServer) registry.lookup("ServerTest");
        this.vs.login(this);
        inputLoop();
    }

    public static void main( String[] args )
    {
        try {
            new RmiClient().startClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void inputLoop() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String message;
        try {
            while ((message = br.readLine()) != null) {
                vs.send(message);
            }
        } catch (IOException e) {
            System.err.println("Errore di input/output: " + e.getMessage());
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                System.err.println("Errore durante la chiusura del BufferedReader: " + e.getMessage());
            }
        }
    }


    @Override
    public void receive(String message) throws RemoteException {
        System.out.println(message);
    }

    @Override
    public GameControllerInterface createGame(GameListener lis, String nick) throws RemoteException {
        return null;
    }

    @Override
    public GameControllerInterface joinFirstAvailableGame(GameListener lis, String nick) throws RemoteException {
        return null;
    }

    @Override
    public GameControllerInterface joinGame(GameListener lis, String nick, int idGame) throws RemoteException {
        return null;
    }

    @Override
    public GameControllerInterface reconnect(GameListener lis, String nick, int idGame) throws RemoteException {
        return null;
    }

    @Override
    public GameControllerInterface leaveGame(GameListener lis, String nick, int idGame) throws RemoteException {
        return null;
    }
}