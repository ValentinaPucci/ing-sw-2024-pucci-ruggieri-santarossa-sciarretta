package it.polimi.demo.model.exceptions;
public class IndexOutOfBoundsException extends RuntimeException {
    public IndexOutOfBoundsException() {
        System.out.println("Index out of bounds");
    }
}
