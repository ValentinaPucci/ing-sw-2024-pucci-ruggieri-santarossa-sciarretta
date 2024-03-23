package it.polimi.ingsw.model;

public class ObjectiveCard extends Card {

    private ScoreStrategy score_strategy;
    private int points;

    /**
     * @param id
     * @param orientation
     */
    public ObjectiveCard(int id, Orientation orientation, ScoreStrategy score_strategy, int points) {
        super(id, orientation);
        this.score_strategy = score_strategy;
    }

    public int getPoints() {
        return points;
    }

    public int calculateScore(PersonalBoard personal_board) {
        return score_strategy.calculateScore(this, personal_board);
    }
}
