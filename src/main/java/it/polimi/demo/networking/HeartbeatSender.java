package it.polimi.demo.networking;

import it.polimi.demo.DefaultValues;
import it.polimi.demo.networking.rmi.TaskOnNetworkDisconnection;
import it.polimi.demo.view.flow.CommonClientActions;
import it.polimi.demo.view.flow.Flow;

import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

import static it.polimi.demo.networking.PrintAsync.printAsync;

public class HeartbeatSender extends Thread {

    private Flow flow;
    private CommonClientActions server;

    public HeartbeatSender(Flow flow, CommonClientActions server) {
        this.flow=flow;
        this.server=server;
    }


    @Override
    public void run() {
        //For the heartbeat
        while (!Thread.interrupted()) {
            Timer timer = new Timer();
            TimerTask task = new TaskOnNetworkDisconnection(flow);
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
