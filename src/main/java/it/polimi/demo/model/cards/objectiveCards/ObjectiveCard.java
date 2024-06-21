package it.polimi.demo.model.cards.objectiveCards;

import it.polimi.demo.model.board.PersonalBoard;
import it.polimi.demo.model.cards.Card;
import it.polimi.demo.model.enumerations.Orientation;

import java.io.Serializable;

/**
 * Abstract class for the Objective Cards.
 */
public abstract class ObjectiveCard extends Card implements Serializable {
    private int points;

    /**
     * Constructor for the ObjectiveCard class
     * @param id the id of the card
     * @param orientation the orientation of the card
     * @param points the points of the card
     */
    protected ObjectiveCard(int id, Orientation orientation, int points) {
        super(id, orientation);
        this.points = points;
    }

    /**
     * Getter for the points of the card
     * @return the points of the card
     */
    public int getPoints() {
        return points;
    }

    /**
     * Abstract method to calculate the score of the card
     * @param personal_board the personal board of the player
     * @return the score of the card
     */
    public abstract int calculateScore(PersonalBoard personal_board);
}