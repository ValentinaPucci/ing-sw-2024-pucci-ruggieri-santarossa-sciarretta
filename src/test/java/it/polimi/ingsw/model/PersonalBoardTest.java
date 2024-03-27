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
    void testBruteForcePlaceCardSE() {

        ResourceCard card = new ResourceCard(1, Orientation.FRONT, Color.BLUE);
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
    void testPlaceCardAtNE() {

        ResourceCard card1 = new ResourceCard(1, Orientation.FRONT, Color.BLUE);
        ResourceCard card2 = new ResourceCard(2, Orientation.FRONT, Color.RED);
        int i = personalBoard.getDim1() / 2; // Specify the row index
        int j = personalBoard.getDim2() / 2; // Specify the column index

        personalBoard.bruteForcePlaceCardSE(card1, i, j);

        try {
            personalBoard.placeCardAtNE(card1, card2);
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
                personalBoard.placeCardAtSW(card1, card2);
            } catch (IllegalMoveException e) {
                fail("Exception should not be thrown");
            }

            for (int k = 0; k < 2; k++) {
                for (int h = 1; h >= 0; h--) {
                    assertTrue(personalBoard.board[i + k][j + h].is_full);
                }
            }
    }

    @Test
    void testPlaceCardOverlappingException_1() {

        ResourceCard card1 = new ResourceCard(1, Orientation.FRONT, Color.BLUE);
        ResourceCard card2 = new ResourceCard(2, Orientation.FRONT, Color.RED);
        int i = personalBoard.getDim1() / 2; // Specify the row index
        int j = personalBoard.getDim2() / 2; // Specify the column index

        personalBoard.bruteForcePlaceCardSE(card1, i, j);

        try {
            personalBoard.placeCardAtSW(card1, card2);
        } catch (IllegalMoveException e) {
            fail("Exception should not be thrown");
        }

        try {
            personalBoard.placeCardAtSW(card1, card2);
            fail("Exception should be thrown");
        } catch (IllegalMoveException e) {
            assertEquals("Illegal move attempted.", e.getMessage());
        }

    }

    @Test
    void testPlaceCardOverlappingException_2() {

        ResourceCard card1 = new ResourceCard(1, Orientation.FRONT, Color.RED);
        ResourceCard card2 = new ResourceCard(2, Orientation.FRONT, Color.RED);
        ResourceCard card3 = new ResourceCard(3, Orientation.FRONT, Color.RED);
        ResourceCard card4 = new ResourceCard(4, Orientation.FRONT, Color.RED);
        int i = personalBoard.getDim1() / 2; // Specify the row index
        int j = personalBoard.getDim2() / 2; // Specify the column index

        personalBoard.bruteForcePlaceCardSE(card1, i, j);

        try {
            personalBoard.placeCardAtSW(card1, card2);
        } catch (IllegalMoveException e) {
            fail("Exception should not be thrown");
        }

        try {
            personalBoard.placeCardAtNW(card2, card3);
        } catch (IllegalMoveException e) {
            fail("Exception should not be thrown");
        }

        try {
            personalBoard.placeCardAtSE(card3, card4);
            fail("Exception should be thrown");
        } catch (IllegalMoveException e) {
            assertEquals("Illegal move attempted.", e.getMessage());
        }

    }

    @Test
    void testCornerCoverage() {

        Corner[][] filledCorner = new Corner[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                filledCorner[i][j] = new Corner(new BoardCellCoordinate(0, 0));
                filledCorner[i][j].setCornerResource(Resource.MUSHROOM);
            }
        }

        ResourceCard card1 = new ResourceCard(1, Orientation.FRONT, Color.BLUE);
        ResourceCard card2 = new ResourceCard(2, Orientation.FRONT, Color.RED, 1, filledCorner);
        ResourceCard card3 = new ResourceCard(3, Orientation.FRONT, Color.RED, 1, filledCorner);
        ResourceCard card4 = new ResourceCard(4, Orientation.FRONT, Color.RED);
        ResourceCard card5 = new ResourceCard(5, Orientation.BACK, Color.RED);

        int i = personalBoard.getDim1() / 2; // Specify the row index
        int j = personalBoard.getDim2() / 2; // Specify the column index

        personalBoard.bruteForcePlaceCardSE(card1, i, j);

        try {
            personalBoard.placeCardAtSE(card1, card2);
        } catch (IllegalMoveException e) {
            fail("Exception should not be thrown");
        }

        try {
            personalBoard.placeCardAtNE(card1, card3);
        } catch (IllegalMoveException e) {
            fail("Exception should not be thrown");
        }

        try {
            personalBoard.placeCardAtSE(card3, card4);
        } catch (IllegalMoveException e) {
            fail("Exception should not be thrown");
        }

        try {
            personalBoard.placeCardAtNW(card1, card5);
        } catch (IllegalMoveException e) {
            fail("Exception should not be thrown");
        }

        assertEquals(7, personalBoard.getNum_mushrooms());
    }

    // Add more tests for other methods
}