package it.polimi.demo.view.flow;

import it.polimi.demo.listener.GameListener;

import java.io.Serializable;

public abstract class Flow implements GameListener, Serializable {

    /**
     * Shows no connection error
     */
    public abstract void noConnectionError();

}