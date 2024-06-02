package it.polimi.demo.networking;

import it.polimi.demo.DefaultValues;
import it.polimi.demo.networking.rmi.TaskOnNetworkDisconnection;
import it.polimi.demo.view.flow.CommonClientActions;
import it.polimi.demo.view.flow.Flow;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

import static it.polimi.demo.networking.PrintAsync.printAsync;

public class HeartbeatSender extends Thread implements Serializable {

    private Flow flow;
    private CommonClientActions server;

    public HeartbeatSender(Flow flow, CommonClientActions server) {
        this.flow = flow;
        this.server = server;
    }


    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                server.heartbeat();
            } catch (RemoteException | NotBoundException e) {
                printAsync("Connection to server lost! Impossible to send heartbeat...");
            }
        }

    }

}
