package it.polimi.demo.model.enumerations;

import java.io.Serializable;

/**
 * This enum represents the game status
 */
public enum GameStatus implements Serializable {
    WAIT,
    FIRST_ROUND,
    RUNNING,
    // Real last_turn, nobody draw a card.
    LAST_ROUND,
    // First player that get 20 points.
    SECOND_LAST_ROUND,
    ENDED
}
