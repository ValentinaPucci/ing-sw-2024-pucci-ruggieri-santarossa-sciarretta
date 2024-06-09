package it.polimi.demo.network;

import it.polimi.demo.view.flow.ClientInterface;
import it.polimi.demo.view.flow.Dynamics;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;

import static it.polimi.demo.network.StaticPrinter.staticPrinter;

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
                StaticPrinter.staticPrinter("Connection to server lost! Impossible to send heartbeat...");
            } catch (NotBoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
