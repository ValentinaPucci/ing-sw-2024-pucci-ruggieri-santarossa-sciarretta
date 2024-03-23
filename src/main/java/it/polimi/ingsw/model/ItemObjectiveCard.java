package it.polimi.ingsw.model;

public class ItemObjectiveCard extends ObjectiveCard {

    /**
     * @param id
     * @param orientation
     */
    public ItemObjectiveCard(int id, Orientation orientation, int points) {
        super(id, orientation, points);
    }

    @Override
    public int calculateScore(PersonalBoard personal_board) {
        return personal_board.getNum_mushrooms() + personal_board.getNum_leaves();
    }
}
