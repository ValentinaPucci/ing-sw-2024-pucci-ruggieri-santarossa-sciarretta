package it.polimi.ingsw.model.exceptions;

public class MaxPlayerLimitException extends RuntimeException {
    public MaxPlayerLimitException() {
        super("Max player limit reached! Cannot add more players!");
    }
}
