package it.polimi.demo.model;

import it.polimi.demo.model.board.BoardCellCoordinate;
import it.polimi.demo.model.board.Corner;
import it.polimi.demo.model.board.PersonalBoard;
import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.cards.gameCards.StarterCard;
import it.polimi.demo.model.cards.objectiveCards.*;
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
        personalBoard = new PersonalBoard(500);
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
    void testPlaceCardAtNE2() {
        StarterCard card0 = new StarterCard(1, Orientation.FRONT);


        //ResourceCard card1 = new ResourceCard(1, Orientation.FRONT, Color.BLUE);
        GoldCard card2 = new GoldCard(2, Orientation.FRONT, Color.RED);
        int i = personalBoard.getDim1() / 2; // Specify the row index
        int j = personalBoard.getDim2() / 2; // Specify the column index

        personalBoard.placeStarterCard(card0);

        try {
            personalBoard.placeCardAtNE(card0, card2);
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
    void placeCardAtNW2(){
        StarterCard card0 = new StarterCard(1, Orientation.FRONT);
        GoldCard card2 = new GoldCard(2, Orientation.FRONT, Color.RED);
        int i = personalBoard.getDim1() / 2; // Specify the row index
        int j = personalBoard.getDim2() / 2; // Specify the column index

        personalBoard.placeStarterCard(card0);

        try {
            personalBoard.placeCardAtNW(card0, card2);
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
        ResourceCard card2 = new ResourceCard(2, Orientation.FRONT, Color.RED);
        ResourceCard card3 = new ResourceCard(3, Orientation.FRONT, Color.RED);
        ResourceCard card4 = new ResourceCard(4, Orientation.FRONT, Color.RED);
        ResourceCard card5 = new ResourceCard(5, Orientation.BACK, Color.RED);

        card1.getCornerAtNE().getResource();
        card5.getCornerAtNE().getItem();

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
    }

    @Test
    void testGenericPositioning() {

        Corner[][] filledCorner = new Corner[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                filledCorner[i][j] = new Corner(new BoardCellCoordinate(i, j));
                filledCorner[i][j].setCornerResource(Resource.LEAF);
            }
        }

        ResourceCard card1 = new ResourceCard(1, Orientation.FRONT, Color.BLUE);
        ResourceCard card2 = new ResourceCard(2, Orientation.FRONT, Color.RED);
        ResourceCard card3 = new ResourceCard(3, Orientation.FRONT, Color.RED);
        ResourceCard card4 = new ResourceCard(4, Orientation.FRONT, Color.RED);
        ResourceCard card5 = new ResourceCard(5, Orientation.BACK, Color.RED);
        ResourceCard card6 = new ResourceCard(6, Orientation.BACK, Color.RED);
        ResourceCard card7 = new ResourceCard(7, Orientation.BACK, Color.RED);
        ResourceCard card8 = new ResourceCard(8, Orientation.BACK, Color.RED);
        GoldCard card9 = new GoldCard(9, Orientation.FRONT, Color.RED);
        GoldCard card10 = new GoldCard(10, Orientation.FRONT, Color.RED);
        GoldCard card11 = new GoldCard(11, Orientation.FRONT, Color.RED);
        GoldCard card12 = new GoldCard(12, Orientation.FRONT, Color.RED);
        GoldCard card13 = new GoldCard(13, Orientation.BACK, Color.RED);
        GoldCard card14 = new GoldCard(14, Orientation.BACK, Color.RED);
        GoldCard card15 = new GoldCard(15, Orientation.BACK, Color.RED);
        GoldCard card16 = new GoldCard(16, Orientation.BACK, Color.RED);

        card9.setGoldCard(0, 0, 0, 0, false, false, false, false);
        card10.setGoldCard(0, 0, 0, 0, false, false, false, false);
        card11.setGoldCard(0, 0, 0, 0, false, false, false, false);
        card12.setGoldCard(0, 0, 0, 0, false, false, false, false);
        card13.setGoldCard(0, 0, 0, 0, false, false, false, false);
        card14.setGoldCard(0, 0, 0, 0, false, false, false, false);
        card15.setGoldCard(0, 0, 0, 0, false, false, false, false);
        card16.setGoldCard(0, 0, 0, 0, false, false, false, false);

        int i = personalBoard.getDim1() / 2; // Specify the row index
        int j = personalBoard.getDim2() / 2; // Specify the column index

        //test
        personalBoard.getNum_feathers();
        personalBoard.getNum_mushrooms();
        personalBoard.getNum_wolves();
        personalBoard.getNum_butterflies();
        personalBoard.getNum_parchments();
        personalBoard.getNum_potions();
        personalBoard.getNum_leaves();
        personalBoard.getPoints();


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
            personalBoard.placeCardAtNW(card1, card4);
        } catch (IllegalMoveException e) {
            fail("Exception should not be thrown");
        }

        try {
            personalBoard.placeCardAtSW(card1, card5);
        } catch (IllegalMoveException e) {
            fail("Exception should not be thrown");
        }

        try {
            personalBoard.placeCardAtSE(card2, card6);
        } catch (IllegalMoveException e) {
            fail("Exception should not be thrown");
        }

        try {
            personalBoard.placeCardAtNE(card3, card7);
        } catch (IllegalMoveException e) {
            fail("Exception should not be thrown");
        }

        try {
            personalBoard.placeCardAtNW(card4, card8);
        } catch (IllegalMoveException e) {
            fail("Exception should not be thrown");
        }

        try {
            personalBoard.placeCardAtSW(card5, card9);
        } catch (IllegalMoveException e) {
            fail("Exception should not be thrown");
        }

        try {
            personalBoard.placeCardAtSE(card5, card10);
        } catch (IllegalMoveException e) {
            fail("Exception should not be thrown");
        }

        try {
            personalBoard.placeCardAtNE(card2, card11);
        } catch (IllegalMoveException e) {
            fail("Exception should not be thrown");
        }

        try {
            personalBoard.placeCardAtNW(card3, card12);
        } catch (IllegalMoveException e) {
            fail("Exception should not be thrown");
        }

        try {
            personalBoard.placeCardAtSW(card4, card13);
        } catch (IllegalMoveException e) {
            fail("Exception should not be thrown");
        }

        try {
            personalBoard.placeCardAtNE(card6, card14);
        } catch (IllegalMoveException e) {
            fail("Exception should not be thrown");
        }

        try {
            personalBoard.placeCardAtNW(card7, card15);
        } catch (IllegalMoveException e) {
            fail("Exception should not be thrown");
        }

        try {
            personalBoard.placeCardAtSW(card8, card16);
        } catch (IllegalMoveException e) {
            fail("Exception should not be thrown");
        }

    }

    @Test
    void testGoldCardPointsUpdate() {

        Corner[][] filledCorner1 = new Corner[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                filledCorner1[i][j] = new Corner(new BoardCellCoordinate(i, j));
                filledCorner1[i][j].setCornerResource(Resource.MUSHROOM);
            }
        }

        Corner[][] filledCorner2 = new Corner[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                filledCorner2[i][j] = new Corner(new BoardCellCoordinate(i, j));
                filledCorner2[i][j].setCornerResource(Resource.LEAF);
            }
        }

        personalBoard.addItem(Item.POTION);
        personalBoard.addItem(Item.POTION);

        ResourceCard card1 = new ResourceCard(1, Orientation.FRONT, Color.BLUE);
        GoldCard card2 = new GoldCard(2, Orientation.FRONT, Color.RED, 2, filledCorner1);
        filledCorner1[0][0].setReference_card(card2);
        filledCorner1[0][1].setReference_card(card2);
        filledCorner1[1][0].setReference_card(card2);
        filledCorner1[1][1].setReference_card(card2);

        GoldCard card3 = new GoldCard(3, Orientation.FRONT, Color.RED, 3, filledCorner2);
        filledCorner2[0][0].setReference_card(card3);
        filledCorner2[0][1].setReference_card(card3);
        filledCorner2[1][0].setReference_card(card3);
        filledCorner2[1][1].setReference_card(card3);

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

        assertEquals(8, personalBoard.getPoints()); //problem!!

    }

    @Test
    void testCalculateScoresObjectivePattern() {

        ResourceCard card1 = new ResourceCard(1, Orientation.FRONT, Color.GREEN);
        ResourceCard card2 = new ResourceCard(2, Orientation.FRONT, Color.GREEN);
        ResourceCard card3 = new ResourceCard(3, Orientation.FRONT, Color.GREEN);
        ResourceCard card4 = new ResourceCard(4, Orientation.FRONT, Color.GREEN);
        ResourceCard card5 = new ResourceCard(5, Orientation.FRONT, Color.RED);
        ResourceCard card6 = new ResourceCard(6, Orientation.FRONT, Color.RED);
        ResourceCard card7 = new ResourceCard(7, Orientation.FRONT, Color.RED);
        ResourceCard card8 = new ResourceCard(8, Orientation.FRONT, Color.RED);

        personalBoard.bruteForcePlaceCardSE(card1, 30, 30);
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

        personalBoard.getNumItem(Item.POTION);
        personalBoard.getNumItem(Item.PARCHMENT);
        personalBoard.getNumItem(Item.FEATHER);

        personalBoard.removeItem(Item.POTION);
        personalBoard.removeItem(Item.PARCHMENT);
        personalBoard.removeItem(Item.FEATHER);

    }

