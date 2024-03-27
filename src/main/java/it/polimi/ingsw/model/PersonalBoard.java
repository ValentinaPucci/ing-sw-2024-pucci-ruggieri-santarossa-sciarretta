package it.polimi.ingsw.model;

public class PersonalBoard {
    public Cell[][] board;

    private final int dim1;
    private final int dim2;
    private int points;
    private int delta_points;
    private int num_mushrooms;
    private int num_leaves;
    private int num_butterflies;
    private int num_wolves;
    private int num_parchments;
    private int num_feathers;
    private int num_potions;

    public PersonalBoard() {
        this.dim1 = 1005;
        this.dim2 = 1005;

        this.board = new Cell[dim1][dim2];
        for (int i = 0; i < dim1; i++) {
            for (int j = 0; j < dim2; j++) {
                this.board[i][j] = new Cell();
            }
        }

        this.points = 0;
        this.delta_points = 20;
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
        this.delta_points = 20;
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
        this.delta_points = 20;
        this.num_mushrooms = 0;
        this.num_leaves = 0;
        this.num_butterflies = 0;
        this.num_wolves = 0;
        this.num_parchments = 0;
        this.num_feathers = 0;
        this.num_potions = 0;
    }

    public int getDeltaPoints() {
        return delta_points;
    } //Used in Game

    /**
     * @param mushrooms_placed
     */
    public void updateMushrooms(int mushrooms_placed) {
        this.num_mushrooms += mushrooms_placed;
    }

    /**
     * @param leaves_placed
     */
    public void updateLeaves(int leaves_placed) {
        this.num_leaves += leaves_placed;
    }

    /**
     * @param butterflies_placed
     */
    public void updateButterflies(int butterflies_placed) {
        this.num_butterflies += butterflies_placed;
    }

    /**
     * @param wolves_placed
     */
    public void updateWolves(int wolves_placed) {
        this.num_wolves += wolves_placed;
    }

    /**
     * @param parchments_placed
     */
    public void updateParchments(int parchments_placed) {
        this.num_parchments += parchments_placed;
    }

    /**
     * @param feathers_placed
     */
    public void updateFeathers(int feathers_placed) {
        this.num_feathers += feathers_placed;
    }

    /**
     * @param potions_placed
     */
    public void updatePotions(int potions_placed) {
        this.num_potions += potions_placed;
    }
    // Sarà necesario anche aggiornare i punti, ma servono i controlli sulla carta da piazzare.

    /**
     * Note that this method has to be overriden by the sublcasses
     *
     * @param points_of_placed_card
     */
    public void updatePoints(int points_of_placed_card) {
        this.points += points_of_placed_card;
        this.delta_points = delta_points - points_of_placed_card;
    }

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

    public int getDim1() {
        return this.dim1;
    }

    public int getDim2() {
        return this.dim2;
    }

