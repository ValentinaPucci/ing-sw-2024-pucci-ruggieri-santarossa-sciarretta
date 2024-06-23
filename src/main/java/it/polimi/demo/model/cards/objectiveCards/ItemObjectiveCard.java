package it.polimi.demo.model.cards.objectiveCards;

import it.polimi.demo.model.board.PersonalBoard;
import it.polimi.demo.model.enumerations.Item;
import it.polimi.demo.model.enumerations.Orientation;

/**
 * The following is a subclass of ObjectiveCard and describes the Item Objective Cards.
 * That are objective cards that only have ITEMS as requirements to achieve the objective.
 *
 */
public class ItemObjectiveCard extends ObjectiveCard {
    /**
     * Number of featherss required to achieve the objective.
     */
    private int num_feathers;
    /**
     * Number of potions required to achieve the objective.
     */
    private int num_potions;
    /**
     * Number of parchments required to achieve the objective.
     */
    private int num_parchments;

    /**
     * Constructor of the class.
     * @param id the id of the card
     * @param orientation the orientation of the card
     * @param points the points of the card
     * @param num_feathers the number of feathers required to achieve the objective
     * @param num_potions the number of potions required to achieve the objective
     * @param num_parchments the number of parchments required to achieve the objective
     */
    public ItemObjectiveCard(int id, Orientation orientation, int points, int num_feathers, int num_potions, int num_parchments) {
        super(id, orientation, points);
        this.num_feathers = num_feathers;
        this.num_potions = num_potions;
        this.num_parchments = num_parchments;
    }

    /**
     * This method returns the number of items.
     * @param item the type of item
     * @return the number of items
     */
    public int getNumItem(Item item) {
        switch (item) {
            case FEATHER:
                return num_feathers;
            case POTION:
                return num_potions;
            case PARCHMENT:
                return num_parchments;
            default:
                return 0;
        }
    }


    /**
     * This method returns the type of item required to achieve the objective.
     * @return the type of item required to achieve the objective.
     */
    public Item getItemType() {
        if (num_feathers > 0)
            return Item.FEATHER;
        else if (num_potions > 0)
            return Item.POTION;
        else
            return Item.PARCHMENT;
    }

    /**
     * This method calculates the score of the player according to the personal board configuration.
     * @param personal_board the personal board of the player
     * @return the score of the player
     */
    @Override
    public int calculateScore(PersonalBoard personal_board) {

        if (num_feathers > 0 && num_potions > 0 && num_parchments > 0) {
            int min = Math.min(personal_board.getNum_feathers(),
                    Math.min(personal_board.getNum_potions(), personal_board.getNum_parchments()));
            return min * this.getPoints();
        }
        else {
            return (personal_board.getNumItem(this.getItemType()) / this.getNumItem(this.getItemType()))
                    * this.getPoints();
        }
    }

}
