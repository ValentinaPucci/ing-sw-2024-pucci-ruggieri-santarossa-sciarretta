package it.polimi.demo.model.exceptions;

/**
 * Exception thrown when trying to pop from an empty stack.
 */
public class EmptyStackException extends RuntimeException {
    public EmptyStackException() {
        super("Stack is empty");
    }

    public EmptyStackException(String message) {
        super(message);
    }
}
