package it.polimi.demo.model.exceptions;
public class IndexOutOfBoundsException extends RuntimeException {
    public IndexOutOfBoundsException() {
        System.out.println("Index out of bounds");
    }

    public IndexOutOfBoundsException(String message) {
        System.out.println(message);
    }
}
