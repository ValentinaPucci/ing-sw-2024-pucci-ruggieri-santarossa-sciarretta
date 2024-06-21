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
     * @param id
     * @param orientation
     */
    public ItemObjectiveCard(int id, Orientation orientation, int points, int num_feathers, int num_potions, int num_parchments) {
        super(id, orientation, points);
        this.num_feathers = num_feathers;
        this.num_potions = num_potions;
        this.num_parchments = num_parchments;
    }

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


    //TODO: è SBAGLIATO --> BISOGNA CONTROLLARE I SUOI UTILIZZI
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
     * @return
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
