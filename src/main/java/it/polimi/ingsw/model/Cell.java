package src.main.java.it.polimi.ingsw.model;

public class Cell {
    private Optional<Corner> corner;
    // 0 is empty, 1 is full
    private int flag;

    /**
     * Constructor
     */
    public Cell() {
        this.corner = Optional.empty();
        this.flag = 0;
    }

}
