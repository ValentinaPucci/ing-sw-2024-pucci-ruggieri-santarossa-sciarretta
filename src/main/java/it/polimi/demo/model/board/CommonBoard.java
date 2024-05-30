package it.polimi.demo.model.board;

import it.polimi.demo.model.interfaces.CommonBoardIC;
import it.polimi.demo.model.ConcreteDeck;
import it.polimi.demo.model.cards.Card;
import it.polimi.demo.model.cards.objectiveCards.ObjectiveCard;
import it.polimi.demo.model.exceptions.EmptyStackException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//NB il giocatore iniziale (pedina nera) è il giocatore 0

public class CommonBoard implements CommonBoardIC, Serializable {

    private ConcreteDeck resource_concrete_deck; // resource_concrete_deck
    private ConcreteDeck gold_concrete_deck; // gold_concrete_deck
    private ConcreteDeck starter_concrete_deck;
    private ConcreteDeck objective_concrete_deck; // objective_concrete_deck
    private List<ConcreteDeck> decks;
    int num_players;
    private CommonBoardNode[] board_nodes; // Array of nodes representing the board
    private Card[][] table_cards; // Cards on the table
    private int partial_winner = -1;

    public CommonBoard() {
        this.resource_concrete_deck = new ConcreteDeck("Resource"); //resource_concrete_deck
        this.gold_concrete_deck = new ConcreteDeck("Gold"); //gold_concrete_deck
        this.starter_concrete_deck = new ConcreteDeck("Starter");
        this.objective_concrete_deck = new ConcreteDeck("Objective"); //objective_concrete_deck
        this.board_nodes = new CommonBoardNode[30];
        this.decks = new ArrayList<>(3); // Create an array to hold the three decks
        this.table_cards = new Card[3][2]; // And a matrix to hold the table cards
    }

     public ConcreteDeck getResourceConcreteDeck() {return resource_concrete_deck;}
     public ConcreteDeck getGoldConcreteDeck() {return gold_concrete_deck;}
     public ConcreteDeck getStarterConcreteDeck() {return starter_concrete_deck;}
     public ConcreteDeck getObjectiveConcreteDeck() {return objective_concrete_deck;}

    /**
     * Board initializer
     */
    public void initializeBoard() {
        setInitialPosition();
        // Populate the table with cards from the decks
        for (int i = 0; i < 2; i++) {
            // Populate the first array with two cards from the Resource ConcreteDeck
            try {
                Card cardFromConcreteDeck1 = resource_concrete_deck.pop();
                table_cards[0][i] = cardFromConcreteDeck1;
            } catch (EmptyStackException e) {
                System.err.println("Empty deck: " + e.getMessage());
            }

            // Populate the second array with two cards from the Gold ConcreteDeck
            try {
                Card cardFromConcreteDeck2 = gold_concrete_deck.pop();
                table_cards[1][i] = cardFromConcreteDeck2;
            } catch (EmptyStackException e) {
                System.err.println("Empty deck: " + e.getMessage());
            }

            // Populate the second array with two cards from the Objective ConcreteDeck
            try {
                Card cardFromConcreteDeck3 = objective_concrete_deck.pop();
                table_cards[2][i] = cardFromConcreteDeck3;
            } catch (EmptyStackException e) {
                System.err.println("Empty deck: " + e.getMessage());
            }
        }
        decks.add(0, this.resource_concrete_deck);
        decks.add(1, this.gold_concrete_deck);
        decks.add(2, this.objective_concrete_deck);
    }

