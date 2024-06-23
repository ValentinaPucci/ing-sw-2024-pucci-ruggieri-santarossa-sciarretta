package it.polimi.demo.model.cards.objectiveCards;

import it.polimi.demo.model.board.PersonalBoard;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.enumerations.Resource;

/**
 * The following is a subclass of ObjectiveCard and describes the Resource Objective Cards.
 * That are objective cards that only have RESOURCE as requirements to achieve the objective.
 *
 */
public class ResourceObjectiveCard extends ObjectiveCard {
    private int num_mushrooms;
    private int num_leaves;
    private int num_butterflies;
    private int num_wolves;

    /**
     * Constructor for the ResourceObjectiveCard class
     * @param id the id of the card
     * @param orientation the orientation of the card
     * @param points the points of the card
     * @param num_mushrooms the number of mushrooms required
     * @param num_leaves the number of leaves required
     * @param num_butterflies the number of butterflies required
     * @param num_wolves the number of wolves required
     */
    public ResourceObjectiveCard(int id, Orientation orientation, int points,
                                 int num_mushrooms, int num_leaves, int num_butterflies, int num_wolves) {
        super(id, orientation, points);
        this.num_mushrooms = num_mushrooms;
        this.num_leaves = num_leaves;
        this.num_butterflies = num_butterflies;
        this.num_wolves = num_wolves;
    }

    /**
     * generic getter for the number of resources
     * @param resource the resource to get the number of
     * @return the number of the resource
     */
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

    /**
     * generic getter for the resource type
     * @return the resource type
     */
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

    /**
     * This method calculates the score of the card
     * @param personal_board the personal board of the player
     * @return the score of the card
     */
    @Override
    public int calculateScore(PersonalBoard personal_board) {
        return (personal_board.getNumResource(this.getResourceType()) / this.getNumResource(this.getResourceType()))
                * this.getPoints();
    }

    @Override
    public String toString() {
        return "ResourceObjectiveCard{" +
                "num_mushrooms=" + num_mushrooms +
                ", num_leaves=" + num_leaves +
                ", num_butterflies=" + num_butterflies +
                ", num_wolves=" + num_wolves +
                '}';
    }

}
