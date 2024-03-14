package src.main.java.it.polimi.ingsw.model;
import  java.util.Optional;

public class Cell {
    private Optional<Corner> corner;
    // false is empty, true is full
    private boolean is_full;

    /**
     *
     * @param corner
     */
    public Cell(Corner corner) {
        this.corner = Optional.ofNullable(corner);
        this.is_full = false;
    }

    /**
     *
     * @param actual_corner
     */
    public void setCorner(Optional<Corner> actual_corner) {
        corner = actual_corner;
        is_full = true;
    }

}
