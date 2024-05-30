package it.polimi.demo.model.finalScore;

import it.polimi.demo.model.board.PersonalBoard;
import it.polimi.demo.model.cards.objectiveCards.ObjectiveCard;

import java.io.Serializable;

public interface ScoreStrategy extends Serializable {
    int calculateScore(ObjectiveCard card, PersonalBoard personal_board);
}
