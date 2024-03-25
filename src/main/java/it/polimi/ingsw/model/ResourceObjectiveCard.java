package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Orientation;

public class ResourceObjectiveCard extends ObjectiveCard {
    int num_mushrooms;
    int num_leaves;
    int num_butterflies;
    int num_wolves;

    /**
     * @param id
     * @param orientation
     */
    public ResourceObjectiveCard(int id, Orientation orientation, int points, int num_mushrooms, int num_leaves, int num_butterflies, int num_wolves) {
        super(id, orientation, points);
        this.num_mushrooms = num_mushrooms;
        this.num_leaves = num_leaves;
        this.num_butterflies = num_butterflies;
        this.num_wolves = num_wolves;
    }

    @Override
    public int calculateScore(PersonalBoard personal_board) {
        return personal_board.getNum_mushrooms() + personal_board.getNum_leaves() + personal_board.getNum_wolves() + personal_board.getNum_butterflies();
    }

}
