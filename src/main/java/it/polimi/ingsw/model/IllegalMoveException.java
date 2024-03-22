package it.polimi.ingsw.model;

public class IllegalMoveException extends RuntimeException {
    public IllegalMoveException() {
        super("Illegal move attempted.");
    }
}
