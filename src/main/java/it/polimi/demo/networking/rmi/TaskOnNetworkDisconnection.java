package it.polimi.demo.networking.rmi;

import it.polimi.demo.view.flow.Dynamics;

import java.util.TimerTask;

public class TaskOnNetworkDisconnection extends TimerTask {
    private Dynamics dynamics;
    public TaskOnNetworkDisconnection(Dynamics dynamics){
        this.dynamics = dynamics;
    }
    public void run() {
        dynamics.noConnectionError();
    }
}