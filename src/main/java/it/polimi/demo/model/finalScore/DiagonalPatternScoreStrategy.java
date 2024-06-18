package it.polimi.demo.model.finalScore;

import it.polimi.demo.model.board.IdColor;
import it.polimi.demo.model.board.PersonalBoard;
import it.polimi.demo.model.cards.objectiveCards.DiagonalPatternObjectiveCard;
import it.polimi.demo.model.cards.objectiveCards.ObjectiveCard;
import it.polimi.demo.model.enumerations.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiagonalPatternScoreStrategy implements ScoreStrategy, Serializable {

    /**
     * @param objectiveCard
     * @param l
     * @param m
     * @return
     */
    public boolean isSubMatrixDiagonalPattern(DiagonalPatternObjectiveCard objectiveCard,
                                              PersonalBoard personal_board, int l, int m) {

        Color targetColor = null;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
//                if (l + i >= 250  && m + j >= 250 && l + i <= 251 && m + j <= 251) {
//                    // if it is only the starter card, it is not a pattern
//                    if (personal_board.board[l + i][m + j].level < 2)
//                        return false;
//                }
                if (objectiveCard.aux_personal_board.board[i][j].is_full) {
                    targetColor = objectiveCard.aux_personal_board.board[i][j].getCornerFromCell().reference_card.color;
//                    if (personal_board.board[l + i][m + j].cell_of_a_found_pattern)
//                        return false;
                    if (!personal_board.board[l + i][m + j].is_full)
                        return false;
                    else if (!personal_board.board[l + i][m + j].equals(objectiveCard.aux_personal_board.board[i][j]))
                        return false;
                }
            }
        }

        // it is a possible pattern!

//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 4; j++) {
//                if (objectiveCard.aux_personal_board.board[i][j].is_full) {
//                    personal_board.board[l + i][m + j].setCellAsPatternFound();
//                }
//            }
//        }

        // Here we populate the hypercube

        // HashMap to store the count of each IdColor ID
        Map<Integer, Integer> idColorCount = new HashMap<>();

        // Iterate over the 4x4 section of the board
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (objectiveCard.aux_personal_board.board[i][j].is_full) {
                    List<IdColor> idColors = personal_board.board[l + i][m + j].getIdColors();
                    for (IdColor q : idColors) {
                        if (q.color().equals(targetColor)) {
                            int id = q.id();
                            idColorCount.put(id, idColorCount.getOrDefault(id, 0) + 1);
                        }
                    }
                }
            }
        }

        // Check if there are exactly 3 different IDs each with exactly 4 occurrences
        int countOfIdsWithFourOccurrences = 0;
        for (int count : idColorCount.values()) {
            if (count == 4) {
                countOfIdsWithFourOccurrences++;
            }
        }

        System.out.println("countOfIdsWithFourOccurrences: " + countOfIdsWithFourOccurrences);

        if (countOfIdsWithFourOccurrences != 3) {
            return false;
        }
        else {
            for (Integer id : idColorCount.keySet()) {
                if (idColorCount.get(id) == 4) {
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            if (objectiveCard.aux_personal_board.board[i][j].is_full) {
                                if (personal_board.board[l + i][m + j].getIdColors().contains(new IdColor(id, targetColor)))
                                    personal_board.board[l + i][m + j].setIdColorAsFoundPattern(id, targetColor);
                            }
                        }
                    }
                }
            }
            return true;
        }
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
