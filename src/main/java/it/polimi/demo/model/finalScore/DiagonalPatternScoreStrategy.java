package it.polimi.demo.model.finalScore;

import it.polimi.demo.model.board.PersonalBoard;
import it.polimi.demo.model.cards.objectiveCards.DiagonalPatternObjectiveCard;
import it.polimi.demo.model.cards.objectiveCards.ObjectiveCard;

import java.io.Serializable;

public class DiagonalPatternScoreStrategy implements ScoreStrategy, Serializable {

    /**
     * @param objectiveCard
     * @param l
     * @param m
     * @return
     */
    public boolean isSubMatrixDiagonalPattern(DiagonalPatternObjectiveCard objectiveCard,
                                              PersonalBoard personal_board, int l, int m) {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (l + i >= 250  && m + j >= 250 && l + i <= 251 && m + j <= 251) {
                    return false;
                }
                if (objectiveCard.aux_personal_board.board[i][j].is_full) {
                    if (personal_board.board[l + i][m + j].cell_of_a_found_pattern)
                        return false;
                    if (!personal_board.board[l + i][m + j].is_full)
                        return false;
                    else if (!personal_board.board[l + i][m + j].equals(objectiveCard.aux_personal_board.board[i][j]))
                        return false;
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (objectiveCard.aux_personal_board.board[i][j].is_full) {
                    personal_board.board[l + i][m + j].setCellAsPatternFound();
                }
            }
        }

        return true;
    }

    /**
     * @param objectiveCard
     * @return
     */
    public int counterOfRecognisedDiagonalPatterns(DiagonalPatternObjectiveCard objectiveCard,
                                                   PersonalBoard personal_board) {
        int count = 0;
        for (int i = 0; i < personal_board.getDim1() - 4; i++) {
            for (int j = 0; j < personal_board.getDim2() - 4; j++) {
                if (isSubMatrixDiagonalPattern(objectiveCard, personal_board, i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public int calculateScore(ObjectiveCard card, PersonalBoard personal_board) {
        return counterOfRecognisedDiagonalPatterns((DiagonalPatternObjectiveCard) card, personal_board)
                * (card.getPoints());
    }
}
