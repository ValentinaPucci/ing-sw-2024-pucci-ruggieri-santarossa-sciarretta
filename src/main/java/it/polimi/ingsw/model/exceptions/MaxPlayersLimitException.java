package it.polimi.ingsw.model.exceptions;

public class MaxPlayersLimitException extends RuntimeException {
    public MaxPlayersLimitException() {
        super("Max player limit reached! Cannot add more players!");
    }
}
