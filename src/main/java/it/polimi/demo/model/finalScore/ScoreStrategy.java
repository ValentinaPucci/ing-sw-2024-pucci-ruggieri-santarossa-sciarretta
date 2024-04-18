package it.polimi.demo.model.finalScore;

import it.polimi.demo.model.board.PersonalBoard;
import it.polimi.demo.model.cards.objectiveCards.ObjectiveCard;

public interface ScoreStrategy {
    public int calculateScore(ObjectiveCard card, PersonalBoard personal_board);
}
