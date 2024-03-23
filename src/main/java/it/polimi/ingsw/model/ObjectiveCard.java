package it.polimi.ingsw.model;

public abstract class ObjectiveCard extends Card {
    private int points;

    /**
     * @param id
     * @param orientation
     */
    public ObjectiveCard(int id, Orientation orientation, int points) {
        super(id, orientation);
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public abstract int calculateScore(PersonalBoard personal_board);
}
