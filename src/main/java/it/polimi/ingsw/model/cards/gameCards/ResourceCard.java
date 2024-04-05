package it.polimi.ingsw.model.cards.gameCards;

import it.polimi.ingsw.interfaces.ResourceCardIC;
import it.polimi.ingsw.model.board.BoardCellCoordinate;
import it.polimi.ingsw.model.board.Corner;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.model.enumerations.Orientation;

public class ResourceCard extends Card implements ResourceCardIC {

    // TODO: you always have to consider the orientation of the card...
    public int points;
    public Color color;
    private Corner[][] corners;

    /**
     * Remark: we first initialize the points at 0 for every card
     * without distinguishing between front or back orientation
     * without loose of generality.
     *
     * @param id
     * @param orientation
     * @param color
     */
    public ResourceCard(int id, Orientation orientation, Color color) {
        super(id, orientation);
        this.points = 0;
        this.color = color;

        this.corners = new Corner[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                corners[i][j] = new Corner(new BoardCellCoordinate(i, j), this);
            }
        }
    }

    /**
     * Second constructor to initialize the cards and the respective corners. We do not need a setter method at the moment.
     * @param points
     * @param actual_corners
     */
    public ResourceCard(int id, Orientation orientation, Color color, int points, Corner[][] actual_corners) {
        super(id, orientation);
        this.points = points;
        this.color = color;

        this.corners = new Corner[2][2];
        for (int i = 0; i < 2; i++) {
            for(int j = 0; j < 2; j++) {
                this.corners[i][j] = actual_corners[i][j];
            }
        }
    }

    /**
     *
     * @return NE corner
     */
    public Corner getCornerAtNE () {
        return corners[0][1];
    }

    /**
     *
     * @return SE corner
     */
    public Corner getCornerAtSE () {
        return corners[1][1];
    }

    /**
     *
     * @return SO corner
     */
    public Corner getCornerAtSW () {
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
     * @requires
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

    // To print output.
    @Override
    public String toString() {
        return "ResourceCard{" +
            "id=" + super.id+
            ", orientation=" + orientation +
            ", color=" + color +
            // Add other attributes here...
            '}';
    }

}