package it.polimi.demo.model;

import it.polimi.demo.model.board.CommonBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommonBoardTest {
    private CommonBoard commonBoard;

    @BeforeEach
    public void setUp() {
        this.commonBoard = new CommonBoard();
        this.commonBoard.setPlayerCount(3);
    }

    @Test
    public void testDrawFromConcreteDeck() {
        this.commonBoard.initializeBoard();

        // Test per estrarre una carta da un deck valido
        assertNotNull(commonBoard.drawFromConcreteDeck(0)); // Deck delle risorse
        assertNotNull(commonBoard.drawFromConcreteDeck(1)); // Deck dell'oro

        // Test per estrarre una carta da un deck non valido
        assertNull(commonBoard.drawFromConcreteDeck(-1)); // Indice negativo
        assertNull(commonBoard.drawFromConcreteDeck(2)); // Indice troppo grande
    }

    @Test
    public void testDrawFromTable() {
        this.commonBoard.initializeBoard();

        // Test per estrarre una carta dalla tabella con indici validi
        assertNotNull(commonBoard.drawFromTable(0, 0, 0)); // Estrazione dalla riga 0, colonna 0, deck delle risorse
        assertNotNull(commonBoard.drawFromTable(1, 1, 1)); // Estrazione dalla riga 1, colonna 1, deck dell'oro

        // Test per estrarre una carta dalla tabella con indici non validi
        assertNull(commonBoard.drawFromTable(-1, 0, 0)); // Riga negativa
        assertNull(commonBoard.drawFromTable(0, 2, 0)); // Colonna troppo grande
        assertNull(commonBoard.drawFromTable(0, 0, -1)); // Deck negativo
        assertNull(commonBoard.drawFromTable(0, 0, 2)); // Deck troppo grande
    }


    @Test
    public void testInitializeBoard() {
        this.commonBoard.initializeBoard();

        for (int i = 0; i <= 29; ++i) {
            assertNotNull(this.commonBoard.getBoardNodes()[i]);
        }

        assertEquals(30, commonBoard.getBoardNodes().length);

        // Test if table cards are populated with cards from the decks
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                assertNotNull(commonBoard.getTableCards()[i][j]);
            }
        }

        assertEquals(38, commonBoard.getResourceConcreteDeck().size());
        assertEquals(38, commonBoard.getGoldConcreteDeck().size());
        assertEquals(14, commonBoard.getObjectiveConcreteDeck().size());


        //assertEquals(6, commonBoard.getStarterConcreteDeck().size()); //PROBLEM

        // Verify that the decks are correctly positioned on the table.
        assertNotNull(commonBoard.getDecks().get(0));
        assertNotNull(commonBoard.getDecks().get(1));
        assertNotNull(commonBoard.getDecks().get(2));

        //Verify the pop of Resource and Gold decks
        assertNotNull(commonBoard.drawFromConcreteDeck(0));
        System.out.println(commonBoard.drawFromConcreteDeck(0));
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
        assertTrue(commonBoard.getBoardNode(0).isPlayerPresent(0));
        assertFalse(commonBoard.getBoardNode(1).isPlayerPresent(0));

        //testing players moves
        commonBoard.movePlayer(0, 5);
        assertEquals(5, commonBoard.getPlayerPosition(0));
        // Test moving beyond the board bounds
        commonBoard.movePlayer(0, 14);
        assertEquals(19, commonBoard.getPlayerPosition(0));

        commonBoard.movePlayer(1, 4);
        commonBoard.movePlayer(1, 17);
        assertEquals(21, commonBoard.getPlayerPosition(1));
        assertEquals(1, commonBoard.getPartialWinner());

        commonBoard.movePlayer(0, 3);
        assertEquals(1, commonBoard.getPartialWinner());




        /*

        //BIIIIIIIIIG PROBLEM
        assertEquals(1, commonBoard.getPartialWinner());

        commonBoard.movePlayer(1, 34);
        assertEquals(4, commonBoard.getPlayerPosition(1));

        commonBoard.movePlayer(2,29);
        assertEquals(29, commonBoard.getPlayerPosition(2));


        // Testing partial winner
        commonBoard.movePlayer(1, 21);
        //assertEquals(2, commonBoard.getPartialWinner());
        // Testing partial winner cannot be overwritten
        commonBoard.movePlayer(0, 14);
        assertEquals(2, commonBoard.getPartialWinner());*/
    }

}
