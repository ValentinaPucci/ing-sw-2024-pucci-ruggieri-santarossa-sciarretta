package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonalBoardTest {
    private PersonalBoard personalBoard;

    @BeforeEach
    public void setup() {
        personalBoard = new PersonalBoard();
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
    public void testBruteForcePlaceCardAtSE() {
        // Create a ResourceCard object
        ResourceCard card = new ResourceCard(1, Orientation.FRONT, Color.RED);

        // Place the card at a specific position on the board
        personalBoard.bruteForcePlaceCardSE(card, 0, 0);

        // Verify that the card has been placed correctly
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                assertEquals(card.getCornerAt(i, j), personalBoard.board[i][j].getCornerFromCell());
            }
        }
    }
}
