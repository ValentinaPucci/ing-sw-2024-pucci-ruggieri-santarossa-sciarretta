package it.polimi.demo.model;

import it.polimi.demo.model.board.BoardCellCoordinate;
import it.polimi.demo.model.board.Corner;
import it.polimi.demo.model.board.PersonalBoard;
import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.cards.objectiveCards.DiagonalPatternObjectiveCard;
import it.polimi.demo.model.cards.objectiveCards.ItemObjectiveCard;
import it.polimi.demo.model.cards.objectiveCards.ResourceObjectiveCard;
import it.polimi.demo.model.enumerations.Color;
import it.polimi.demo.model.enumerations.Item;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.enumerations.Resource;
import it.polimi.demo.model.exceptions.IllegalMoveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonalBoardTest {
    private PersonalBoard personalBoard;

    @BeforeEach
    public void setup() {
        personalBoard = new PersonalBoard(1000);
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
        System.out.println(card1);
        System.out.println(card1.getCornerAtNE().resource);
        System.out.println(card1.getCornerAtNW().is_visible);
        System.out.println(card1.getCornerAtSW().item);
        System.out.println(card1.getCornerAtSE());


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
                filledCorner[i][j] = new Corner(new BoardCellCoordinate(i, j));
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

    @Test
    void testGoldCardPointsUpdate() { //CONTROLARE!!!!

        Corner[][] filledCorner1 = new Corner[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                filledCorner1[i][j] = new Corner(new BoardCellCoordinate(0, 0));
                filledCorner1[i][j].setCornerResource(Resource.MUSHROOM);
            }
        }

        Corner[][] filledCorner2 = new Corner[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                filledCorner2[i][j] = new Corner(new BoardCellCoordinate(0, 0));
                filledCorner2[i][j].setCornerResource(Resource.LEAF);
            }
        }

        personalBoard.addItem(Item.POTION);
        personalBoard.addItem(Item.POTION);

        ResourceCard card1 = new ResourceCard(1, Orientation.FRONT, Color.BLUE);
        GoldCard card2 = new GoldCard(2, Orientation.FRONT, Color.RED, 2, filledCorner1);
        GoldCard card3 = new GoldCard(3, Orientation.FRONT, Color.RED, 3, filledCorner2);

        card2.setGoldCard(0, 0, 0, 0, true, false, false, false);
        card3.setGoldCard(4, 0, 0, 0, false, true, false, false);


        int i = personalBoard.getDim1() / 2; // Specify the row index
        int j = personalBoard.getDim2() / 2; // Specify the column index

        personalBoard.bruteForcePlaceCardSE(card1, i, j);

        try {
            personalBoard.placeCardAtSE(card1, card2);
        } catch (IllegalMoveException e) {
            fail("Exception should not be thrown");
        }

        try {
            personalBoard.placeCardAtNE(card2, card3);
        } catch (IllegalMoveException e) {
            fail("Exception should not be thrown");
        }

        try {
            personalBoard.placeCardAtNE(card1, card3);
        } catch (IllegalMoveException e) {
            assertEquals("Illegal move attempted.", e.getMessage());
        }

        //assertEquals(8, personalBoard.getPoints()); //problem!!

    }

    @Test
    void testCalculateScoresObjectivePattern() {

        ResourceCard card1 = new ResourceCard(1, Orientation.FRONT, Color.RED);
        ResourceCard card2 = new ResourceCard(2, Orientation.FRONT, Color.RED);
        ResourceCard card3 = new ResourceCard(3, Orientation.FRONT, Color.RED);
        ResourceCard card4 = new ResourceCard(4, Orientation.FRONT, Color.RED);
        ResourceCard card5 = new ResourceCard(5, Orientation.FRONT, Color.GREEN);
        ResourceCard card6 = new ResourceCard(6, Orientation.FRONT, Color.GREEN);
        ResourceCard card7 = new ResourceCard(7, Orientation.FRONT, Color.GREEN);
        ResourceCard card8 = new ResourceCard(8, Orientation.FRONT, Color.GREEN);

        int i = personalBoard.getDim1() / 2; // Specify the row index
        int j = personalBoard.getDim2() / 2; // Specify the column index

        personalBoard.bruteForcePlaceCardSE(card1, i, j);

        personalBoard.placeCardAtSE(card1, card2);
        personalBoard.placeCardAtSE(card2, card3);
        personalBoard.placeCardAtSE(card3, card4);
        personalBoard.placeCardAtSW(card4, card5);
        personalBoard.placeCardAtSW(card5, card6);
        personalBoard.placeCardAtSW(card6, card7);
        personalBoard.placeCardAtSW(card7, card8);

        DiagonalPatternObjectiveCard diagonalPatternObjectiveCard1 = new DiagonalPatternObjectiveCard(1, Orientation.FRONT, 1);
        diagonalPatternObjectiveCard1.init_objDecreasingDiagonal(Color.GREEN);

        DiagonalPatternObjectiveCard diagonalPatternObjectiveCard2 = new DiagonalPatternObjectiveCard(1, Orientation.FRONT, 1);
        diagonalPatternObjectiveCard2.init_objIncreasingDiagonal(Color.RED);

        assertEquals(2, diagonalPatternObjectiveCard1.calculateScore(personalBoard) +
                diagonalPatternObjectiveCard2.calculateScore(personalBoard));

    }

    @Test
    void testCalculateResourceItemObjective() {

        Corner[][] filledCorner1 = new Corner[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                filledCorner1[i][j] = new Corner(new BoardCellCoordinate(0, 0));
                filledCorner1[i][j].setCornerResource(Resource.MUSHROOM);
            }
        }

        Corner[][] filledCorner2 = new Corner[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                filledCorner2[i][j] = new Corner(new BoardCellCoordinate(0, 0));
                filledCorner2[i][j].setCornerItem(Item.POTION);
            }
        }

        Corner[][] filledCorner3 = new Corner[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                filledCorner3[i][j] = new Corner(new BoardCellCoordinate(0, 0));
                filledCorner3[i][j].setCornerItem(Item.PARCHMENT);
            }
        }

        Corner[][] filledCorner4 = new Corner[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                filledCorner4[i][j] = new Corner(new BoardCellCoordinate(0, 0));
                filledCorner4[i][j].setCornerItem(Item.FEATHER);
            }
        }

        ResourceCard card1 = new ResourceCard(1, Orientation.FRONT, Color.RED);
        ResourceCard card2 = new ResourceCard(2, Orientation.FRONT, Color.RED, 1, filledCorner1);
        ResourceCard card3 = new ResourceCard(3, Orientation.FRONT, Color.RED, 1, filledCorner1);
        ResourceCard card4 = new ResourceCard(4, Orientation.FRONT, Color.RED, 1, filledCorner1);
        ResourceCard card5 = new ResourceCard(5, Orientation.FRONT, Color.GREEN, 1, filledCorner2);
        ResourceCard card6 = new ResourceCard(6, Orientation.FRONT, Color.GREEN, 1, filledCorner2);
        ResourceCard card7 = new ResourceCard(7, Orientation.FRONT, Color.GREEN, 1, filledCorner2);
        ResourceCard card8 = new ResourceCard(8, Orientation.FRONT, Color.GREEN, 1, filledCorner2);
        ResourceCard card9 = new ResourceCard(9, Orientation.FRONT, Color.GREEN, 1, filledCorner3);
        ResourceCard card10 = new ResourceCard(10, Orientation.FRONT, Color.GREEN, 1, filledCorner4);

        // After the placements, we have 9 mushrooms and 12 potions, 3 parchments and 3 feathers

        int i = personalBoard.getDim1() / 2; // Specify the row index
        int j = personalBoard.getDim2() / 2; // Specify the column index

        personalBoard.bruteForcePlaceCardSE(card1, i, j);

        personalBoard.placeCardAtSE(card1, card2);
        personalBoard.placeCardAtSE(card2, card3);
        personalBoard.placeCardAtSE(card3, card4);
        personalBoard.placeCardAtSW(card4, card5);
        personalBoard.placeCardAtSW(card5, card6);
        personalBoard.placeCardAtSW(card6, card7);
        personalBoard.placeCardAtSW(card7, card8);
        personalBoard.placeCardAtSE(card8, card9);
        personalBoard.placeCardAtSE(card9, card10);

        ResourceObjectiveCard resourceObjectiveCard = new ResourceObjectiveCard(1, Orientation.FRONT, 2,
                3, 0, 0, 0);

        ItemObjectiveCard itemObjectiveCard = new ItemObjectiveCard(1, Orientation.FRONT, 3,
                1, 1, 1);

        assertEquals(15, resourceObjectiveCard.calculateScore(personalBoard) +
                itemObjectiveCard.calculateScore(personalBoard));
    }


    // Add more tests for other methods
}