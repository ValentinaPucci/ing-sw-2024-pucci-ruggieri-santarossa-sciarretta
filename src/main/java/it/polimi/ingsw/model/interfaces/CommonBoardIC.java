package it.polimi.ingsw.model.interfaces;

import it.polimi.ingsw.model.ConcreteDeck;
import it.polimi.ingsw.model.board.CommonBoardNode;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.objectiveCards.ObjectiveCard;

public interface CommonBoardIC {
    /**
     *
     * @return the concrete deck of the resource cards
     */
    ConcreteDeck getResourceConcreteDeck() ;

    /**
     *
     * @return the concrete deck of the gold cards
     */
    ConcreteDeck getGoldConcreteDeck() ;

    /**
     *
     * @return the concrete deck of the starter cards
     */
    ConcreteDeck getStarterConcreteDeck() ;

    /**
     *
     * @return the concrete deck of the objective cards
     */
    ConcreteDeck getObjectiveConcreteDeck();

    /**
     * This method initializes the board
     */
    void initializeBoard();

    /**
     * This method sets the initial position of the players
     */
    Card drawFromConcreteDeck(int ConcreteDeckIndex);

    /**
     * This method draws a card from the table
     * @param row the row of the card
     * @param col the column of the card
     * @return the card drawn
     */
    Card drawFromTable(int row, int col, int ConcreteDeckIndex);

    /**
     * This method sets the initial position of the players
     */
    int getPlayerPosition(int player_index);

    /**
     * This method moves the player
     * @param playerIndex the index of the player
     * @param delta the delta of the movement
     */
    void movePlayer(int playerIndex, int delta);

    /**
     * This method returns the board nodes
     * @return the board nodes
     */
    CommonBoardNode[] getBoardNodes();

    /**
     * This method returns the board node at the specified index
     * @param index the index of the board node
     * @return the board node
     */
    CommonBoardNode getBoardNode(int index);

    /**
     * This method returns the table cards
     * @return the table cards
     */
    Card[][] getTableCards();

    /**
     * This method returns the partial winner
     * @return the partial winner
     */
    int getPartialWinner();

    /**
     * This method returns the player count
     * @return the player count
     */
    int getPlayerCount();

    ConcreteDeck[] getDecks();
    ObjectiveCard[] getCommonObjectives();
}
