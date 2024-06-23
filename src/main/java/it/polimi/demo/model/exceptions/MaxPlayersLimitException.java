package it.polimi.demo.model.exceptions;

/**
 * This exception is used to indicate that the game is not ready to run.
 */
public class MaxPlayersLimitException extends RuntimeException {
    public MaxPlayersLimitException() {
        super("Max player limit reached! Cannot offer more players!");
    }
}
