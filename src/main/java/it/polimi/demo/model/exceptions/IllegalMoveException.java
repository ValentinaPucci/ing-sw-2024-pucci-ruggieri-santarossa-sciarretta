package it.polimi.demo.model.exceptions;

public class IllegalMoveException extends RuntimeException {
    public IllegalMoveException() {
        super("Illegal move attempted.");
    }
}
