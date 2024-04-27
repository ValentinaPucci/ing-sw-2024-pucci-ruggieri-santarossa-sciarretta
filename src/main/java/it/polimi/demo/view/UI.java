package it.polimi.demo.view;

import java.util.ArrayList;
import java.util.List;

public abstract class UI implements Runnable{
    //protected final List<StartUIListener> lst = new ArrayList<>();

    public abstract void update(GameView gameView);

    public abstract void gameEnded(GameView gameView);

    public abstract void showGamesList(List<GameDetails> o);

    public abstract void showError(String err);

    public abstract void showPlayersList(List<String> o);

    public abstract void close();

//    public synchronized void addListener(StartUIListener o){
//        if(!lst.contains(o)){
//            lst.add(o);
//        }
//    }

//    public synchronized void removeListener(StartUIListener o){
//        lst.remove(o);
//    }


}
