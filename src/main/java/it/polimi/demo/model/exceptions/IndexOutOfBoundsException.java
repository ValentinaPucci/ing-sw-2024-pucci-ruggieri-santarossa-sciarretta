package it.polimi.demo.model.exceptions;
/**
 * This class represents an exception that is thrown when an index is out of bounds.
 */
public class IndexOutOfBoundsException extends RuntimeException {
    public IndexOutOfBoundsException() {
        System.out.println("Index out of bounds");
    }
}
