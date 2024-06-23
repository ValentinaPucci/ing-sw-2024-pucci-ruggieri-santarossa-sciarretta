package it.polimi.demo.model.exceptions;

/**
 * This exception is used to indicate that the game is not ready to run.
 */
public class GameEndedException extends Exception {
    public GameEndedException() {
        super("The game has ended!");
    }
}
