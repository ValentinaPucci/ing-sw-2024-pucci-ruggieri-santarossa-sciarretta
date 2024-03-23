package it.polimi.ingsw.model;

public interface ScoreStrategy {
    public int calculateScore(ObjectiveCard card, PersonalBoard personal_board);
}
