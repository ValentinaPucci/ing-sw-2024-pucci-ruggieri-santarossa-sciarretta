package src.main.java.it.polimi.ingsw.model;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

//NB il giocatore iniziale (pedina nera) è il giocatore 0

public class CommonBoard {
    private final ConcreteDeck ResourceConcreteDeck; // ResourceConcreteDeck
    private final ConcreteDeck GoldConcreteDeck; // GoldConcreteDeck
    private final ConcreteDeck ObjectiveConcreteDeck; // ObjectiveConcreteDeck
    private final ConcreteDeck[] ConcreteDecks;
    private final CommonBoardNode[] boardNodes; // Array of nodes representing the board
    private final Card[][] tableCards; // Cards on the table


    public CommonBoard(String boardImage) {
        ResourceConcreteDeck = new ConcreteDeck(); //ResourceConcreteDeck
        GoldConcreteDeck = new ConcreteDeck(); //GoldConcreteDeck
        ObjectiveConcreteDeck = new ConcreteDeck(); //ObjectiveConcreteDeck
        boardNodes = new CommonBoardNode[29];
        ConcreteDecks = new ConcreteDeck[3]; // Create an array to hold the two ConcreteDecks
        tableCards = new Card[3][2];
        initializeBoard();

    }

    // Method to initialize the board
    private void initializeBoard() {
        for (int i = 0; i < 29; i++) {
            boardNodes[i] = new CommonBoardNode(i);
        }
        // Populate the table with cards from the ConcreteDecks
        for (int i = 0; i < 2; i++) {
            // Populate the first array with two cards from the Resource ConcreteDeck
            Card cardFromConcreteDeck1 = ResourceConcreteDeck.pop();
            tableCards[0][i] = cardFromConcreteDeck1;

            // Populate the second array with two cards from the Gold ConcreteDeck
            Card cardFromConcreteDeck2 = GoldConcreteDeck.pop();
            tableCards[1][i] = cardFromConcreteDeck2;

            // Populate the second array with two cards from the Objective ConcreteDeck
            Card cardFromConcreteDeck3 = ObjectiveConcreteDeck.pop();
            tableCards[2][i] = cardFromConcreteDeck3;
        }
        ConcreteDecks[0] = ResourceConcreteDeck;
        ConcreteDecks[1] = GoldConcreteDeck;
        ConcreteDecks[2] = ObjectiveConcreteDeck;
    }


    // Method to draw a card directly from a ConcreteDeck
    public Card drawFromConcreteDeck(int ConcreteDeckIndex) {
        if (ConcreteDeckIndex >= 0 && ConcreteDeckIndex < 2) {
            return ConcreteDecks[ConcreteDeckIndex].pop(); //the return of this function is the card that will be taken by the player
        }
        return null;
    }


    // Method to draw a card from the table and replace it with a card from the corresponding ConcreteDeck
    public Card drawFromTable(int row, int col, int ConcreteDeckIndex) {
        if (row >= 0 && row < 2 && col >= 0 && col < 2 && ConcreteDeckIndex >= 0 && ConcreteDeckIndex < 2) {
            // Remove the card from the table and store it
            Card drawnCard = tableCards[row][col];
            // Draw a card from the corresponding ConcreteDeck and replace it on the table
            tableCards[row][col] = ConcreteDecks[ConcreteDeckIndex].pop();
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




