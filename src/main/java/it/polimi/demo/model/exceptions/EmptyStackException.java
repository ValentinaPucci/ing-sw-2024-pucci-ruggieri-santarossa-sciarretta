package it.polimi.demo.model.exceptions;

public class EmptyStackException extends RuntimeException {
    public EmptyStackException() {
        super("Stack is empty");
    }

    public EmptyStackException(String message) {
        super(message);
    }
}
