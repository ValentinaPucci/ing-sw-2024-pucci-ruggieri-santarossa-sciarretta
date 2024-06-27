package it.polimi.demo.model.board;

import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.cards.gameCards.StarterCard;
import it.polimi.demo.model.enumerations.*;
import it.polimi.demo.model.exceptions.IllegalMoveException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * This class represents the personal board of a player.
 */

public class PersonalBoard implements Serializable {

    /**
     * Board matrix
     */
    public Cell[][] board;
    private final int dim1;
    private final int dim2;
    private int points;

    /**
     * Number of mushrooms in the personal board.
     */
    private int num_mushrooms;
    /**
     * Number of leaves in the personal board.
     */
    private int num_leaves;
    /**
     * Number of butterflies in the personal board.
     */
    private int num_butterflies;
    /**
     * Number of wolves in the personal board.
     */
    private int num_wolves;
    /**
     * Number of parchments in the personal board.
     */
    private int num_parchments;
    /**
     * Number of feathers in the personal board.
     */
    private int num_feathers;
    /**
     * Number of potions in the personal board.
     */
    private int num_potions;

    /**
     * Constructor.
     */

    public PersonalBoard() {
        this.dim1 = 50;
        this.dim2 = 50;

        this.board = new Cell[dim1][dim2];
        for (int i = 0; i < dim1; i++) {
            for (int j = 0; j < dim2; j++) {
                this.board[i][j] = new Cell();
            }
        }
        this.points = 0;
        this.num_mushrooms = 0;
        this.num_leaves = 0;
        this.num_butterflies = 0;
        this.num_wolves = 0;
        this.num_parchments = 0;
        this.num_feathers = 0;
        this.num_potions = 0;
    }

    /**
     * @param dim
     */
    public PersonalBoard(int dim) {
        this.dim1 = dim;
        this.dim2 = dim;

        this.board = new Cell[dim1][dim2];
        for (int i = 0; i < dim1; i++) {
            for (int j = 0; j < dim2; j++) {
                this.board[i][j] = new Cell();
            }
        }

        this.points = 0;
        this.num_mushrooms = 0;
        this.num_leaves = 0;
        this.num_butterflies = 0;
        this.num_wolves = 0;
        this.num_parchments = 0;
        this.num_feathers = 0;
        this.num_potions = 0;
    }

    /**
     * @param dim1
     * @param dim2
     */
    public PersonalBoard(int dim1, int dim2) {
        this.dim1 = dim1;
        this.dim2 = dim2;

        this.board = new Cell[dim1][dim2];
        for (int i = 0; i < dim1; i++) {
            for (int j = 0; j < dim2; j++) {
                this.board[i][j] = new Cell();
            }
        }

        this.points = 0;
        this.num_mushrooms = 0;
        this.num_leaves = 0;
        this.num_butterflies = 0;
        this.num_wolves = 0;
        this.num_parchments = 0;
        this.num_feathers = 0;
        this.num_potions = 0;
    }


    /**
     * Updates the player's points by adding the points from the specified resource card.
     *
     * @param card The resource card from which points are to be added.
     */
    public void updatePoints(ResourceCard card) {
        this.points += card.points;
    }


