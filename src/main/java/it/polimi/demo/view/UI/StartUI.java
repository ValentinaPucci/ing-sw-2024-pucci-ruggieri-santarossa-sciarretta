package it.polimi.demo.view.UI;

import it.polimi.demo.listener.UIListener;
import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.view.GameDetails;

import java.util.ArrayList;
import java.util.List;

abstract public class StartUI implements Runnable {

    protected final List<UIListener> lst = new ArrayList<>();

    public abstract void showGamesList(List<GameDetails> o);

    public abstract void showError(String err);

    public abstract void showStatus(GameStatus status);

    public abstract void showPlayersList(List<String> o);

    /**
     * This method closes the UI.
     */
    public abstract void close();

    public synchronized void addListener(UIListener o){
        if(!lst.contains(o)){
            lst.add(o);
        }
    }

    public synchronized void removeListener(UIListener o){
        lst.remove(o);
    }

    public abstract void joinGame();
}