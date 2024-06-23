package it.polimi.demo.model.exceptions;

/**
 * This exception is used to indicate that a player is already connected to the game.
 */
public class PlayerAlreadyConnectedException extends RuntimeException {
    public PlayerAlreadyConnectedException() {
        super("Player is already playing! Cannot offer him again!");
    }
}