    /**
     * Updates the player's points based on the conditions specified by the provided Gold Card.
     * If the card requires feathers, points are increased based on the number of feathers the player has.
     * If the card requires parchments, points are increased based on the number of parchments the player has.
     * If the card requires potions, points are increased based on the number of potions the player has.
     * If the card requires corner coverage, points are increased based on the number of corners covered by level 2 resources on the player's board.
     *
     * @param card The Gold Card specifying the conditions for updating points.
     */
    public void updatePoints(GoldCard card) {

        if (card.getIsFeatherRequired())
            this.points += card.points * this.num_feathers;
        else if (card.getIsParchmentRequired())
            this.points += card.points * this.num_parchments;
        else if (card.getIsPotionRequired())
            this.points += card.points * this.num_potions;
        else if (card.getIsCornerCoverageRequired()) {
            int darken_corners = 0;
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    int k = card.getCornerAt(i, j).board_coordinate.getX();
                    int h = card.getCornerAt(i, j).board_coordinate.getY();
                    if (this.board[k][h].level == 2) {
                            darken_corners++;
                    }
                }
            }
            this.points += card.points * darken_corners;
        }
        else
            this.points += card.points;
    }


    /**
     * Methods used only for tests
     */

    public int getNum_mushrooms() {
        return num_mushrooms;
    }

    public int getNum_leaves() {
        return num_leaves;
    }

    public int getNum_butterflies() {
        return num_butterflies;
    }

    public int getNum_wolves() {
        return num_wolves;
    }

    public int getNum_parchments() {
        return num_parchments;
    }

    public int getNum_feathers() {
        return num_feathers;
    }

    public int getNum_potions() {
        return num_potions;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getDim1() {
        return this.dim1;
    }

    public int getDim2() {
        return this.dim2;
    }


    /**
     * Adds a specified type of resource to the player's inventory.
     * Increments the count of the corresponding resource type.
     */

    public void addResource(Resource resource) {
        if (resource == null) {
            return;
        }
        switch (resource) {
            case LEAF:
                this.num_leaves++;
                break;
            case MUSHROOM:
                this.num_mushrooms++;
                break;
            case BUTTERFLY:
                this.num_butterflies++;
                break;
            case WOLF:
                this.num_wolves++;
                break;
            default:
                break;
        }
    }

    /**
     * Adds a fixed resource of a specified color to the player's inventory.
     * Increments the count of the corresponding resource type based on the color.
     *
     * @param color The color of the resource to be added.
     */

    public void addFixedResource(Color color) {
        switch (color) {
            case GREEN:
                this.num_leaves++;
                break;
            case RED:
                this.num_mushrooms++;
                break;
            case PURPLE:
                this.num_butterflies++;
                break;
            case BLUE:
                this.num_wolves++;
                break;
            default:
                break;
        }
    }

    /**
     * Removes a specified type of resource from the player's inventory.
     * Decrements the count of the corresponding resource type.
     *
     * @param resource The type of resource to be removed.
     */
    public void removeResource(Resource resource) {
        if (resource == null)
            return;
        switch (resource) {
            case LEAF:
                this.num_leaves--;
                break;
            case MUSHROOM:
                this.num_mushrooms--;
                break;
            case BUTTERFLY:
                this.num_butterflies--;
                break;
            case WOLF:
                this.num_wolves--;
                break;
            default:
                break;
        }
    }

    /**
     * Adds a specified type of item to the player's inventory.
     * Increments the count of the corresponding item type.
     *
     * @param item The type of item to be added.
     */
    public void addItem(Item item) {
        if (item == null)
            return;
        switch (item) {
            case PARCHMENT:
                this.num_parchments++;
                break;
            case FEATHER:
                this.num_feathers++;
                break;
            case POTION:
                this.num_potions++;
                break;
            default:
                break;
        }
    }

    /**
     * Removes a specified type of item from the player's inventory.
     * Decrements the count of the corresponding item type.
     *
     * @param item The type of item to be removed.
     */
    public void removeItem(Item item) {
        if (item == null)
            return;
        switch (item) {
            case PARCHMENT:
                this.num_parchments--;
                break;
            case FEATHER:
                this.num_feathers--;
                break;
            case POTION:
                this.num_potions--;
                break;
            default:
                break;
        }
    }

    /**
     * Retrieves the count of a specified type of resource from the player's inventory.
     *
     * @param resource The type of resource to retrieve the count for.
     * @return The count of the specified resource type.
     */

    public int getNumResource(Resource resource) {
        switch (resource) {
            case LEAF:
                return this.num_leaves;
            case MUSHROOM:
                return this.num_mushrooms;
            case BUTTERFLY:
                return this.num_butterflies;
            case WOLF:
                return this.num_wolves;
            default:
                return 0;
        }
    }

    /**
     * Retrieves the count of a specified type of item from the player's inventory.
     *
     * @param item The type of item to retrieve the count for.
     * @return The count of the specified item type.
     */
    public int getNumItem(Item item) {
        switch (item) {
            case PARCHMENT:
                return this.num_parchments;
            case FEATHER:
                return this.num_feathers;
            case POTION:
                return this.num_potions;
            default:
                return 0;
        }
    }

    /**
     * Note that this method has to be overriden by the sublcasses
     *
     * @param i
     * @param j
     * @return true iff the planed move is doable
     */
    public boolean subMatrixCellChecker(int i, int j) {

        for (int k = 0; k < 2; k++) {
            for (int h = 0; h < 2; h++) {
                if (board[i + k][j + h].is_full) {
                    if (!board[i + k][j + h].getCornerFromCell().is_visible
                            || board[i + k][j + h].level >= 2) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * This method simply put a card at position (i,j) of the board
     * without doing any check. It is used only for tests.
     * SE because we use a classic for loop with increasing indexes both for i and j.
     *
     * @param card
     */
    public void bruteForcePlaceCardSE(ResourceCard card, int i, int j) {
        for (int k = 0; k < 2; k++) {
            for (int h = 0; h < 2; h++) {
                this.board[i + k][j + h].setCellAsFull(card.getCornerAt(k, h));
                this.board[i + k][j + h].getCornerFromCell().board_coordinate.setXY(i + k, j + h);
            }
        }
    }

    public void placeStarterCard(StarterCard card) {

        int i = dim1 / 2;
        int j = dim2 / 2;

        for (int k = 0; k < 2; k++) {
            for (int h = 0; h < 2; h++) {
                // aux *****
                card.getCornerAt(k, h).setReference_card(new ResourceCard(-1, Orientation.FRONT, Color.NONE));
                // aux *****
                this.board[i + k][j + h].setCellAsFull(card.getCornerAt(k, h));
                this.board[i + k][j + h].getCornerFromCell().board_coordinate.setXY(i + k, j + h);
            }
        }

        addResource(card.getCornerAtNW().resource);
        addResource(card.getCornerAtNE().resource);
        addResource(card.getCornerAtSW().resource);
        addResource( card.getCornerAtSE().resource);

        addResource(card.front_resource1);
        addResource(card.front_resource2);
        addResource(card.front_resource3);
    }
    

    /**
     * We assume that the game_card's corners have a specified board_coordinate,
     *
     * @param game_card    is the card already on the PersonalBoard
     * @param card_to_play is the card to put on the PersonalBoard
     * ensures card_to_play is attached to another card, specifically in the NE corner
     * of the game_card
     */
    public boolean placeCardAtNE(ResourceCard game_card, ResourceCard card_to_play) {

        /**
         * The following attributes are the starting point in the grid
         * to perform the 'allocation' (namely, the placement) of the
         * card_to_play on the PersonalBoard.
         */
        int i;
        int j;

        Corner corner_aux = game_card.getCornerAtNE();


        if (!corner_aux.is_visible)
            return false;

        i = corner_aux.board_coordinate.getX();
        j = corner_aux.board_coordinate.getY();



        /**
         * i + 1 is a technicality: the checker starts always
         * form the position (0, 0) of the submatrix 2 x 2 it is
         * analyzing.
         */

        if (!subMatrixCellChecker(i - 1, j))
            return false;

        /**
         * we can forget to take **directly** track of the items or
         * the resources which are currently on the board at a certain position,
         * because we know every corner in a given cell. Hence, we know the resources
         * and/or the items in every cell.
         */
        generalUpdateRoutine(i - 1, j, this.board, card_to_play);
        this.updatePoints(card_to_play);

        return true;

    }

    /**
     * overloading:
     *
     * We assume that the game_card's corners have a specified board_coordinate, 
     *
     * @param game_card    is the card already on the PersonalBoard
     * @param card_to_play is the card to put on the PersonalBoard
     * ensures card_to_play is attached to another card, specifically in the NE corner
     * of the game_card
     */
    public boolean placeCardAtNE(ResourceCard game_card, GoldCard card_to_play) {

        int i;
        int j;

        Corner corner_aux = game_card.getCornerAtNE();

        if (!corner_aux.is_visible)
            return false;

        if (card_to_play.getMushroomRequired() > this.num_mushrooms ||
                card_to_play.getLeafRequired() > this.num_leaves ||
                card_to_play.getButterflyRequired() > this.num_butterflies ||
                card_to_play.getWolfRequired() > this.num_wolves)
            return false;

        i = corner_aux.board_coordinate.getX();
        j = corner_aux.board_coordinate.getY();

        if (!subMatrixCellChecker(i - 1, j))
            return false;

        generalUpdateRoutine(i - 1, j, this.board, card_to_play);
        this.updatePoints(card_to_play);

        return true;
    }
    
    
    /**
     * overloading:
     *
     * This method assumes that the corners of the game card have specified board coordinates.
     * It is utilized when placing a new ResourceCard (@param card_to_play) onto a corner
     * of the personal board where a corner of the StarterCard already exists.
     *
     * @param game_card    is the card already on the PersonalBoard
     * @param card_to_play is the card to put on the PersonalBoard
     * ensures card_to_play is attached to another card, specifically in the NE corner
     * of the game_card
     */
    public boolean placeCardAtNE(StarterCard game_card, ResourceCard card_to_play) {

        /**
         * The following attributes are the starting point in the grid
         * to perform the 'allocation' (namely, the placement) of the
         * card_to_play on the PersonalBoard.
         */
        int i;
        int j;

        Corner corner_aux = game_card.getCornerAtNE();

        if (!corner_aux.is_visible)
            return false;

        i = corner_aux.board_coordinate.getX();
        j = corner_aux.board_coordinate.getY();

        /**
         * i + 1 is a technicality: the checker starts always
         * form the position (0, 0) of the submatrix 2 x 2 it is
         * analyzing.
         */
        if (!subMatrixCellChecker(i - 1, j))
            return false;

        /**
         * we can forget to take **directly** track of the items or
         * the resources which are currently on the board at a certain position,
         * because we know every corner in a given cell. Hence, we know the resources
         * and/or the items in every cell.
         */
        generalUpdateRoutine(i - 1, j, this.board, card_to_play);
        this.updatePoints(card_to_play);

        return true;
    }

    /**
     * overloading:
     *
     * This method assumes that the corners of the game card have specified board coordinates.
     * It is utilized when placing a new GoldCard (@param card_to_play) onto a corner
     * of the personal board where a corner of the StarterCard already exists.
     *
     * @param game_card    is the card already on the PersonalBoard
     * @param card_to_play is the card to put on the PersonalBoard
     * ensures card_to_play is attached to another card, specifically in the NE corner
     * of the game_card
     */
    public boolean placeCardAtNE(StarterCard game_card, GoldCard card_to_play) {

        int i;
        int j;

        Corner corner_aux = game_card.getCornerAtNE();

        if (!corner_aux.is_visible)
            return false;

        if (card_to_play.getMushroomRequired() > this.num_mushrooms ||
                card_to_play.getLeafRequired() > this.num_leaves ||
                card_to_play.getButterflyRequired() > this.num_butterflies ||
                card_to_play.getWolfRequired() > this.num_wolves)
            return false;

        i = corner_aux.board_coordinate.getX();
        j = corner_aux.board_coordinate.getY();

        if (!subMatrixCellChecker(i - 1, j))
            return false;

        generalUpdateRoutine(i - 1, j, this.board, card_to_play);
        this.updatePoints(card_to_play);

        return true;
    }


    /**
     * We assume that the game_card's corners have a specified board_coordinate,
     *
     * @param game_card    is the card already on the PersonalBoard
     * @param card_to_play is the card to put on the PersonalBoard
     * ensures card_to_play is attached to another card, specifically in the SE corner
     * of the game_card
     */

    public boolean placeCardAtSE(ResourceCard game_card, ResourceCard card_to_play) {

        int i;
        int j;

        Corner corner_aux = game_card.getCornerAtSE();

        if (!corner_aux.is_visible)
            return false;

        i = corner_aux.board_coordinate.getX();
        j = corner_aux.board_coordinate.getY();

        if (!subMatrixCellChecker(i, j))
            return false;

        generalUpdateRoutine(i, j, this.board, card_to_play);
        this.updatePoints(card_to_play);

        return true;
    }


    /**
     * We assume that the game_card's corners have a specified board_coordinate,
     *
     * @param game_card    is the card already on the PersonalBoard
     * @param card_to_play is the card to put on the PersonalBoard
     * ensures card_to_play is attached to another card, specifically in the SE corner
     * of the game_card
     */
    public boolean placeCardAtSE(ResourceCard game_card, GoldCard card_to_play) {

        int i;
        int j;

        Corner corner_aux = game_card.getCornerAtSE();

        if (!corner_aux.is_visible)
            return false;

        if (card_to_play.getMushroomRequired() > this.num_mushrooms ||
                card_to_play.getLeafRequired() > this.num_leaves ||
                card_to_play.getButterflyRequired() > this.num_butterflies ||
                card_to_play.getWolfRequired() > this.num_wolves)
            return false;

        i = corner_aux.board_coordinate.getX();
        j = corner_aux.board_coordinate.getY();

        if (!subMatrixCellChecker(i, j))
            return false;

        generalUpdateRoutine(i, j, this.board, card_to_play);
        this.updatePoints(card_to_play);

        return true;
    }


    /**
     * We assume that the game_card's corners have a specified board_coordinate,
     * It is utilized when placing a new ResourceCard (@param card_to_play) onto a corner
     * of the personal board where a corner of the StarterCard already exists.
     *
     * @param game_card    is the card already on the PersonalBoard
     * @param card_to_play is the card to put on the PersonalBoard
     * ensures card_to_play is attached to another card, specifically in the SE corner
     * of the game_card
     */
    public boolean placeCardAtSE(StarterCard game_card, ResourceCard card_to_play) {

        int i;
        int j;

        Corner corner_aux = game_card.getCornerAtSE();

        if (!corner_aux.is_visible)
            return false;

        i = corner_aux.board_coordinate.getX();
        j = corner_aux.board_coordinate.getY();

        if (!subMatrixCellChecker(i, j))
            return false;

        generalUpdateRoutine(i, j, this.board, card_to_play);
        this.updatePoints(card_to_play);

        return true;
    }

    /**
     * We assume that the game_card's corners have a specified board_coordinate,
     * It is utilized when placing a new GoldCard (@param card_to_play) onto a corner
     * of the personal board where a corner of the StarterCard already exists.
     *
     * @param game_card    is the card already on the PersonalBoard
     * @param card_to_play is the card to put on the PersonalBoard
     * ensures card_to_play is attached to another card, specifically in the SE corner
     * of the game_card
     */

    public boolean placeCardAtSE(StarterCard game_card, GoldCard card_to_play) {

        int i;
        int j;

        Corner corner_aux = game_card.getCornerAtSE();

        if (!corner_aux.is_visible)
            return false;

        if (card_to_play.getMushroomRequired() > this.num_mushrooms ||
                card_to_play.getLeafRequired() > this.num_leaves ||
                card_to_play.getButterflyRequired() > this.num_butterflies ||
                card_to_play.getWolfRequired() > this.num_wolves)
            return false;

        i = corner_aux.board_coordinate.getX();
        j = corner_aux.board_coordinate.getY();

        if (!subMatrixCellChecker(i, j))
            return false;

        generalUpdateRoutine(i, j, this.board, card_to_play);
        this.updatePoints(card_to_play);

        return true;
    }



    /**
     * We assume that the game_card's corners have a specified board_coordinate,
     *
     * @param game_card    is the card already on the PersonalBoard
     * @param card_to_play is the card to put on the PersonalBoard
     * ensures card_to_play is attached to another card, specifically in the SW corner
     * of the game_card
     */
    public boolean placeCardAtSW(ResourceCard game_card, ResourceCard card_to_play) {

        int i;
        int j;

        Corner corner_aux = game_card.getCornerAtSW();

        if (!corner_aux.is_visible)
            return false;

        i = corner_aux.board_coordinate.getX();
        j = corner_aux.board_coordinate.getY();

        if (!subMatrixCellChecker(i, j - 1))
            return false;

        generalUpdateRoutine(i , j - 1, this.board, card_to_play);
        this.updatePoints(card_to_play);

        return true;
    }


    /**
     * We assume that the game_card's corners have a specified board_coordinate,
     *
     * @param game_card    is the card already on the PersonalBoard
     * @param card_to_play is the card to put on the PersonalBoard
     * ensures card_to_play is attached to another card, specifically in the SW corner
     * of the game_card
     */
    public boolean placeCardAtSW(ResourceCard game_card, GoldCard card_to_play) {

        int i;
        int j;

        Corner corner_aux = game_card.getCornerAtSW();

        if (!corner_aux.is_visible)
            return false;

        if (card_to_play.getMushroomRequired() > this.num_mushrooms ||
                card_to_play.getLeafRequired() > this.num_leaves ||
                card_to_play.getButterflyRequired() > this.num_butterflies ||
                card_to_play.getWolfRequired() > this.num_wolves)
            return false;

        i = corner_aux.board_coordinate.getX();
        j = corner_aux.board_coordinate.getY();

        if (!subMatrixCellChecker(i, j - 1))
            return false;

        generalUpdateRoutine(i, j - 1, this.board, card_to_play);
        this.updatePoints(card_to_play);

        return true;
    }


    /**
     * overloading:
     *
     * We assume that the game_card's corners have a specified board_coordinate,
     * It is utilized when placing a new ResourceCard (@param card_to_play) onto a corner
     * of the personal board where a corner of the StarterCard already exists.
     *
     * @param game_card    is the card already on the PersonalBoard
     * @param card_to_play is the card to put on the PersonalBoard
     * ensures card_to_play is attached to another card, specifically in the SW corner
     * of the game_card
     */
    public boolean placeCardAtSW(StarterCard game_card, ResourceCard card_to_play) {

        int i;
        int j;

        Corner corner_aux = game_card.getCornerAtSW();

        if (!corner_aux.is_visible)
            return false;

        i = corner_aux.board_coordinate.getX();
        j = corner_aux.board_coordinate.getY();

        if (!subMatrixCellChecker(i, j - 1))
            return false;

        generalUpdateRoutine(i , j - 1, this.board, card_to_play);
        this.updatePoints(card_to_play);

        return true;
    }


    /**
     * overloading:
     *
     * We assume that the game_card's corners have a specified board_coordinate,
     * It is utilized when placing a new GoldCard (@param card_to_play) onto a corner
     * of the personal board where a corner of the StarterCard already exists.
     *
     * @param game_card    is the card already on the PersonalBoard
     * @param card_to_play is the card to put on the PersonalBoard
     * ensures card_to_play is attached to another card, specifically in the SW corner
     * of the game_card
     */
    public boolean placeCardAtSW(StarterCard game_card, GoldCard card_to_play) {

        int i;
        int j;

        Corner corner_aux = game_card.getCornerAtSW();

        if (!corner_aux.is_visible)
            return false;

        if (card_to_play.getMushroomRequired() > this.num_mushrooms ||
                card_to_play.getLeafRequired() > this.num_leaves ||
                card_to_play.getButterflyRequired() > this.num_butterflies ||
                card_to_play.getWolfRequired() > this.num_wolves)
            return false;

        i = corner_aux.board_coordinate.getX();
        j = corner_aux.board_coordinate.getY();

        if (!subMatrixCellChecker(i, j - 1))
            return false;

        generalUpdateRoutine(i, j - 1, this.board, card_to_play);
        this.updatePoints(card_to_play);

        return true;
    }

    /**
     * We assume that the game_card's corners have a specified board_coordinate,
     *
     * @param game_card    is the card already on the PersonalBoard
     * @param card_to_play is the card to put on the PersonalBoard
     * ensures card_to_play is attached to another card, specifically in the NW corner
     * of the game_card
     */

    public boolean placeCardAtNW(ResourceCard game_card, ResourceCard card_to_play) {

        int i;
        int j;

        Corner corner_aux = game_card.getCornerAtNW();

        if (!corner_aux.is_visible)
            return false;

        i = corner_aux.board_coordinate.getX();
        j = corner_aux.board_coordinate.getY();

        if (!subMatrixCellChecker(i - 1, j - 1))
            return false;

        generalUpdateRoutine(i - 1, j - 1, this.board, card_to_play);
        this.updatePoints(card_to_play);

        return true;
    }

    /**
     * We assume that the game_card's corners have a specified board_coordinate,
     *
     * @param game_card    is the card already on the PersonalBoard
     * @param card_to_play is the card to put on the PersonalBoard
     * ensures card_to_play is attached to another card, specifically in the NW corner
     * of the game_card
     */
    public boolean placeCardAtNW(ResourceCard game_card, GoldCard card_to_play) {

        int i;
        int j;

        Corner corner_aux = game_card.getCornerAtNW();

        if (!corner_aux.is_visible)
            return false;

        if (card_to_play.getMushroomRequired() > this.num_mushrooms ||
                card_to_play.getLeafRequired() > this.num_leaves ||
                card_to_play.getButterflyRequired() > this.num_butterflies ||
                card_to_play.getWolfRequired() > this.num_wolves)
            return false;

        i = corner_aux.board_coordinate.getX();
        j = corner_aux.board_coordinate.getY();

        if (!subMatrixCellChecker(i - 1, j - 1))
            return false;

        generalUpdateRoutine(i - 1, j - 1, this.board, card_to_play);
        this.updatePoints(card_to_play);

        return true;
    }

    /**
     * overloading:
     *
     * We assume that the game_card's corners have a specified board_coordinate,
     * It is utilized when placing a new ResourceCard (@param card_to_play) onto a corner
     * of the personal board where a corner of the StarterCard already exists.
     *
     * @param game_card    is the card already on the PersonalBoard
     * @param card_to_play is the card to put on the PersonalBoard
     * ensures card_to_play is attached to another card, specifically in the NW corner
     * of the game_card
     */
    public boolean placeCardAtNW(StarterCard game_card, ResourceCard card_to_play) {

        int i;
        int j;

        Corner corner_aux = game_card.getCornerAtNW();

        if (!corner_aux.is_visible)
            return false;

        i = corner_aux.board_coordinate.getX();
        j = corner_aux.board_coordinate.getY();

        if (!subMatrixCellChecker(i - 1, j - 1))
            return false;

        generalUpdateRoutine(i - 1, j - 1, this.board, card_to_play);
        this.updatePoints(card_to_play);

        return true;
    }


    /**
     * overloading:
     *
     * We assume that the game_card's corners have a specified board_coordinate,
     * It is utilized when placing a new GoldCard (@param card_to_play) onto a corner
     * of the personal board where a corner of the StarterCard already exists.
     *
     * @param game_card    is the card already on the PersonalBoard
     * @param card_to_play is the card to put on the PersonalBoard
     * ensures card_to_play is attached to another card, specifically in the NW corner
     * of the game_card
     */
    public boolean placeCardAtNW(StarterCard game_card, GoldCard card_to_play) {

        int i;
        int j;

        Corner corner_aux = game_card.getCornerAtNW();

        if (!corner_aux.is_visible)
            return false;

        if (card_to_play.getMushroomRequired() > this.num_mushrooms ||
                card_to_play.getLeafRequired() > this.num_leaves ||
                card_to_play.getButterflyRequired() > this.num_butterflies ||
                card_to_play.getWolfRequired() > this.num_wolves)
            return false;

        i = corner_aux.board_coordinate.getX();
        j = corner_aux.board_coordinate.getY();

        if (!subMatrixCellChecker(i - 1, j - 1))
            return false;

        generalUpdateRoutine(i - 1, j - 1, this.board, card_to_play);
        this.updatePoints(card_to_play);

        return true;
    }

    /**
     * Place a card with known coordinates
     * @param game_card is the card already on the PersonalBoard
     * @param card_to_play is the card to put on the PersonalBoard
     * @param coord is the coordinate of the card_to_play
     * @return true iff the card can be placed
     */
    public boolean placeCardAt(ResourceCard game_card, ResourceCard card_to_play, Coordinate coord) {

        if (coord == Coordinate.NE)
            return placeCardAtNE(game_card, card_to_play);
        else if (coord == Coordinate.SE)
            return placeCardAtSE(game_card, card_to_play);
        else if (coord == Coordinate.SW)
            return placeCardAtSW(game_card, card_to_play);
        else if (coord == Coordinate.NW)
            return placeCardAtNW(game_card, card_to_play);
        else
            return false;

    }

    /**
     * Place a card with known coordinates
     * @param game_card is the card already on the PersonalBoard
     * @param card_to_play is the card to put on the PersonalBoard
     * @param coord is the coordinate of the card_to_play
     * @return true iff the card can be placed
     */
    public boolean placeCardAt(ResourceCard game_card, GoldCard card_to_play, Coordinate coord) {

        if (coord == Coordinate.NE)
            return placeCardAtNE(game_card, card_to_play);
        else if (coord == Coordinate.SE)
            return placeCardAtSE(game_card, card_to_play);
        else if (coord == Coordinate.SW)
            return placeCardAtSW(game_card, card_to_play);
        else if (coord == Coordinate.NW)
            return placeCardAtNW(game_card, card_to_play);
        else
            return false;
    }

    /**
     * Place a card with known coordinates
     * @param game_card is the card already on the PersonalBoard
     * @param card_to_play is the card to put on the PersonalBoard
     * @param coord is the coordinate of the card_to_play
     * @return true iff the card can be placed
     */
    public boolean placeCardAt(StarterCard game_card, ResourceCard card_to_play, Coordinate coord) {

        if (coord == Coordinate.NE)
            return placeCardAtNE(game_card, card_to_play);
        else if (coord == Coordinate.SE)
            return placeCardAtSE(game_card, card_to_play);
        else if (coord == Coordinate.SW)
            return placeCardAtSW(game_card, card_to_play);
        else if (coord == Coordinate.NW)
            return placeCardAtNW(game_card, card_to_play);
        else
            return false;
    }

    /**
     * Place a card with known coordinates
     * @param game_card is the card already on the PersonalBoard
     * @param card_to_play is the card to put on the PersonalBoard
     * @param coord is the coordinate of the card_to_play
     * @return true iff the card can be placed
     */
    public boolean placeCardAt(StarterCard game_card, GoldCard card_to_play, Coordinate coord) {

        if (coord == Coordinate.NE)
            return placeCardAtNE(game_card, card_to_play);
        else if (coord == Coordinate.SE)
            return placeCardAtSE(game_card, card_to_play);
        else if (coord == Coordinate.SW)
            return placeCardAtSW(game_card, card_to_play);
        else if (coord == Coordinate.NW)
            return placeCardAtNW(game_card, card_to_play);
        else
            return false;
    }

    /**
     * Performs a general update routine on the player's board based on the placement of a resource card.
     * It updates the board cells and coordinates, adds or removes resources/items as necessary,
     * and handles the orientation of the resource card.
     *
     * @param i           The row index of the starting position on the board for placing the resource card.
     * @param j           The column index of the starting position on the board for placing the resource card.
     * @param board       The 2D array representing the player's board.
     * @param card_to_play The resource card to be placed on the board.
     */
    public void generalUpdateRoutine(int i, int j, Cell[][] board, ResourceCard card_to_play) {

        for (int k = 0; k < 2; k++) {
            for (int h = 0; h < 2; h++) {

                if (board[i + k][j + h].is_full) {
                    Resource res1 = board[i + k][j + h].getCornerFromCell().resource;
                    Item it1 = board[i + k][j + h].getCornerFromCell().item;
                    removeResource(res1);
                    removeItem(it1);
                }

                // Effective cell-setter
                board[i + k][j + h].setCellAsFull(card_to_play.getCornerAt(k, h));
                // Effective coordinate-setter
                board[i + k][j + h].getCornerFromCell().board_coordinate.setXY(i + k, j + h);

                if (card_to_play.orientation == Orientation.FRONT) {
                    Resource res2 = board[i + k][j + h].getCornerFromCell().resource;
                    Item it2 = board[i + k][j + h].getCornerFromCell().item;
                    addResource(res2);
                    addItem(it2);
                }
            }
        }

        if (card_to_play.orientation == Orientation.BACK)
            addFixedResource(card_to_play.color);
    }

    /**
     * Retrieves the player's board.
     */
    public Cell[][] getBoard() {
        return board;
    }

    /**
     * equals override
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonalBoard)) return false;
        PersonalBoard that = (PersonalBoard) o;
        return num_mushrooms == that.num_mushrooms &&
                num_leaves == that.num_leaves &&
                num_butterflies == that.num_butterflies &&
                num_wolves == that.num_wolves &&
                num_parchments == that.num_parchments &&
                num_feathers == that.num_feathers &&
                num_potions == that.num_potions &&
                points == that.points &&
                dim1 == that.dim1 &&
                dim2 == that.dim2 &&
                Arrays.equals(board, that.board);
    }
}
