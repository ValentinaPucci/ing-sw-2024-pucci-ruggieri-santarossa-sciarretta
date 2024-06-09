package it.polimi.demo.view.dynamic;

import it.polimi.demo.observer.Listener;

import java.io.Serializable;

public abstract class Dynamic implements Listener, Serializable {

    public abstract void noConnectionError();

}