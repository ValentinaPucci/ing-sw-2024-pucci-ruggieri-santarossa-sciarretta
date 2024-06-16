package it.polimi.demo.model.board;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class provide the needed mapping between the Coordinate-based
 * description of a given corner and its matrix-based (i.e. using indexes)
 * description.
 */
public class BoardCellCoordinate implements Serializable {

    @Serial
    private static final long serialVersionUID = -8934012821084644108L;

    /**
     * these are the REAL indexes of the matrix that represents the board
     */
    private int x;
    private int y;

    /**
     * Constructor for the class BoardCellCoordinate.
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public BoardCellCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * This method is used to set the x coordinate.
     * @param x the x coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * This method is used to set the y coordinate.
     * @param y the y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * This method is used to set the x and y coordinates.
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void setXY(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    /**
     * This method is used to get the x coordinate.
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * This method is used to get the y coordinate.
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }
}
