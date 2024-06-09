package it.polimi.demo.network;

import it.polimi.demo.view.dynamic.ClientInterface;
import it.polimi.demo.view.dynamic.Dynamic;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;

import static it.polimi.demo.network.StaticPrinter.staticPrinter;

public class PingSender extends Thread implements Serializable {

    private Dynamic dynamic;
    private ClientInterface server;

    public PingSender(Dynamic dynamic, ClientInterface server) {
        this.dynamic = dynamic;
        this.server = server;
    }


    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                server.ping();
            } catch (IOException e) {
                StaticPrinter.staticPrinter("Connection to server lost! Impossible to send ping...");
            } catch (NotBoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
