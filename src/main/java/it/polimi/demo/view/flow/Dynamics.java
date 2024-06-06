package it.polimi.demo.view.flow;

import it.polimi.demo.observer.Listener;

import java.io.Serializable;

public abstract class Dynamics implements Listener, Serializable {

    public abstract void noConnectionError();

}