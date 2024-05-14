package it.polimi.demo.model.board;

import java.io.Serializable;

/**
 * This class provide the needed mapping between the Coordinate-based
 * description of a given corner and its matrix-based (i.e. using indexes)
 * description.
 */
public class BoardCellCoordinate implements Serializable {

    /**
     * these are the REAL indexes of the matrix that represents the board
     */
    private int x;
    private int y;

    public BoardCellCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setXY(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
