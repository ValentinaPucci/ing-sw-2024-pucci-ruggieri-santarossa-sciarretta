package it.polimi.demo.model.exceptions;

public class GameEndedException extends Exception {
    public GameEndedException() {
        super("The game has ended!");
    }
}
