package src.main.java.it.polimi.ingsw.model;

/**
 * This class provide the needed mapping between the Coordinate-based
 * description of a given corner and its matrix-based (i.e. using indexes)
 * description.
 */
public class BoardCellCoordinate {
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

    public void setXY(Coordinate coord) {
        switch (coord) {
            case NE:
                this.setX(0);
                this.setY(1);
                break;
            case SE:
                this.setX(1);
                this.setY(1);
                break;
            case SW:
                this.setX(1);
                this.setY(0);
                break;
            case NW:
                this.setX(0);
                this.setY(0);
                break;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
