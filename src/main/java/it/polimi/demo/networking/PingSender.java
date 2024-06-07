package it.polimi.demo.networking;

import it.polimi.demo.DefaultValues;
import it.polimi.demo.networking.rmi.TaskOnNetworkDisconnection;
import it.polimi.demo.view.flow.ClientInterface;
import it.polimi.demo.view.flow.Dynamics;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

import static it.polimi.demo.networking.PrintAsync.printAsync;

public class PingSender extends Thread implements Serializable {

    private Dynamics dynamics;
    private ClientInterface server;

    public PingSender(Dynamics dynamics, ClientInterface server) {
        this.dynamics = dynamics;
        this.server = server;
    }


    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                server.heartbeat();
            } catch (IOException e) {
                printAsync("Connection to server lost! Impossible to send heartbeat...");
            } catch (NotBoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
