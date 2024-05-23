package it.polimi.demo.networking.rmi;

import it.polimi.demo.view.flow.Flow;

import java.util.TimerTask;

public class TaskOnNetworkDisconnection extends TimerTask {
    private Flow flow;
    public TaskOnNetworkDisconnection(Flow flow){
        this.flow = flow;
    }
    public void run() {
        flow.noConnectionError();
    }
}