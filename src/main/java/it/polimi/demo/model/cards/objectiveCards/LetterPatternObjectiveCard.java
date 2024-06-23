package it.polimi.demo.model.cards.objectiveCards;

import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.finalScore.LetterPatternScoreStrategy;
import it.polimi.demo.model.board.PersonalBoard;
import it.polimi.demo.model.finalScore.ScoreStrategy;
import it.polimi.demo.model.enumerations.Color;
import it.polimi.demo.model.enumerations.Orientation;

/**
 * The following class describe those objective cards that deal
 * with ** letter ** pattern recognition of cards with respect to the board configuration:
 * There are 4 possible (geometric) patterns of three cards each. In order to distinguish
 * the cards in the patter to recognise, we only need to know the color of each. It is useful
 * to implements some methods that can achieve our goals in terms of sub-matrices encoding.
 */
public class LetterPatternObjectiveCard extends it.polimi.demo.model.cards.objectiveCards.ObjectiveCard {

    /**
     * This attributes let us use all the methods we
     * implemented in Personal board. Dim = 5 x 3;
     */
    public PersonalBoard aux_personal_board;
    public ScoreStrategy score_strategy;

    /**
     * @param id
     * @param orientation
     */
    public LetterPatternObjectiveCard(int id, Orientation orientation, int points) {
        super(id, orientation, points);
        this.aux_personal_board = new PersonalBoard(5, 3);
        this.score_strategy = new LetterPatternScoreStrategy();
    }

    /**
     * This method is an initializer; in fact we initialize auxiliary
     * cards that are out of the deck. In general their id does not make
     * any sense. For the sake of simplicity, we assume that those cards
     * have id set to -1;
     *
     * Remark: color is not a parameter anymore.
     *
     * @requires
     *      color == RED || color == BLUE
     */
    public void init_obj_L() {

        ResourceCard card1 = new ResourceCard(-1, Orientation.FRONT, Color.RED);
        ResourceCard card2 = new ResourceCard(-1, Orientation.FRONT, Color.RED);
        ResourceCard card3 = new ResourceCard(-1, Orientation.FRONT, Color.GREEN);

        aux_personal_board.bruteForcePlaceCardSE(card1, 0, 0);
        aux_personal_board.bruteForcePlaceCardSE(card3, 3, 1);
        aux_personal_board.placeCardAtNW(card3, card2);
    }

    /**
     * This method is an initializer; in fact we initialize auxiliary
     * cards that are out of the deck. In general their id does not make
     * any sense. For the sake of simplicity, we assume that those cards
     * have id set to -1;
     *
     * Remark: color is not a parameter anymore.
     *
     * @requires
     *      color == GREEN || color == PURPLE
     */
    public void init_obj_J() {

        ResourceCard card1 = new ResourceCard(-1, Orientation.FRONT, Color.GREEN);
        ResourceCard card2 = new ResourceCard(-1, Orientation.FRONT, Color.GREEN);
        ResourceCard card3 = new ResourceCard(-1, Orientation.FRONT, Color.PURPLE);

        aux_personal_board.bruteForcePlaceCardSE(card1, 0, 1);
        aux_personal_board.bruteForcePlaceCardSE(card3, 3, 0);
        aux_personal_board.placeCardAtNE(card3, card2);
    }

    /**
     * This method is an initializer; in fact we initialize auxiliary
     * cards that are out of the deck. In general their id does not make
     * any sense. For the sake of simplicity, we assume that those cards
     * have id set to -1;
     *
     * Remark: color is not a parameter anymore.
     *
     * @requires
     *      color == RED || color == BLUE
     */
    public void init_obj_P() {

        ResourceCard card1 = new ResourceCard(-1, Orientation.FRONT, Color.RED);
        ResourceCard card2 = new ResourceCard(-1, Orientation.FRONT, Color.BLUE);
        ResourceCard card3 = new ResourceCard(-1, Orientation.FRONT, Color.BLUE);

        aux_personal_board.bruteForcePlaceCardSE(card1, 0, 1);
        aux_personal_board.bruteForcePlaceCardSE(card3, 3, 0);
        aux_personal_board.placeCardAtSW(card1, card2);
    }

    /**
     * This method is an initializer; in fact we initialize auxiliary
     * cards that are out of the deck. In general their id does not make
     * any sense. For the sake of simplicity, we assume that those cards
     * have id set to -1;
     *
     * Remark: color is not a parameter anymore.
     *
     * @requires
     *      color == BLUE || color == PURPLE
     */
    public void init_obj_Q() {

        ResourceCard card1 = new ResourceCard(-1, Orientation.FRONT, Color.BLUE);
        ResourceCard card2 = new ResourceCard(-1, Orientation.FRONT, Color.PURPLE);
        ResourceCard card3 = new ResourceCard(-1, Orientation.FRONT, Color.PURPLE);

        aux_personal_board.bruteForcePlaceCardSE(card1, 0, 0);
        aux_personal_board.bruteForcePlaceCardSE(card3, 3, 1);
        aux_personal_board.placeCardAtSE(card1, card2);
    }

    /**
     * calculateScore is a method that is used to calculate the score of the player
     * @param personal_board the personal board of the player
     * @return the score of the player
     */
    @Override
    public int calculateScore(PersonalBoard personal_board) {
        return score_strategy.calculateScore(this, personal_board);
    }

}
