package it.polimi.demo.view;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.listener.ListenersHandler;
import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;
import java.util.List;

// todo: re-implement code (originality)
public abstract class UI implements Runnable {

    public abstract void update(GameModelImmutable gameView);

    public abstract void gameEnded(GameModelImmutable gameView);

    public abstract void showGamesList(List<GameDetails> o);

    public abstract void showError(String err);

    public abstract void showPlayersList(List<String> o);

    public abstract void close();

}
