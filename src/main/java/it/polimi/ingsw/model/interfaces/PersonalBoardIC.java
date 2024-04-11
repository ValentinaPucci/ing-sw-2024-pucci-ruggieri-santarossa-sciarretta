package it.polimi.ingsw.model.interfaces;

import it.polimi.ingsw.model.cards.gameCards.GoldCard;
import it.polimi.ingsw.model.cards.gameCards.ResourceCard;
import it.polimi.ingsw.model.cards.gameCards.StarterCard;
import it.polimi.ingsw.model.enumerations.Item;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.IllegalMoveException;

public interface PersonalBoardIC {


    /**
     * This method simply put a StarterCard at position (i,j) of the board
     * without doing any check. It is used for the placement of the first
     * card or in other marginal situations, like for the construction of
     * sub-matrices of personalBoard in other classes. SE because we use
     * a classic for loop with increasing indexes both for i and j.
     *
     * @param card;
     * @param i;
     * @param j;
     */
    void bruteForcePlaceCardSE(StarterCard card, int i, int j);

    /**
     * This method simply put a ResourceCard at position (i,j) of the board
     * without doing any check
     */

    void bruteForcePlaceCardSE(ResourceCard card, int i, int j);

    /**
     * This method is used to place a card on the NE card's angle
     * @param game_card;
     * @param card_to_play;
     * @throws IllegalMoveException;
     */
    void placeCardAtNE(ResourceCard game_card, ResourceCard card_to_play)
            throws IllegalMoveException;

    /**
     * This method is used to place a ResourceCard on the NW card's angle
     * @param game_card;
     * @param card_to_play;
     * @throws IllegalMoveException;
     */
    void placeCardAtNW(ResourceCard game_card, ResourceCard card_to_play)
            throws IllegalMoveException;

    /**
     * This method is used to place a ResourceCard on the SW card's angle
     * @param game_card;
     * @param card_to_play;
     * @throws IllegalMoveException;
     */
    void placeCardAtSW(ResourceCard game_card, ResourceCard card_to_play)
            throws IllegalMoveException;

    /**
     * This method is used to place a ResourceCard on the SE card's angle
     * @param game_card;
     * @param card_to_play;
     * @throws IllegalMoveException;
     */
    void placeCardAtSE(ResourceCard game_card, ResourceCard card_to_play)
            throws IllegalMoveException;

    /**
     * This method is used to place a GoldCard on the NE card's angle
     * @param game_card;
     * @param card_to_play;
     * @throws IllegalMoveException;
     */
    void placeCardAtNE(ResourceCard game_card, GoldCard card_to_play)
            throws IllegalMoveException;

    /**
     * This method is used to place a GoldCard on the NW card's angle
     * @param game_card;
     * @param card_to_play;
     * @throws IllegalMoveException;
     */
    void placeCardAtNW(ResourceCard game_card, GoldCard card_to_play)
            throws IllegalMoveException;

    /**
     * This method is used to place a GoldCard on the SW card's angle
     * @param game_card;
     * @param card_to_play;
     * @throws IllegalMoveException;
     */
    void placeCardAtSW(ResourceCard game_card, GoldCard card_to_play)
            throws IllegalMoveException;

    /**
     * This method is used to place a GoldCard on the SE card's angle
     * @param game_card;
     * @param card_to_play;
     * @throws IllegalMoveException;
     */
    void placeCardAtSE(ResourceCard game_card, GoldCard card_to_play)
            throws IllegalMoveException;

    /**
     * This method is used to get the number of mushrooms on PersonalBoard
     * @return num_mushroom
     */

    int getNum_mushrooms();

    /**
     * This method is used to get the number of leaves on PersonalBoard
     * @return num_leaves
     */
    int getNum_leaves();

    /**
     * This method is used to get the number of butterflies on PersonalBoard
     * @return num_butterflies
     */
    int getNum_butterflies();

    /**
     * This method is used to get the number of wolves on PersonalBoard
     * @return num_wolves
     */
    int getNum_wolves();


    /**
     * This method is used to get the number of parchments on PersonalBoard
     * @return num_parchments;
     */
    int getNum_parchments();

    /**
     * This method is used to get the number of feathers on PersonalBoard
     * @return num_feathers;
     */

    int getNum_feathers();

    /**
     * This method is used to get the number of potions on PersonalBoard
     * @return num_potions;
     */

    int getNum_potions();

    /**
     *
     * @param resource;
     * @return number of the specified type of resource
     */

    int getNumResource(Resource resource);

    /**
     *
     * @param item;
     * @return number of the specified type of item
     */
    int getNumItem(Item item);



}
