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

    // Add more tests for other methods
}
