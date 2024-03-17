package src.main.java.it.polimi.ingsw.model;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

//NB il giocatore iniziale (pedina nera) è il giocatore 0

public class CommonBoard {
    private final Deck deck1; // ResourceDeck
    private final Deck deck2; // GoldDeck
    private final Deck deck3; // ObjectiveDeck
    private final CommonBoardNode[] boardNodes; // Array of nodes representing the board
    private Card[][] tableCards; // Cards on the table


    public CommonBoard(String boardImage) {
        deck1 = new Deck(); //ResourceDeck
        deck2 = new Deck(); //GoldDeck
        deck3 = new Deck(); //ObjectiveDeck
        boardNodes = new CommonBoardNode[29];
        decks = new Deck[3]; // Create an array to hold the two decks
        initializeBoard();

    }

    // Method to initialize the board
    private void initializeBoard() {
        for (int i = 0; i < 29; i++) {
            boardNodes[i] = new CommonBoardNode(i);
        }
        // Populate the table with cards from the decks
        for (int i = 0; i < 2; i++) {
            // Populate the first array with two cards from the Resource deck
            Card cardFromDeck1 = deck1.pop();
            tableCards[0][i] = cardFromDeck1;

            // Populate the second array with two cards from the Gold deck
            Card cardFromDeck2 = deck2.pop();
            tableCards[1][i] = cardFromDeck2;

            // Populate the second array with two cards from the Objective deck
            Card cardFromDeck3 = deck3.pop();
            tableCards[2][i] = cardFromDeck3;
        }
        decks[0] = deck1;
        decks[1] = deck2;
        decks[2] = deck3;
    }


    // Method to draw a card directly from a deck
    public Card drawFromDeck(int deckIndex) {
        if (deckIndex >= 0 && deckIndex < 2) {
            return decks[deckIndex].pop(); //the return of this function is the card that will be taken by the player
        }
        return null;
    }


    // Method to draw a card from the table and replace it with a card from the corresponding deck
    public Card drawFromTable(int row, int col, int deckIndex) {
        if (row >= 0 && row < 2 && col >= 0 && col < 2 && deckIndex >= 0 && deckIndex < 2) {
            // Remove the card from the table and store it
            Card drawnCard = tableCards[row][col];
            // Draw a card from the corresponding deck and replace it on the table
            tableCards[row][col] = decks[deckIndex].pop();
            // Return the drawn card
            return drawnCard;
        }
        return null;
    }



    // Method to get the position of a player
    private int getPlayerPosition(int playerIndex) {
        for (CommonBoardNode node : boardNodes) {
            if (node.isPlayerPresent(playerIndex)) {
                return node.getNodeNumber();
            }
        }
        return -1; // Player not found
    }


    // Method to move a player by a specified delta
    public void movePlayer(int playerIndex, int delta) {
        // Get the current position of the player
        int currentPosition = getPlayerPosition(playerIndex);

        // Calculate the new position by adding delta
        int newPosition = currentPosition + delta;

        // Check if the new position is within the board bounds
        if (newPosition >= 0 && newPosition < 29) {
            // Remove the player from the current position
            boardNodes[currentPosition].setPlayer(playerIndex, false);
            // Set the player to the new position
            boardNodes[newPosition].setPlayer(playerIndex, true);
        }
    }

}




