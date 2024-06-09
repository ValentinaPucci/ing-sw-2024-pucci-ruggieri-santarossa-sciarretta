package it.polimi.demo.view.dynamic.utilities.gameFacts;

import it.polimi.demo.model.ModelView;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class is a queue of facts that are used to update the view
 */
public class FactQueue implements Serializable {

    @Serial
    private static final long serialVersionUID = 8285017790211304290L;

    /**
     * The queue of facts. Motivation for the use of an HashMap: the key is the model and the value is the type of fact
     * that is associated with the model, so that the view can update the model accordingly. The queue is a FIFO queue.
     * The queue is thread safe. The queue is a blocking queue, so that the view can wait for the next fact to be
     * available. Why an HashMap and not a Map? This is because we need to put null keys whenever it is needed, see run
     * method of GameDynamic class.
     */
    private final Queue<HashMap<ModelView, FactType>> queue_of_facts = new LinkedBlockingQueue<>();

    /**
     * Adds a fact to the queue.
     *
     * @param model the model
     * @param factType  the type of fact
     */
    public synchronized void offer(ModelView model, FactType factType) {
        queue_of_facts.add(new HashMap<>() {{
            put(model, factType);
        }});
    }

    /**
     * Polls the queue of facts.
     * @return the fact
     */
    public synchronized HashMap<ModelView, FactType> poll() {
        return queue_of_facts.poll();
    }

    /**
     * Peeks the queue of facts. If we expect a player is inside the game, then
     * we will return true, otherwise false.
     * @return true if the player is inside the game, false otherwise
     */
    public boolean isToHandle() {
        if (!queue_of_facts.isEmpty()) {
            HashMap<ModelView, FactType> map = queue_of_facts.peek();
            FactType factType = map.values().iterator().next();
            return !factType.equals(FactType.LOBBY_INFO);
        }
        else
            return false;
    }
}


