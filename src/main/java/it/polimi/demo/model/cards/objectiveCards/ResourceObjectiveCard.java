package it.polimi.demo.model.cards.objectiveCards;

import it.polimi.demo.model.board.PersonalBoard;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.enumerations.Resource;

public class ResourceObjectiveCard extends ObjectiveCard {
    private int num_mushrooms;
    private int num_leaves;
    private int num_butterflies;
    private int num_wolves;

    /**
     * @param id
     * @param orientation
     */
    public ResourceObjectiveCard(int id, Orientation orientation, int points,
                                 int num_mushrooms, int num_leaves, int num_butterflies, int num_wolves) {
        super(id, orientation, points);
        this.num_mushrooms = num_mushrooms;
        this.num_leaves = num_leaves;
        this.num_butterflies = num_butterflies;
        this.num_wolves = num_wolves;
    }

    public int getNumResource(Resource resource) {
        switch (resource) {
            case MUSHROOM:
                return num_mushrooms;
            case LEAF:
                return num_leaves;
            case BUTTERFLY:
                return num_butterflies;
            case WOLF:
                return num_wolves;
            default:
                return 0;
        }
    }

    public Resource getResourceType() {
        if (num_mushrooms > 0)
            return Resource.MUSHROOM;
        else if (num_leaves > 0)
            return Resource.LEAF;
        else if (num_butterflies > 0)
            return Resource.BUTTERFLY;
        else
            return Resource.WOLF;
    }

    @Override
    public int calculateScore(PersonalBoard personal_board) {
        return (personal_board.getNumResource(this.getResourceType()) / this.getNumResource(this.getResourceType()))
                * this.getPoints();
    }

}
