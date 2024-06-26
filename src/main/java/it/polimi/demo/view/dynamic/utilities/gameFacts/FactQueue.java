package it.polimi.demo.view.dynamic.utilities.gameFacts;

import it.polimi.demo.model.ModelView;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class is a queue of facts that are used to update the view
 */
public class FactQueue implements Serializable {

    @Serial
    private static final long serialVersionUID = 8285017790211304290L;

    /**
     * The queue of facts. The key is the model and the value is the type of fact
     * that is associated with the model, so that the view can update the model accordingly.
     * The queue is a FIFO queue, thread-safe and blocking. We need an HashMap because we need to
     * eventually store null values as keys.
     */
    private final LinkedBlockingQueue<HashMap<ModelView, FactType>> queue_of_facts = new LinkedBlockingQueue<>();

    /**
     * Adds a fact to the queue.
     *
     * @param model    the model
     * @param factType the type of fact
     */
    public synchronized void offer(ModelView model, FactType factType) {
        HashMap<ModelView, FactType> factMap = new HashMap<>();
        factMap.put(model, factType);
        queue_of_facts.add(factMap);
    }

    /**
     * Polls the queue of facts.
     *
     * @return the fact
     */
    public synchronized HashMap<ModelView, FactType> poll() {
        return queue_of_facts.poll();
    }

    /**
     * Checks if the queue has facts to handle, excluding LOBBY_INFO type.
     *
     * @return true if the next fact is not LOBBY_INFO, false otherwise
     */
    public boolean isToHandle() {
        return queue_of_facts.stream()
                .map(HashMap::values)
                .flatMap(Collection::stream)
                .anyMatch(factType -> !(factType.equals(FactType.LOBBY_INFO) ||
                        factType.equals(FactType.GENERIC_ERROR) ||
                        factType.equals(FactType.ALREADY_USED_NICKNAME) ||
                        factType.equals(FactType.FULL_GAME)));
    }

    /**
     * Clears the queue of facts.
     */
    public void clear() {
        queue_of_facts.clear();
    }
}



