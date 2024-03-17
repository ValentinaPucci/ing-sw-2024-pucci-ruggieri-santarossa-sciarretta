package src.main.java.it.polimi.ingsw.model;

/**
 * The following class describe those objective cards that deal
 * with patter recognition of cards with respect to the board configuration:
 * There are 6 possible (geometric) patterns of three cards each. In order to distinguish
 * the cards in the patter to recognise, we only need to know the color of each. It is useful
 * to implements some methods that can achieve our goals in terms of submatrices encoding.
 */
public class PatternObjectiveCard extends ObjectiveCard {

    public Cell[][] submatrix;

    /**
     * @param id
     * @param orientation
     */
    public PatternObjectiveCard(int id, Orientation orientation) {
        super(id, orientation);
        this.submatrix = new Cell[5][5];
    }

    /**
     * This method is an initializer
     *
     * @requires
     *      color == RED or color == BLUE
     * @param color
     * @return
     */
    public void init_objIncreasingDiagonal(Color color, Cell[][] submatrix) {

    }


}
