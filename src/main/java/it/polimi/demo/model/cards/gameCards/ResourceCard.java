package it.polimi.demo.model.cards.gameCards;

import it.polimi.demo.model.cards.Card;
import it.polimi.demo.model.enumerations.Coordinate;
import it.polimi.demo.model.board.BoardCellCoordinate;
import it.polimi.demo.model.board.Corner;
import it.polimi.demo.model.enumerations.Color;
import it.polimi.demo.model.enumerations.Orientation;

/**
 * This class represents the ResourceCard. It extends the Card class. It is also the super class of the Gold Cards.
 */

public class ResourceCard extends Card {

    /**
     * points of the card
     */
    public int points;
    private Corner[][] corners;
    /**
     * color of the card
     */
    public Color color;

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
            for (int j = 0; j < 2; j++) {
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
    public Corner getCornerAtNW () { return corners[0][0]; }

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

    /**
     * getter for the back of the card
     * @return the back of the card
     */
    public ResourceCard getBack() {
        Corner[][] corners_back = new Corner[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                corners_back[i][j] = new Corner();
                corners_back[i][j].setEmpty();
                corners_back[i][j].is_visible = true;

            }
        }
        corners_back[0][0].setCoordinate(Coordinate.NW);
        corners_back[0][1].setCoordinate(Coordinate.NE);
        corners_back[1][1].setCoordinate(Coordinate.SE);
        corners_back[1][0].setCoordinate(Coordinate.SW);
        // We set orientation as Orientation.BACK in order to identify it easily.
        ResourceCard card = new ResourceCard(super.id, Orientation.BACK, this.color, 0, corners_back);
        corners_back[0][0].setReference_card(card);
        corners_back[0][1].setReference_card(card);
        corners_back[1][1].setReference_card(card);
        corners_back[1][0].setReference_card(card);
        return card;
    }

    /**
     * getter for the color of the card
     * @return the color of the card
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * getter for the points of the card
     * @return the points of the card
     */
    public int getPoints() {
        return this.points;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ResourceCard)) {
            return false;
        }
        ResourceCard card = (ResourceCard) obj;
        return super.id == card.id;
    }

}