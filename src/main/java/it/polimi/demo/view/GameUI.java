package it.polimi.demo.view;

import it.polimi.demo.listener.UIListener;

import java.util.ArrayList;
import java.util.List;

abstract public class GameUI implements Runnable {
    protected final List<UIListener> lst = new ArrayList<>();

    public abstract void update(GameView gameView);

    public abstract void gameEnded(GameView gameView);

    public synchronized void addListener(UIListener o){
        if(!lst.contains(o)){
            lst.add(o);
        }
    }

    public synchronized void removeListener(UIListener o){
        lst.remove(o);
    }
}
