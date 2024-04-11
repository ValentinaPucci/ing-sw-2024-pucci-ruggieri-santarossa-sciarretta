package it.polimi.ingsw.model.enumerations;

import java.io.Serializable;

/**
 * This enum represents the game status
 */
public enum GameStatus implements Serializable {
    WAIT,
    RUNNING,
    LAST_TURN,
    ENDED
}
