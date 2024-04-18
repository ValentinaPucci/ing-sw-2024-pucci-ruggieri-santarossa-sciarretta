package it.polimi.demo.model.finalScore;

import it.polimi.demo.model.board.PersonalBoard;
import it.polimi.demo.model.cards.objectiveCards.LetterPatternObjectiveCard;
import it.polimi.demo.model.cards.objectiveCards.ObjectiveCard;

public class LetterPatternScoreStrategy implements ScoreStrategy {

    /**
     * @param objectiveCard
     * @param l
     * @param m
     * @return true iff we recognised a letter pattern
     */
    public boolean isSubMatrixLetterPattern(LetterPatternObjectiveCard objectiveCard,
                                            PersonalBoard personal_board, int l, int m) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
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

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
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
    public int counterOfRecognisedLetterPatterns(LetterPatternObjectiveCard objectiveCard,
                                                 PersonalBoard personal_board) {

        int count = 0;
        for (int i = 0; i <= personal_board.getDim1() - 5; i++) {
            for (int j = 0; j <= personal_board.getDim2() - 3; j++) {
                if (isSubMatrixLetterPattern(objectiveCard, personal_board, i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public int calculateScore(ObjectiveCard card, PersonalBoard personal_board) {
        return counterOfRecognisedLetterPatterns((LetterPatternObjectiveCard) card, personal_board)
                * (card.getPoints());
    }
}
