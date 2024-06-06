package it.polimi.demo.view.flow.utilities.gameFacts;


import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.model.ModelView;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Queue;

public class EventList implements Serializable {

    private Queue<ModelFact> list;
    private boolean joined = false;

    /**
     * Init
     */
    public EventList() {
        list = new ArrayDeque<>();
    }

    /**
     * Adds a new event to the list
     * @param model
     * @param type
     */
    public synchronized void add(ModelView model, FactType type) {

        list.add(new ModelFact(model, type));

        if (type.equals(FactType.PLAYER_JOINED) ||
                (model != null && (model.getStatus().equals(GameStatus.RUNNING)
                        || model.getStatus().equals(GameStatus.LAST_ROUND))))
            joined = true;

        if (type.equals(FactType.APP_MENU))
            joined = false;
    }

    /**
     *
     * @return an element from the queue(FIFO)
     */
    public synchronized ModelFact pop() {
        return list.poll();
    }

    /**
     *
     * @return the list's size
     */
    public synchronized int size() {
        return list.size();
    }

    /**
     *
     * @return true if the player has joined the game, false if not
     */
    public synchronized boolean isJoined() {
        return joined;
    }
}
