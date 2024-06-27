package it.polimi.demo.model.cards.objectiveCards;

import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.finalScore.DiagonalPatternScoreStrategy;
import it.polimi.demo.model.board.PersonalBoard;
import it.polimi.demo.model.finalScore.ScoreStrategy;
import it.polimi.demo.model.enumerations.Color;
import it.polimi.demo.model.enumerations.Orientation;

/**
 * The following class describe those objective cards that deal
 * with ** diagonal ** pattern recognition of cards with respect to the board configuration:
 * There are 2 possible (geometric) patterns of three cards each. In order to distinguish
 * the cards in the patter to recognise, we only need to know the color of each. It is useful
 * to implements some methods that can achieve our goals in terms of sub-matrices encoding.
 */
public class DiagonalPatternObjectiveCard extends ObjectiveCard {

    /**
     * This attributes let us use all the methods we
     * implemented in Personal board. Dim = 4 x 4;
     */
    public PersonalBoard aux_personal_board;
    public ScoreStrategy score_strategy;

    /**
     * @param id
     * @param orientation
     * @param points
     */
    public DiagonalPatternObjectiveCard(int id, Orientation orientation, int points) {
        super(id, orientation, points);
        this.aux_personal_board = new PersonalBoard(4);
        this.score_strategy = new DiagonalPatternScoreStrategy();
    }

    /**
     * This method is an initializer; in fact we initialize auxiliary
     * cards that are out of the deck. In general their id does not make
     * any sense. For the sake of simplicity, we assume that those cards
     * have id set to -1;
     *
     * Remark: Both increasing diagonal pattern and decreasing diagonal
     * pattern deal with cards of the SAME color.
     *
     *      color == RED || color == BLUE
     * @param color
     */
    public void init_objDecreasingDiagonal(Color color) {

        ResourceCard card1 = new ResourceCard(-1, Orientation.FRONT, color);
        ResourceCard card2 = new ResourceCard(-1, Orientation.FRONT, color);
        ResourceCard card3 = new ResourceCard(-1, Orientation.FRONT, color);

        aux_personal_board.bruteForcePlaceCardSE(card1, 0, 0);
        aux_personal_board.placeCardAtSE(card1, card2);
        aux_personal_board.placeCardAtSE(card2, card3);

    }

    /**
     * Symmetric method to the previous one
     *
     *      color == GREEN || color == PURPLE
     *
     * @param color
     */
    public void init_objIncreasingDiagonal(Color color) {
        ResourceCard card1 = new ResourceCard(-1, Orientation.FRONT, color);
        ResourceCard card2 = new ResourceCard(-1, Orientation.FRONT, color);
        ResourceCard card3 = new ResourceCard(-1, Orientation.FRONT, color);

        aux_personal_board.bruteForcePlaceCardSE(card1, 2, 0);
        aux_personal_board.placeCardAtNE(card1, card2);
        aux_personal_board.placeCardAtNE(card2, card3);
    }

    /**
     * Calculate the score of the player with respect to the objective card
     * @param personal_board the personal board of the player
     * @return the score of the player
     */
    @Override
    public int calculateScore(PersonalBoard personal_board) {
        return score_strategy.calculateScore(this, personal_board);
    }

    @Override
    public String toString() {
        return "ObjectiveCard{" +
                "id=" + super.id+
                ", orientation=" + orientation +

                // Add other attributes here...
                '}';
    }
}











