package it.polimi.demo.model.cards.gameCards;

import it.polimi.demo.model.cards.Card;
import it.polimi.demo.model.enumerations.Color;
import it.polimi.demo.model.enumerations.Coordinate;
import it.polimi.demo.model.board.BoardCellCoordinate;
import it.polimi.demo.model.board.Corner;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.enumerations.Resource;

/**
 * This class represents the starter card of the game.
 */

public class StarterCard extends Card {

    public Resource front_resource1;
    public Resource front_resource2;
    public Resource front_resource3;
    public Corner[][] corners;
    public Orientation orientation;
    public Color color;

    /**
     *
     * @param id
     * @param orientation
     */
    public StarterCard(int id, Orientation orientation) {
        super(id, orientation);
        this.front_resource1 = null;
        this.front_resource2 = null;
        this.front_resource3 = null;
        this.color = Color.NONE;

        this.corners = new Corner[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                corners[i][j] = new Corner(new BoardCellCoordinate(i, j));
            }
        }
    }

    /**
     * Set the front of the starter card
     * @param resource1
     * @param resource2
     */
    public void setStarterCardFront(Resource resource1, Resource resource2, Resource resource3, Corner[][] actual_corners){
        this.front_resource1 = resource1;
        this.front_resource2 = resource2;
        this.front_resource3 = resource3;
        this.orientation = Orientation.FRONT;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                this.corners[i][j] = actual_corners[i][j];
            }
        }
    }

    /**
     * Set the back of the starter card
     * @param actual_corners
     */
    public void setStarterCardBack(Corner[][] actual_corners){
        this.orientation = Orientation.BACK;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                this.corners[i][j] = actual_corners[i][j];
            }
        }
    }

    /**
     *
     * @return NE corner
     */
    public Corner getCornerAtNE() {
        return corners[0][1];
    }

    /**
     *
     * @return SE corner
     */
    public Corner getCornerAtSE() {
        return corners[1][1];
    }

    /**
     *
     * @return SO corner
     */
    public Corner getCornerAtSW() {
        return corners[1][0];
    }

    /**
     *
     * @return NO corner
     */
    public Corner getCornerAtNW () {
        return corners[0][0];
    }

    /**
     *      0 <= i < 2 && 0 <= j < 2;
     * @param i
     * @param j
     * @return the corner indexed by the couple (i, j)
     */
    public Corner getCornerAt(int i, int j) {
        if (i == 0 && j == 0)
            return this.getCornerAtNW();
        else if (i == 0 && j == 1)
            return this.getCornerAtNE();
        else if (i == 1 && j == 0)
            return this.getCornerAtSW();
        else
            return this.getCornerAtSE();
    }

    //TODO: use the default values in the appropriate class instead of explicit indexes.
    public Coordinate getCoordinateAt(int i, int j) {
        if (i == 25 && j == 25)
            return Coordinate.NW;
        else if (i == 25 && j == 26)
            return Coordinate.NE;
        else if (i == 26 && j == 25)
            return Coordinate.SW;
        else if (i == 26 && j == 26)
            return Coordinate.SE;
        else
            throw new IndexOutOfBoundsException("Invalid index");
    }

    /**
     * get the orientation of the card
     * @return the orientation of the card
     */
    public Orientation getOrientation() {
        return orientation;
    }

    @Override
    public String toString() {
        return "StarterCard{" +
                "id=" + id +
                ", orientation=" + orientation +
                ", front_resource1=" + front_resource1 +
                ", front_resource2=" + front_resource2 +
                ", front_resource3=" + front_resource3 +
                ", cornerNW=" + getCornerAtNW().toString() +
                ", cornerNE=" + getCornerAtNE().toString() +
                ", cornerSW=" + getCornerAtSW().toString() +
                ", cornerSE=" + getCornerAtSE().toString() +
                '}';
    }

    @Override
    public int getId(){
        return super.getId();
    }

}


