package it.polimi.ingsw.model;

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
                if (!personal_board.board[l + i][m + j].equals(objectiveCard.aux_personal_board.board[i][j])
                        && objectiveCard.aux_personal_board.board[i][j].is_full) {
                    return false;
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
                * (card).getPoints();
    }
}