    public void addResource(Resource resource) {
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

    public void removeResource(Resource resource) {
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

    public void addItem(Item item) {
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

    public void removeItem(Item item) {
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
     * without doing any check. It is used for the placement of the first
     * card or in other marginal situations, like for the construction of
     * sub-matrices of personalBoard in other classes. SE because we use
     * a classic for loop with increasing indexes both for i and j.
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


    /**
     * We assume that the game_card's corners have a specified board_coordinate,
     *
     * @param game_card    is the card already on the PersonalBoard
     * @param card_to_play is the card to put on the PersonalBoard
     * @ensures card_to_play is attached to another card, specifically in the NE corner
     * of the game_card
     */
    public void placeCardAtNE(ResourceCard game_card, ResourceCard card_to_play)
            throws IllegalMoveException {

        /**
         * The following attributes are the starting point in the grid
         * to perform the 'allocation' (namely, the placement) of the
         * card_to_play on the PersonalBoard.
         */
        int i;
        int j;

        Corner corner_aux = game_card.getCornerAtNE();

        if (!corner_aux.is_visible)
            throw new IllegalMoveException();

        i = corner_aux.board_coordinate.getX();
        j = corner_aux.board_coordinate.getY();

        /**
         * i + 1 is a technicality: the checker starts always
         * form the position (0, 0) of the submatrix 2 x 2 it is
         * analyzing.
         */
        if (!subMatrixCellChecker(i - 1, j))
            throw new IllegalMoveException();

        /**
         * we can forget to take **directly** track of the items or
         * the resources which are currently on the board at a certain position,
         * because we know every corner in a given cell. Hence, we know the resources
         * and/or the items in every cell.
         */
        generalUpdateRoutine(i - 1, j, this.board, card_to_play);
    }

    /**
     * @param game_card    is the card already on the PersonalBoard
     * @param card_to_play is the card to put on the PersonalBoard
     * @ensures card_to_play is attached to another card, specifically in the SE corner
     * of the game_card
     */
    public void placeCardAtSE(ResourceCard game_card, ResourceCard card_to_play)
            throws IllegalMoveException {

        int i;
        int j;

        Corner corner_aux = game_card.getCornerAtSE();

        if (!corner_aux.is_visible)
            throw new IllegalMoveException();

        i = corner_aux.board_coordinate.getX();
        j = corner_aux.board_coordinate.getY();

        if (!subMatrixCellChecker(i, j))
            throw new IllegalMoveException();

        generalUpdateRoutine(i, j, this.board, card_to_play);
    }

    /**
     * @param game_card    is the card already on the PersonalBoard
     * @param card_to_play is the card to put on the PersonalBoard
     * @ensures card_to_play is attached to another card, specifically in the SO corner
     * of the game_card
     */
    public void placeCardAtSW(ResourceCard game_card, ResourceCard card_to_play)
            throws IllegalMoveException {

        int i;
        int j;

        Corner corner_aux = game_card.getCornerAtSW();

        if (!corner_aux.is_visible)
            throw new IllegalMoveException();

        i = corner_aux.board_coordinate.getX();
        j = corner_aux.board_coordinate.getY();

        if (!subMatrixCellChecker(i, j - 1))
            throw new IllegalMoveException();

        generalUpdateRoutine(i , j - 1, this.board, card_to_play);
    }

    /**
     * @param game_card    is the card already on the PersonalBoard
     * @param card_to_play is the card to put on the PersonalBoard
     * @ensures card_to_play is attached to another card, specifically in the NO corner
     * of the game_card
     */
    public void placeCardAtNW(ResourceCard game_card, ResourceCard card_to_play)
            throws IllegalMoveException {

        int i;
        int j;

        Corner corner_aux = game_card.getCornerAtNW();

        if (!corner_aux.is_visible)
            throw new IllegalMoveException();

        i = corner_aux.board_coordinate.getX();
        j = corner_aux.board_coordinate.getY();

        if (!subMatrixCellChecker(i - 1, j - 1))
            throw new IllegalMoveException();

        generalUpdateRoutine(i - 1, j - 1, this.board, card_to_play);
    }

    public void generalUpdateRoutine(int i, int j, Cell[][] board, ResourceCard card_to_play) {

        for (int k = 0; k < 2; k++) {
            for (int h = 0; h < 2; h++) {

                if (board[i + k][j + h].is_full) {
                    board[i + k][j + h].getCornerFromCell().resource.ifPresent(res -> this.removeResource(res));
                    board[i + k][j + h].getCornerFromCell().item.ifPresent(it -> this.removeItem(it));
                }

                // Effective cell-setter
                board[i + k][j + h].setCellAsFull(card_to_play.getCornerAt(k, h));
                // Effective coordinate-setter
                board[i + k][j + h].getCornerFromCell().board_coordinate.setXY(i + k, j + h);

                if (card_to_play.orientation == Orientation.FRONT) {
                    board[i + k][j + h].getCornerFromCell().resource.ifPresent(res -> this.addResource(res));
                    board[i + k][j + h].getCornerFromCell().item.ifPresent(it -> this.addItem(it));
                }
            }
        }

        if (card_to_play.orientation == Orientation.BACK)
            addFixedResource(card_to_play.color);
        this.updatePoints(card_to_play.points);
    }
}
