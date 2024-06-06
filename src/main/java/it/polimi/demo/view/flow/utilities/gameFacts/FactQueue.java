package it.polimi.demo.view.flow.utilities.gameFacts;

import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Predicate;

// todo: checked
public class FactQueue implements Serializable {

    private Queue<ModelFact> modelFactQueue;
    private boolean playerJoined = false;

    public FactQueue() {
        modelFactQueue = new LinkedList<>();
    }

    public synchronized void add(GameModelImmutable gameModel, FactType factType) {

        modelFactQueue.add(new ModelFact(gameModel, factType));

        Predicate<GameModelImmutable> isGameRunningOrLastRound =
                gm -> gm != null && (gm.getStatus().equals(GameStatus.RUNNING) || gm.getStatus().equals(GameStatus.LAST_ROUND));

        if (factType.equals(FactType.PLAYER_JOINED) || isGameRunningOrLastRound.test(gameModel)) {
            playerJoined = true;
        }

        if (factType.equals(FactType.APP_MENU)) {
            playerJoined = false;
        }
    }

    public synchronized ModelFact pop() {
        return modelFactQueue.poll();
    }

    public synchronized int size() {
        return modelFactQueue.size();
    }

    public synchronized boolean isJoined() {
        return playerJoined;
    }
}


