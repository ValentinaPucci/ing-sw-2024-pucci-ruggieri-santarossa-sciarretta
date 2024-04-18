package it.polimi.demo.model.exceptions;

public class PlayerAlreadyConnectedException extends RuntimeException {
    public PlayerAlreadyConnectedException() {
        super("Player is already playing! Cannot add him again!");
    }
}
