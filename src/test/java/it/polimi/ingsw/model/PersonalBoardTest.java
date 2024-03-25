package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonalBoardTest {
    private PersonalBoard personalBoard;

    @BeforeEach
    public void setup() {
        personalBoard = new PersonalBoard(1001);
    }

    @Test
    public void testUpdateMushrooms() {
        personalBoard.updateMushrooms(5);
        assertEquals(5, personalBoard.getNum_mushrooms());
    }

    @Test
    public void testUpdateLeaves() {
        personalBoard.updateLeaves(3);
        assertEquals(3, personalBoard.getNum_leaves());
    }

    @Test
    void testBruteForcePlaceCardSE() {

        ResourceCard card = new ResourceCard(1, Orientation.FRONT, Color.BLUE); // You may need to provide appropriate arguments to the constructor
        int i = personalBoard.getDim1() / 2; // Specify the row index
        int j = personalBoard.getDim2() / 2; // Specify the column index

        personalBoard.bruteForcePlaceCardSE(card, i, j);

        for (int k = 0; k < 2; k++) {
            for (int h = 0; h < 2; h++) {
                assertTrue(personalBoard.board[i + k][j + h].is_full);
            }
        }
    }

    @Test
    void testPlaceCardAtSE() {

        ResourceCard card1 = new ResourceCard(1, Orientation.FRONT, Color.BLUE);
        ResourceCard card2 = new ResourceCard(2, Orientation.FRONT, Color.RED);
        int i = personalBoard.getDim1() / 2; // Specify the row index
        int j = personalBoard.getDim2() / 2; // Specify the column index

        personalBoard.bruteForcePlaceCardSE(card1, i, j);

        try {
            personalBoard.placeCardAtSE(card1, card2);
        } catch (IllegalMoveException e) {
            fail("Exception should not be thrown");
        }

        for (int k = 0; k < 2; k++) {
            for (int h = 0; h < 2; h++) {
                assertTrue(personalBoard.board[i + k][j + h].is_full);
            }
        }
    }

    @Test
    void testPlaceCardAtSO() {

            ResourceCard card1 = new ResourceCard(1, Orientation.FRONT, Color.BLUE);
            ResourceCard card2 = new ResourceCard(2, Orientation.FRONT, Color.RED);
            int i = personalBoard.getDim1() / 2; // Specify the row index
            int j = personalBoard.getDim2() / 2; // Specify the column index

            personalBoard.bruteForcePlaceCardSE(card1, i, j);

            try {
                personalBoard.placeCardAtSO(card1, card2);
            } catch (IllegalMoveException e) {
                fail("Exception should not be thrown");
            }

            for (int k = 0; k < 2; k++) {
                for (int h = 1; h >= 0; h--) {
                    assertTrue(personalBoard.board[i + k][j + h].is_full);
                }
            }
    }

    // Add more tests for other methods
}