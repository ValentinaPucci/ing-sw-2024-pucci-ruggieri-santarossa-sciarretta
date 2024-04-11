package it.polimi.ingsw.model.cards.objectiveCards;

import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.model.enumerations.Item;
import it.polimi.ingsw.model.enumerations.Orientation;

public class ItemObjectiveCard extends ObjectiveCard {
    private int num_feathers;
    private int num_potions;
    private int num_parchments;

    /**
     * @param id;
     * @param orientation;
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

    public Item getItemType() {
        if (num_feathers > 0)
            return Item.FEATHER;
        else if (num_potions > 0)
            return Item.POTION;
        else
            return Item.PARCHMENT;
    }

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
