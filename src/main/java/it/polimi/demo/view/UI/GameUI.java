package it.polimi.demo.view.UI;

import it.polimi.demo.listener.UIListener;
import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.view.GameView;

import java.util.ArrayList;
import java.util.List;

abstract public class GameUI {

    protected final List<UIListener> lst = new ArrayList<>();

    public abstract void update(GameView gameView);

    public abstract void nextTurn();

    public abstract void printModel();

    public abstract void gameEnded(GameView gameView);

    public synchronized void addListener(UIListener o) {
        if(!lst.contains(o)){
            lst.add(o);
        }
    }

    public synchronized void removeListener(UIListener o){
        lst.remove(o);
    }
}
