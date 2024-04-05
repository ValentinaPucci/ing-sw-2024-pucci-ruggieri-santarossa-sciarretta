package it.polimi.ingsw.model.finalScore;

import it.polimi.ingsw.model.board.PersonalBoard;
import it.polimi.ingsw.model.cards.objectiveCards.ObjectiveCard;

public interface ScoreStrategy {
    public int calculateScore(ObjectiveCard card, PersonalBoard personal_board);
}
