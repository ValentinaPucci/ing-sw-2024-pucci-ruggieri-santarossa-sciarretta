package it.polimi.demo.model.exceptions;

/**
 * This class represents an exception that is thrown when an illegal move is attempted.
 */
public class IllegalMoveException extends RuntimeException {
    public IllegalMoveException() {
        super("Illegal move attempted.");
    }
}
