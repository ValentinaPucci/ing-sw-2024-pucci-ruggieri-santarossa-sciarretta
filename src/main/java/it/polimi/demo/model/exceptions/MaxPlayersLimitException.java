package it.polimi.demo.model.exceptions;

public class MaxPlayersLimitException extends RuntimeException {
    public MaxPlayersLimitException() {
        super("Max player limit reached! Cannot offer more players!");
    }
}
