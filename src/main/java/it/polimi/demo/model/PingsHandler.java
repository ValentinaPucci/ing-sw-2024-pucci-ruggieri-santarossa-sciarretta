package it.polimi.demo.model;

import it.polimi.demo.observer.Listener;


public class PingsHandler {
    private Listener listener;
    private Ping ping;

    public PingsHandler(Listener listener, Ping ping) {
        this.listener = listener;
        this.ping = ping;
    }

    public Ping getPing() {
        return ping;
    }

    public Listener getListener() {
        return listener;
    }
}
