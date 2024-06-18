package it.polimi.demo.model.finalScore;

import it.polimi.demo.model.board.IdColor;
import it.polimi.demo.model.board.PersonalBoard;
import it.polimi.demo.model.cards.objectiveCards.LetterPatternObjectiveCard;
import it.polimi.demo.model.cards.objectiveCards.ObjectiveCard;
import it.polimi.demo.model.enumerations.Color;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LetterPatternScoreStrategy implements ScoreStrategy, Serializable {

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
//                if (l + i >= 250  && m + j >= 250 && l + i <= 251 && m + j <= 251) {
//                    return false;
//                }
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

        Color target_1 = null;
        Color target_2 = null;

        Map<Integer, Integer> idColorCount = new HashMap<>();

        // case 1: P / Q patterns
        if (objectiveCard.aux_personal_board.board[1][1].level == 2) {
            if (objectiveCard.aux_personal_board.board[0][2].is_full) {
                target_1 = objectiveCard.aux_personal_board.board[0][2].getCornerFromCell().reference_card.color;
                target_2 = objectiveCard.aux_personal_board.board[4][0].getCornerFromCell().reference_card.color;
            }
            else {
                target_1 = objectiveCard.aux_personal_board.board[0][0].getCornerFromCell().reference_card.color;
                target_2 = objectiveCard.aux_personal_board.board[4][2].getCornerFromCell().reference_card.color;
            }
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    if (objectiveCard.aux_personal_board.board[i][j].is_full) {
                        List<IdColor> idColors = personal_board.board[l + i][m + j].getIdColors();
                        for (IdColor q : idColors) {
                            if (q.color().equals(target_1)) {
                                int id = q.id();
                                idColorCount.put(id, idColorCount.getOrDefault(id, 0) + 1);
                            }
                        }
                    }
                }
            }
            for (int i = 1; i < 5; i++) {
                for (int j = 0; j < 3; j++) {
                    if (objectiveCard.aux_personal_board.board[i][j].is_full) {
                        List<IdColor> idColors = personal_board.board[l + i][m + j].getIdColors();
                        for (IdColor q : idColors) {
                            if (q.color().equals(target_2)) {
                                int id = q.id();
                                idColorCount.put(id, idColorCount.getOrDefault(id, 0) + 1);
                            }
                        }
                    }
                }
            }
        }
        // case 2: other patterns (J, L)
        else if (objectiveCard.aux_personal_board.board[3][1].level == 2) {
            if (objectiveCard.aux_personal_board.board[4][0].is_full) {
                target_1 = objectiveCard.aux_personal_board.board[4][0].getCornerFromCell().reference_card.color;
                target_2 = objectiveCard.aux_personal_board.board[0][2].getCornerFromCell().reference_card.color;
            }
            else {
                target_1 = objectiveCard.aux_personal_board.board[4][2].getCornerFromCell().reference_card.color;
                target_2 = objectiveCard.aux_personal_board.board[0][0].getCornerFromCell().reference_card.color;
            }
            for (int i = 3; i < 5; i++) {
                for (int j = 0; j < 3; j++) {
                    if (objectiveCard.aux_personal_board.board[i][j].is_full) {
                        List<IdColor> idColors = personal_board.board[l + i][m + j].getIdColors();
                        for (IdColor q : idColors) {
                            if (q.color().equals(target_1)) {
                                int id = q.id();
                                idColorCount.put(id, idColorCount.getOrDefault(id, 0) + 1);
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    if (objectiveCard.aux_personal_board.board[i][j].is_full) {
                        List<IdColor> idColors = personal_board.board[l + i][m + j].getIdColors();
                        for (IdColor q : idColors) {
                            if (q.color().equals(target_2)) {
                                int id = q.id();
                                idColorCount.put(id, idColorCount.getOrDefault(id, 0) + 1);
                            }
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
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 3; j++) {
                            if (objectiveCard.aux_personal_board.board[i][j].is_full) {
                                if (personal_board.board[l + i][m + j].getIdColors().contains(new IdColor(id, target_1)))
                                    personal_board.board[l + i][m + j].setIdColorAsFoundPattern(id, target_2);
                                else if (personal_board.board[l + i][m + j].getIdColors().contains(new IdColor(id, target_2)))
                                    personal_board.board[l + i][m + j].setIdColorAsFoundPattern(id, target_1);
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
    public int counterOfRecognisedLetterPatterns(LetterPatternObjectiveCard objectiveCard,
                                                 PersonalBoard personal_board) {

        int count = 0;
        for (int i = 0; i < personal_board.getDim1() - 5; i++) {
            for (int j = 0; j < personal_board.getDim2() - 3; j++) {
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
