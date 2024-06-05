package it.polimi.demo.networking;
import it.polimi.demo.view.flow.CommonClientActions;
import it.polimi.demo.view.flow.Flow;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;

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
            } catch (NotBoundException | IOException e) {
                printAsync("Connection to server lost! Impossible to send heartbeat...");
            }
        }
    }

}
