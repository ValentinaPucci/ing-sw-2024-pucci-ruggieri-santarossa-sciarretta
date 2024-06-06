package it.polimi.demo.networking;

import it.polimi.demo.DefaultValues;
import it.polimi.demo.networking.rmi.TaskOnNetworkDisconnection;
import it.polimi.demo.view.flow.ClientInterface;
import it.polimi.demo.view.flow.Dynamics;

import java.io.Serializable;
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
        //For the heartbeat
        while (!Thread.interrupted()) {
            Timer timer = new Timer();
            TimerTask task = new TaskOnNetworkDisconnection(dynamics);
            timer.schedule(task, DefaultValues.timeoutConnection_millis);
            //send heartbeat so the server knows I am still online
            try {
                server.heartbeat();
            } catch (RemoteException e) {
                printAsync("Connection to server lost! Impossible to send heartbeat...");
            }
            timer.cancel();

            try {
                Thread.sleep(DefaultValues.secondToWaitToSend_heartbeat);
            } catch (InterruptedException ignored) {}
        }

    }

}
