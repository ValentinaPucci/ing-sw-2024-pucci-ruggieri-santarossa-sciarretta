package it.polimi.ingsw.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommonBoardTest {
    private CommonBoard commonBoard;

    @BeforeEach
    public void setUp() {
        CardsCollection resource_cards_collection = new CardsCollection();
        CardsCollection gold_cards_collection = new CardsCollection();
        CardsCollection starter_cards_collection = new CardsCollection();
        CardsCollection objective_cards_collection = new CardsCollection();
        String resource_type = "Resource";
        String gold_type = "Gold";
        String starter_type = "Starter";
        String objective_type = "Objective";
        int num_players = 3;
        this.commonBoard = new CommonBoard(resource_cards_collection, gold_cards_collection, starter_cards_collection, objective_cards_collection, resource_type, gold_type, starter_type, objective_type, num_players);
    }

    @Test
    public void testInitializeBoard() {
        this.commonBoard.initializeBoard();

        for (int i = 0; i < 29; ++i) {
            assertNotNull(this.commonBoard.getBoardNodes()[i]);
        }

        // Test if table cards are populated with cards from the decks
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                assertNotNull(commonBoard.getTableCards()[i][j]);
            }
        }
        // Verify that the decks are correctly positioned on the table.
        assertNotNull(commonBoard.getDecks()[0]);
        assertNotNull(commonBoard.getDecks()[1]);
        assertNotNull(commonBoard.getDecks()[2]);

        //Verify the pop of Resource and Gold decks
        assertNotNull(commonBoard.drawFromConcreteDeck(0));
        assertNotNull(commonBoard.drawFromConcreteDeck(1));
        assertNull(commonBoard.drawFromConcreteDeck(-1));
        assertNull(commonBoard.drawFromConcreteDeck(2));

        //Verify the draw from the table
        assertNotNull(commonBoard.drawFromTable(0, 0, 0));
        assertNotNull(commonBoard.drawFromTable(1, 1, 1));
        assertNull(commonBoard.drawFromTable(-1, 0, 0));
        assertNull(commonBoard.drawFromTable(0, 2, 0));
        assertNull(commonBoard.drawFromTable(0, 0, -1));
        assertNull(commonBoard.drawFromTable(0, 0, 2));
    }

    @Test
    public void testMovePlayer() {
        this.commonBoard.initializeBoard();

        //testing initial position of players
        assertTrue(commonBoard.getBoardNode(0).getPlayers()[0]);
        assertTrue(commonBoard.getBoardNode(0).getPlayers()[1]);
        assertTrue(commonBoard.getBoardNode(0).getPlayers()[2]);
        assertFalse(commonBoard.getBoardNode(1).getPlayers()[0]);

        //testing players moves
        commonBoard.movePlayer(0, 5);
        assertEquals(5, commonBoard.getPlayerPosition(0));
        // Test moving beyond the board bounds
        commonBoard.movePlayer(0, 30);
        assertEquals(6, commonBoard.getPlayerPosition(0));

        // Testing partial winner
        commonBoard.movePlayer(1, 21);
        assertEquals(1, commonBoard.getPartialWinner());
        // Testing partial winner cannot be overwritten
        commonBoard.movePlayer(0, 14);
        assertEquals(1, commonBoard.getPartialWinner());
    }

}
