package it.polimi.ingsw.model.exceptions;

public class EmptyStackException extends RuntimeException {
    public EmptyStackException() {
        super("Stack is empty");
    }

    public EmptyStackException(String message) {
        super(message);
    }
}
