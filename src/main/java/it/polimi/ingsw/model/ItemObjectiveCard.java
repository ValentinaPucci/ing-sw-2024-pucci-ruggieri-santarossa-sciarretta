package it.polimi.ingsw.model;

public class ItemObjectiveCard extends ObjectiveCard {
    int num_feathers;
    int num_potions;
    int num_parchments;

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

    @Override
    public int calculateScore(PersonalBoard personal_board) {
        return personal_board.getNum_feathers() + personal_board.getNum_potions() + personal_board.getNum_parchments();
    }
}