    // Method to draw a card directly from a ConcreteDeck
    //        decks[0] = this.resource_concrete_deck;
    //        decks[1] = this.gold_concrete_deck;
    //        decks[2] = this.objective_concrete_deck;
    public Card drawFromConcreteDeck(int concrete_deck_index) {
        if (concrete_deck_index >= 0 && concrete_deck_index < 2) {
            try {
                return decks.get(concrete_deck_index).pop();
                // the return of this function is the card that will be taken by the player
            } catch (EmptyStackException e) {
                System.err.println("Empty deck: " + e.getMessage());
            }
        }
        return null;
    }

//    /**
//     * Method to draw a card from the Resource ConcreteDeck
//     * @param index the index of the deck to pop
//     * @return the card drawn
//     */
//    public ResourceCard drawFromConcreteResourceDeck(int index) {
//        if (index == 0) {
//            try {
//                return decks.get(index).popResourceCard();
//                // the return of this function is the card that will be taken by the player
//            } catch (EmptyStackException e) {
//                System.err.println("Empty deck: " + e.getMessage());
//            }
//        }
//        return null;
//    }
//
//    /**
//     * Method to draw a card from the Gold ConcreteDeck
//     * @param index the index of the deck to pop
//     * @return the card drawn
//     */
//    public GoldCard drawFromConcreteGoldDeck(int index) {
//        if (index == 1) {
//            try {
//                return decks.get(index).popGoldCard();
//                // the return of this function is the card that will be taken by the player
//            } catch (EmptyStackException e) {
//                System.err.println("Empty deck: " + e.getMessage());
//            }
//        }
//        return null;
//    }

    // Method to draw a card from the table and replace it with a card from the corresponding ConcreteDeck
    public Card drawFromTable(int row, int col, int concrete_deck_index) {
        if (row >= 0 && row < 2 && col >= 0 && col < 2 && concrete_deck_index >= 0 && concrete_deck_index < 2) {
            // Remove the card from the table and store it
            Card drawnCard = table_cards[row][col];
            try {
                // Draw a card from the corresponding ConcreteDeck and replace it on the table
                table_cards[row][col] = decks.get(concrete_deck_index).pop();
            } catch (EmptyStackException e) {
                System.err.println("Empty deck: " + e.getMessage());
            }
            // Return the drawn card
            return drawnCard;
        }
        return null;
    }



    // Method to get the position of a player
    public int getPlayerPosition(int player_index) {
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
        if (new_position >= 0 && new_position <= 29) {
            // Remove the player from the current position
            board_nodes[current_position].setPlayer(playerIndex, false);
            // Set the player to the new position
            board_nodes[new_position].setPlayer(playerIndex, true);
        }
        // Moving beyond the board bounds
        if (new_position > 29) {
            int new_new_position = new_position % 30;
            board_nodes[current_position].setPlayer(playerIndex, false);
            // Set the player to the new position
            board_nodes[new_new_position].setPlayer(playerIndex, true);
        }

        // Check if a player reaches 20 points for the first time
        if (getPlayerPosition(playerIndex) >= 20 && partial_winner == -1) {
            setPartialWinner(playerIndex);
        }
    }

    public CommonBoardNode[] getBoardNodes() {
        return board_nodes;
    }

    // Method to get a specific board node by index
    public CommonBoardNode getBoardNode(int index) {
        if (index >= 0 && index < board_nodes.length) {
            return board_nodes[index];
        }
        return null;
    }

    // Method to get the table cards array
    public Card[][] getTableCards() {
        return table_cards;
    }

    public int getPartialWinner(){
        return partial_winner;
    }

    // Method to set a player as partial winner
    public void setPartialWinner(int playerIndex) {
        this.partial_winner = playerIndex;
    }

    public int getPlayerCount() {
        return num_players;
    }

    public void setPlayerCount(int num_players){
        this.num_players = num_players;
    }

    public void setInitialPosition() {
        for (int i = 0; i <= 29; i++) {
            board_nodes[i] = new CommonBoardNode(i);
        }
        for (int i = 0; i < getPlayerCount(); i++) {
            board_nodes[0].setPlayer(i, true);
        }
    }

    public List<ConcreteDeck> getDecks(){
        return this.decks;
    }

    public List<ObjectiveCard> getCommonObjectives() {
        List<ObjectiveCard> common_objectives = new ArrayList<>(2);
        common_objectives.add(0, (ObjectiveCard) table_cards[2][0]);
        common_objectives.add(1, (ObjectiveCard) table_cards[2][1]);
        return common_objectives;
    }

    @Override
    //necessary for the GUI
    public Integer[] getCommonCardsId() {
        Integer[] cards = new Integer[6];
        cards[0] = table_cards[0][0].getId(); //deck risorsa
        cards[1] = table_cards[0][1].getId(); //card risorsa
        cards[2] = table_cards[1][0].getId(); //deck gold
        cards[3] = table_cards[1][1].getId(); //card gold
        cards[4] = getCommonObjectives().getFirst().getId();
        cards[5] = getCommonObjectives().get(1).getId();
        return cards;
    }
}