//    @Test
//    void testCalculateResourceItemObjective() {
//
//        // todo: you need to update filled corner references card
//
//        Corner[][] filledCorner1 = new Corner[2][2];
//        for (int i = 0; i < 2; i++) {
//            for (int j = 0; j < 2; j++) {
//                filledCorner1[i][j] = new Corner(new BoardCellCoordinate(i, j));
//                filledCorner1[i][j].setCornerResource(Resource.MUSHROOM);
//            }
//        }
//
//        System.out.println(filledCorner1[1][1].resource);
//
//        Corner[][] filledCorner2 = new Corner[2][2];
//        for (int i = 0; i < 2; i++) {
//            for (int j = 0; j < 2; j++) {
//                filledCorner2[i][j] = new Corner(new BoardCellCoordinate(i, j));
//                filledCorner2[i][j].setCornerItem(Item.POTION);
//            }
//        }
//
//        Corner[][] filledCorner3 = new Corner[2][2];
//        for (int i = 0; i < 2; i++) {
//            for (int j = 0; j < 2; j++) {
//                filledCorner3[i][j] = new Corner(new BoardCellCoordinate(i, j));
//                filledCorner3[i][j].setCornerItem(Item.PARCHMENT);
//            }
//        }
//
//        Corner[][] filledCorner4 = new Corner[2][2];
//        for (int i = 0; i < 2; i++) {
//            for (int j = 0; j < 2; j++) {
//                filledCorner4[i][j] = new Corner(new BoardCellCoordinate(i, j));
//                filledCorner4[i][j].setCornerItem(Item.FEATHER);
//            }
//        }
//
//        ResourceCard card1 = new ResourceCard(1, Orientation.FRONT, Color.RED);
//        ResourceCard card2 = new ResourceCard(2, Orientation.FRONT, Color.RED, 1, filledCorner1);
//        ResourceCard card3 = new ResourceCard(3, Orientation.FRONT, Color.RED, 1, filledCorner1);
//        ResourceCard card4 = new ResourceCard(4, Orientation.FRONT, Color.RED, 1, filledCorner1);
//        ResourceCard card5 = new ResourceCard(5, Orientation.FRONT, Color.GREEN, 1, filledCorner2);
//        ResourceCard card6 = new ResourceCard(6, Orientation.FRONT, Color.GREEN, 1, filledCorner2);
//        ResourceCard card7 = new ResourceCard(7, Orientation.FRONT, Color.GREEN, 1, filledCorner2);
//        ResourceCard card8 = new ResourceCard(8, Orientation.FRONT, Color.GREEN, 1, filledCorner2);
//        ResourceCard card9 = new ResourceCard(9, Orientation.FRONT, Color.GREEN, 1, filledCorner3);
//        ResourceCard card10 = new ResourceCard(10, Orientation.FRONT, Color.GREEN, 1, filledCorner4);
//
//        // After the placements, we have 9 mushrooms and 12 potions, 3 parchments and 3 feathers
//
//        int i = personalBoard.getDim1() / 2; // Specify the row index
//        int j = personalBoard.getDim2() / 2; // Specify the column index
//
//        personalBoard.bruteForcePlaceCardSE(card1, i, j);
//
//
//
//        personalBoard.placeCardAtSE(card1, card2);
//        System.out.println(personalBoard.getNum_mushrooms());
//
//        personalBoard.placeCardAtSE(card2, card3);
//        personalBoard.placeCardAtSE(card3, card4);
//        personalBoard.placeCardAtSW(card4, card5);
//        personalBoard.placeCardAtSW(card5, card6);
//        personalBoard.placeCardAtSW(card6, card7);
//        personalBoard.placeCardAtSW(card7, card8);
//        personalBoard.placeCardAtSE(card8, card9);
//        personalBoard.placeCardAtSE(card9, card10);
//
//        ResourceObjectiveCard resourceObjectiveCard = new ResourceObjectiveCard(1, Orientation.FRONT, 2,
//                3, 0, 0, 0);
//
//        ItemObjectiveCard itemObjectiveCard = new ItemObjectiveCard(1, Orientation.FRONT, 3,
//                1, 1, 1);
//
//        assertEquals(15, resourceObjectiveCard.calculateScore(personalBoard) +
//                itemObjectiveCard.calculateScore(personalBoard));
//    }

    // Add more tests for other methods

    @Test
    void testCalculateScore() {

        ResourceCard card1 = new ResourceCard(1, Orientation.FRONT, Color.GREEN);
        ResourceCard card2 = new ResourceCard(2, Orientation.FRONT, Color.GREEN);
        ResourceCard card3 = new ResourceCard(3, Orientation.FRONT, Color.GREEN);
        ResourceCard card4 = new ResourceCard(4, Orientation.FRONT, Color.RED);
        ResourceCard card5 = new ResourceCard(5, Orientation.FRONT, Color.RED);

        Corner[][] filledCorner1 = new Corner[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                filledCorner1[i][j] = new Corner(new BoardCellCoordinate(i, j));
                filledCorner1[i][j].setCornerResource(Resource.MUSHROOM);
            }
        }

        StarterCard starterCard = new StarterCard(0, Orientation.FRONT);
        starterCard.setStarterCardFront(Resource.LEAF, Resource.LEAF, Resource.LEAF, filledCorner1);

        // todo: complete it

        //personalBoard.placeStarterCard(starterCard);
        personalBoard.placeStarterCard(starterCard);
        personalBoard.placeCardAtSW(starterCard, card1);
        //personalBoard.placeCardAtSW(card5, card1);
        personalBoard.placeCardAtSE(card1, card2);
        personalBoard.placeCardAtNW(card1, card3);

        DiagonalPatternObjectiveCard diagonalPatternObjectiveCard1 = new DiagonalPatternObjectiveCard(1, Orientation.FRONT, 2);
        diagonalPatternObjectiveCard1.init_objDecreasingDiagonal(Color.GREEN);

        assertEquals(2, diagonalPatternObjectiveCard1.calculateScore(personalBoard));

    }

    @Test
    void patternCrisis() {

        ResourceCard card1 = new ResourceCard(1, Orientation.FRONT, Color.BLUE);
        ResourceCard card2 = new ResourceCard(2, Orientation.FRONT, Color.BLUE);
        ResourceCard card3 = new ResourceCard(3, Orientation.FRONT, Color.RED);
        ResourceCard card4 = new ResourceCard(4, Orientation.FRONT, Color.BLUE);
        ResourceCard card5 = new ResourceCard(5, Orientation.FRONT, Color.BLUE);
        ResourceCard card6 = new ResourceCard(6, Orientation.FRONT, Color.BLUE);

        Corner[][] filledCorner1 = new Corner[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                filledCorner1[i][j] = new Corner(new BoardCellCoordinate(i, j));
                filledCorner1[i][j].setCornerResource(Resource.MUSHROOM);
            }
        }

        StarterCard starterCard = new StarterCard(0, Orientation.FRONT);
        starterCard.setStarterCardFront(Resource.LEAF, Resource.LEAF, Resource.LEAF, filledCorner1);

        personalBoard.placeStarterCard(starterCard);

        personalBoard.placeCardAtSE(starterCard, card1);
        personalBoard.placeCardAtNE(card1, card2);
        personalBoard.placeCardAtSW(card1, card3);
        personalBoard.placeCardAtNW(card3, card4);
        personalBoard.placeCardAtSW(card3, card5);
        personalBoard.placeCardAtSE(card3, card6);

        DiagonalPatternObjectiveCard diagonalPatternObjectiveCard1 = new DiagonalPatternObjectiveCard(1, Orientation.FRONT, 2);
        diagonalPatternObjectiveCard1.init_objIncreasingDiagonal(Color.BLUE);

        assertEquals(0, diagonalPatternObjectiveCard1.calculateScore(personalBoard));
    }

    @Test
    void letterPattern() {

        ResourceCard card1 = new ResourceCard(1, Orientation.FRONT, Color.PURPLE);
        ResourceCard card2 = new ResourceCard(2, Orientation.FRONT, Color.GREEN);
        ResourceCard card3 = new ResourceCard(3, Orientation.FRONT, Color.GREEN);

        Corner[][] filledCorner1 = new Corner[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                filledCorner1[i][j] = new Corner(new BoardCellCoordinate(i, j));
                filledCorner1[i][j].setCornerResource(Resource.MUSHROOM);
            }
        }

        StarterCard starterCard = new StarterCard(0, Orientation.FRONT);
        starterCard.setStarterCardFront(Resource.LEAF, Resource.LEAF, Resource.LEAF, filledCorner1);

        personalBoard.placeStarterCard(starterCard);

        personalBoard.placeCardAtSE(starterCard, card2);
        personalBoard.placeCardAtNE(starterCard, card3);
        personalBoard.placeCardAtSW(card2, card1);

        ResourceCard card4 = new ResourceCard(4, Orientation.FRONT, Color.PURPLE);

        personalBoard.placeCardAtSE(card3, card4);

        LetterPatternObjectiveCard letterPatternObjectiveCard = new LetterPatternObjectiveCard(1, Orientation.FRONT, 2);
        letterPatternObjectiveCard.init_obj_J();

        assertEquals(2, letterPatternObjectiveCard.calculateScore(personalBoard));

    }

    @Test
    void backPatterns() {

        ResourceCard card1 = new ResourceCard(1, Orientation.FRONT, Color.PURPLE);
        ResourceCard card2 = new ResourceCard(2, Orientation.FRONT, Color.GREEN);
        ResourceCard card3 = new ResourceCard(3, Orientation.FRONT, Color.GREEN);

        ResourceCard card4 = card1.getBack();
        ResourceCard card5 = card2.getBack();
        ResourceCard card6 = card3.getBack();

        Corner[][] filledCorner1 = new Corner[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                filledCorner1[i][j] = new Corner(new BoardCellCoordinate(i, j));
                filledCorner1[i][j].setCornerResource(Resource.MUSHROOM);
            }
        }

        StarterCard starterCard = new StarterCard(0, Orientation.FRONT);
        starterCard.setStarterCardFront(Resource.LEAF, Resource.LEAF, Resource.LEAF, filledCorner1);

        personalBoard.placeStarterCard(starterCard);

        personalBoard.placeCardAtSE(starterCard, card5);
        personalBoard.placeCardAtNE(starterCard, card6);
        personalBoard.placeCardAtSW(card5, card4);

        ResourceCard card7 = new ResourceCard(4, Orientation.FRONT, Color.PURPLE);

        personalBoard.placeCardAtSE(card6, card7);

        LetterPatternObjectiveCard letterPatternObjectiveCard = new LetterPatternObjectiveCard(1, Orientation.FRONT, 2);
        letterPatternObjectiveCard.init_obj_J();

        assertEquals(2, letterPatternObjectiveCard.calculateScore(personalBoard));

    }

    @Test
    void doubleDiagonalPattern() {

        ResourceCard card1 = new ResourceCard(1, Orientation.FRONT, Color.RED);
        ResourceCard card2 = new ResourceCard(2, Orientation.FRONT, Color.RED);
        ResourceCard card3 = new ResourceCard(3, Orientation.FRONT, Color.RED);
        ResourceCard card4 = new ResourceCard(4, Orientation.FRONT, Color.RED);
        ResourceCard card5 = new ResourceCard(5, Orientation.FRONT, Color.RED);
        ResourceCard card6 = new ResourceCard(6, Orientation.FRONT, Color.RED);

        Corner[][] filledCorner1 = new Corner[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                filledCorner1[i][j] = new Corner(new BoardCellCoordinate(i, j));
                filledCorner1[i][j].setCornerResource(Resource.MUSHROOM);
            }
        }

        StarterCard starterCard = new StarterCard(0, Orientation.FRONT);
        starterCard.setStarterCardFront(Resource.LEAF, Resource.LEAF, Resource.LEAF, filledCorner1);

        personalBoard.placeStarterCard(starterCard);

        personalBoard.placeCardAtSE(starterCard, card1);
        personalBoard.placeCardAtNE(card1, card2);
        personalBoard.placeCardAtNE(card2, card3);
        personalBoard.placeCardAtNE(card3, card4);
        personalBoard.placeCardAtNE(card4, card5);
        personalBoard.placeCardAtNE(card5, card6);

        DiagonalPatternObjectiveCard diagonalPatternObjectiveCard1 = new DiagonalPatternObjectiveCard(1, Orientation.FRONT, 2);
        diagonalPatternObjectiveCard1.init_objIncreasingDiagonal(Color.RED);

        assertEquals(4, diagonalPatternObjectiveCard1.calculateScore(personalBoard));

    }

    @Test
    void doubleLetterPattern() {

        ResourceCard card1 = new ResourceCard(1, Orientation.FRONT, Color.BLUE);
        ResourceCard card2 = new ResourceCard(2, Orientation.FRONT, Color.BLUE);
        ResourceCard card3 = new ResourceCard(3, Orientation.FRONT, Color.RED);
        ResourceCard card4 = new ResourceCard(4, Orientation.FRONT, Color.BLUE);
        ResourceCard card5 = new ResourceCard(5, Orientation.FRONT, Color.BLUE);
        ResourceCard card6 = new ResourceCard(6, Orientation.FRONT, Color.RED);
        //ResourceCard card7 = new ResourceCard(7, Orientation.FRONT, Color.RED);

        Corner[][] filledCorner1 = new Corner[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                filledCorner1[i][j] = new Corner(new BoardCellCoordinate(i, j));
                filledCorner1[i][j].setCornerResource(Resource.MUSHROOM);
            }
        }

        StarterCard starterCard = new StarterCard(0, Orientation.FRONT);
        starterCard.setStarterCardFront(Resource.LEAF, Resource.LEAF, Resource.LEAF, filledCorner1);

        personalBoard.placeStarterCard(starterCard);

        personalBoard.placeCardAtSW(starterCard, card1);
        personalBoard.placeCardAtNW(starterCard, card2);
        personalBoard.placeCardAtNE(card2, card3);

        personalBoard.placeCardAtSE(starterCard, card4);
        personalBoard.placeCardAtNE(starterCard, card5);
        personalBoard.placeCardAtNE(card5, card6);

        LetterPatternObjectiveCard letterPatternObjectiveCard1 = new LetterPatternObjectiveCard(1, Orientation.FRONT, 2);
        letterPatternObjectiveCard1.init_obj_P();

        assertEquals(4, letterPatternObjectiveCard1.calculateScore(personalBoard));
    }

    @Test
    void diagonalPatternsWithBackStarter() {

        ResourceCard card0 = new ResourceCard(0, Orientation.FRONT, Color.RED);
        ResourceCard card1 = new ResourceCard(1, Orientation.FRONT, Color.GREEN);
        ResourceCard card2 = new ResourceCard(2, Orientation.FRONT, Color.RED);
        ResourceCard card3 = new ResourceCard(3, Orientation.FRONT, Color.RED);
        ResourceCard card4 = new ResourceCard(4, Orientation.FRONT, Color.RED);
        ResourceCard card5 = new ResourceCard(5, Orientation.FRONT, Color.RED);
        ResourceCard card6 = new ResourceCard(6, Orientation.FRONT, Color.RED);
        ResourceCard card7 = new ResourceCard(7, Orientation.FRONT, Color.GREEN);
        ResourceCard card8 = new ResourceCard(8, Orientation.FRONT, Color.GREEN);
        ResourceCard card9 = new ResourceCard(9, Orientation.FRONT, Color.GREEN);
        ResourceCard card10 = new ResourceCard(10, Orientation.FRONT, Color.RED);
        ResourceCard card11 = new ResourceCard(11, Orientation.FRONT, Color.RED);
        ResourceCard card12 = new ResourceCard(12, Orientation.FRONT, Color.RED);
        ResourceCard card13 = new ResourceCard(13, Orientation.FRONT, Color.BLUE);
        ResourceCard card14 = new ResourceCard(14, Orientation.FRONT, Color.PURPLE);
        ResourceCard card15 = new ResourceCard(15, Orientation.FRONT, Color.PURPLE);
        ResourceCard card16 = new ResourceCard(16, Orientation.FRONT, Color.PURPLE);

        Corner[][] filledCorner1 = new Corner[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                filledCorner1[i][j] = new Corner(new BoardCellCoordinate(i, j));
                filledCorner1[i][j].setCornerResource(Resource.MUSHROOM);
            }
        }

        StarterCard starterCard = new StarterCard(-1, Orientation.FRONT);
        starterCard.setStarterCardBack(filledCorner1);

        personalBoard.placeStarterCard(starterCard);
        personalBoard.placeCardAtNE(starterCard, card7);
        personalBoard.placeCardAtSE(card7, card8);
        personalBoard.placeCardAtSE(card8, card1);
        personalBoard.placeCardAtNE(card1, card2);
        personalBoard.placeCardAtNE(card2, card3);
        personalBoard.placeCardAtNE(card3, card4);
        personalBoard.placeCardAtNE(card4, card5);
        personalBoard.placeCardAtNE(card5, card6);
        personalBoard.placeCardAtNE(card6, card0);
        personalBoard.placeCardAtNW(starterCard, card9);
        personalBoard.placeCardAtNW(card9, card10);
        personalBoard.placeCardAtNE(card10, card11);
        personalBoard.placeCardAtNW(card11, card12);
        personalBoard.placeCardAtSE(card0, card13);
        personalBoard.placeCardAtSE(card13, card14);
        personalBoard.placeCardAtSW(card14, card15);
        personalBoard.placeCardAtSE(card15, card16);

        DiagonalPatternObjectiveCard diagonalPatternObjectiveCard1 = new DiagonalPatternObjectiveCard(1, Orientation.FRONT, 2);
        diagonalPatternObjectiveCard1.init_objIncreasingDiagonal(Color.RED);

        DiagonalPatternObjectiveCard diagonalPatternObjectiveCard2 = new DiagonalPatternObjectiveCard(1, Orientation.FRONT, 2);
        diagonalPatternObjectiveCard2.init_objDecreasingDiagonal(Color.GREEN);

        LetterPatternObjectiveCard letterPatternObjectiveCard1 = new LetterPatternObjectiveCard(1, Orientation.FRONT, 2);
        letterPatternObjectiveCard1.init_obj_L();

        LetterPatternObjectiveCard letterPatternObjectiveCard2 = new LetterPatternObjectiveCard(1, Orientation.FRONT, 2);
        letterPatternObjectiveCard2.init_obj_Q();

        assertEquals(10, diagonalPatternObjectiveCard1.calculateScore(personalBoard) +
                diagonalPatternObjectiveCard2.calculateScore(personalBoard) +
                letterPatternObjectiveCard1.calculateScore(personalBoard) +
                letterPatternObjectiveCard2.calculateScore(personalBoard));

    }

    @Test
    public void testGetNumResource() {
        personalBoard.addResource(Resource.LEAF);
        personalBoard.addResource(Resource.MUSHROOM);
        personalBoard.addResource(Resource.BUTTERFLY);
        personalBoard.addResource(Resource.WOLF);

        assertEquals(1, personalBoard.getNumResource(Resource.LEAF));
        assertEquals(1, personalBoard.getNumResource(Resource.MUSHROOM));
        assertEquals(1, personalBoard.getNumResource(Resource.BUTTERFLY));
        assertEquals(1, personalBoard.getNumResource(Resource.WOLF));
    }

    @Test
    void ObjectiveItemTest(){
        ItemObjectiveCard card = new ItemObjectiveCard(1, Orientation.FRONT, 1, 1, 1, 1);
        card.getItemType();
        card.getNumItem(Item.POTION);
        card.getNumItem(Item.PARCHMENT);
        card.getNumItem(Item.FEATHER);


    }

    @Test
    void ResourceObjectiveCardTest(){
        ResourceObjectiveCard card = new ResourceObjectiveCard(1, Orientation.FRONT, 1, 1, 1, 1, 1);
        card.getResourceType();
        card.getNumResource(Resource.LEAF);
        card.getNumResource(Resource.MUSHROOM);
        card.getNumResource(Resource.BUTTERFLY);
        card.getNumResource(Resource.WOLF);
        card.getNumResource(Resource.LEAF);
        card.getNumResource(Resource.MUSHROOM);
        card.getNumResource(Resource.BUTTERFLY);
        card.getNumResource(Resource.WOLF);

        ResourceObjectiveCard card2 = new ResourceObjectiveCard(1, Orientation.FRONT, 1, 0, 1, 1, 1);
        card2.getResourceType();
        ResourceObjectiveCard card3 = new ResourceObjectiveCard(1, Orientation.FRONT, 1, 0, 0, 1, 1);
        card3.getResourceType();
        ResourceObjectiveCard card4 = new ResourceObjectiveCard(1, Orientation.FRONT, 1, 0, 0, 0, 1);
        card4.getResourceType();
    }


}