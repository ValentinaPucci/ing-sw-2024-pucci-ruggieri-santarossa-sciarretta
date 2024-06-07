package it.polimi.demo.model.exceptions;

public class ConnectionSuccessfulException extends RuntimeException {
    // This exception is used to indicate that a connection was successfully established.
    public ConnectionSuccessfulException() {
        super("Connection successfully established.");
    }
}
