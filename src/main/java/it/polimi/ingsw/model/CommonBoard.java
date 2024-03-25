package it.polimi.ingsw.model;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

//NB il giocatore iniziale (pedina nera) è il giocatore 0

public class CommonBoard {
    private ConcreteDeck resource_concrete_deck; // resource_concrete_deck
    private ConcreteDeck gold_concrete_deck; // gold_concrete_deck
    private ConcreteDeck starter_concrete_deck;
    private ConcreteDeck objective_concrete_deck; // objective_concrete_deck
    private ConcreteDeck[] decks;
    private CommonBoardNode[] board_nodes; // Array of nodes representing the board
    private Card[][] table_cards; // Cards on the table
    private int partial_winner = -1;;


    public CommonBoard(CardsCollection resource_cards_collection,
                       CardsCollection gold_cards_collection,
                       CardsCollection starter_cards_collection,
                       CardsCollection objective_cards_collection,
                       String resource_path, String gold_path, String starter_path, String objective_path,
                       String resource_type, String gold_type, String  starter_type, String objective_type) {
        resource_concrete_deck = new ConcreteDeck(resource_cards_collection, resource_path, resource_type); //resource_concrete_deck
        gold_concrete_deck = new ConcreteDeck(gold_cards_collection, gold_path, gold_type); //gold_concrete_deck
        starter_concrete_deck = new ConcreteDeck(starter_cards_collection, starter_path, starter_type);
        objective_concrete_deck = new ConcreteDeck(objective_cards_collection, objective_path, objective_type); //objective_concrete_deck
        board_nodes = new CommonBoardNode[29];
        decks = new ConcreteDeck[3]; // Create an array to hold the two decks
        table_cards = new Card[3][2];

    }
     public ConcreteDeck getResourceConcreteDeck(){return resource_concrete_deck;}
     public ConcreteDeck getGoldConcreteDeck(){return gold_concrete_deck;}
    public ConcreteDeck getStarterConcreteDeck(){return starter_concrete_deck;}
     public ConcreteDeck getObjectiveConcreteDeck(){return objective_concrete_deck;}

    // Method to initialize the board
    public void initializeBoard() {
        for (int i = 0; i < 29; i++) {
            board_nodes[i] = new CommonBoardNode(i);
        }
        // Populate the table with cards from the decks
        for (int i = 0; i < 2; i++) {
            // Populate the first array with two cards from the Resource ConcreteDeck
            Card cardFromConcreteDeck1 = resource_concrete_deck.pop();
            table_cards[0][i] = cardFromConcreteDeck1;

            // Populate the second array with two cards from the Gold ConcreteDeck
            Card cardFromConcreteDeck2 = gold_concrete_deck.pop();
            table_cards[1][i] = cardFromConcreteDeck2;

            // Populate the second array with two cards from the Objective ConcreteDeck
            Card cardFromConcreteDeck3 = objective_concrete_deck.pop();
            table_cards[2][i] = cardFromConcreteDeck3;
        }
        decks[0] = resource_concrete_deck;
        decks[1] = gold_concrete_deck;
        decks[2] = objective_concrete_deck;
    }


    // Method to draw a card directly from a ConcreteDeck
    public Card drawFromConcreteDeck(int ConcreteDeckIndex) {
        if (ConcreteDeckIndex >= 0 && ConcreteDeckIndex < 2) {
            return decks[ConcreteDeckIndex].pop(); //the return of this function is the card that will be taken by the player
        }
        return null;
    }


    // Method to draw a card from the table and replace it with a card from the corresponding ConcreteDeck
    public Card drawFromTable(int row, int col, int ConcreteDeckIndex) {
        if (row >= 0 && row < 2 && col >= 0 && col < 2 && ConcreteDeckIndex >= 0 && ConcreteDeckIndex < 2) {
            // Remove the card from the table and store it
            Card drawnCard = table_cards[row][col];
            // Draw a card from the corresponding ConcreteDeck and replace it on the table
            table_cards[row][col] = decks[ConcreteDeckIndex].pop();
            // Return the drawn card
            return drawnCard;
        }
        return null;
    }

    // Method to get the position of a player
    private int getPlayerPosition(int player_index) {
        for (CommonBoardNode node : board_nodes) {
            if (node.isPlayerPresent(player_index)) {
                return node.getNodeNumber();
            }
        }
        return -1; // Player not found
    }



    // Method to move a player by a specified delta
    public void movePlayer(int playerIndex, int delta) {
        // Get the current position of the player
        int current_position = getPlayerPosition(playerIndex);

        // Calculate the new position by adding delta
        int new_position = current_position + delta;

        // Check if the new position is within the board bounds
        if (new_position >= 0 && new_position < 29) {
            // Remove the player from the current position
            board_nodes[current_position].setPlayer(playerIndex, false);
            // Set the player to the new position
            board_nodes[new_position].setPlayer(playerIndex, true);
        }

        // Check if a player reaches 20 points for the first time
        if (getPlayerPosition(playerIndex) >= 20 && partial_winner == -1) {
            setPartialWinner(playerIndex);
        }

    }



    public int getPartialWinner(){
        return partial_winner;
    }

    // Method to set a player as partial winner
    public void setPartialWinner(int playerIndex) {
        this.partial_winner = playerIndex;
    }

}




