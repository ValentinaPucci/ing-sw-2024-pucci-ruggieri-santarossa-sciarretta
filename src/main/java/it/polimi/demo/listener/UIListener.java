package it.polimi.demo.listener;

import it.polimi.demo.model.enumerations.Orientation;

import java.rmi.RemoteException;

public interface UIListener extends Listener {

    void refreshStartUI();

    /**
     * This method is invoked when startUI wants to place the starter card.
     * @param orientation the orientation of the starter card
     */
    void placeStarterCard(Orientation orientation);

    /**
     * This method is invoked when startUI wants to create a new game.
     */
    void createGame(String nickname, int numberOfPlayers)throws RemoteException;

    /**
     * This method is invoked by gameUI when the player wants to choose a card
     * @param which_card the index of the card the player wants to choose
     */
    void chooseCard(int which_card);

    /**
     * This method is invoked by gameUI when the player wants to place a card.
     * All the parameters are meant as indexes or coordinates.
     *
     * @param where_to_place_x the x coordinate of the cell where the player wants to place the card
     * @param where_to_place_y the y coordinate of the cell where the player wants to place the card
     * @param orientation
     */
     void placeCard(int where_to_place_x, int where_to_place_y, Orientation orientation);

    /**
     * This method is invoked by gameUI when the player wants to draw a card.
     * @param index the index of the card the player wants to draw
     */
     void drawCard(int index);

    /**
     *
     * This method is invoked when startUI wants to join a game.
     */
    void joinGame(int gameID, String nickname);

    void exit();
}
