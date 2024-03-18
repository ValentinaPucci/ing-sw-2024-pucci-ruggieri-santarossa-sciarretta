package src.main.java.it.polimi.ingsw.model;

public class PersonalBoard {
    private Cell[][] board;

    private int dim1;
    private int dim2;
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
     * @param points_of_placed_card
     */
    public void updatePoints(int points_of_placed_card) {
        //Se piazzo carta oro che mi fa guadagnare punti, ma solo se rispetta i vincoli correttamente.
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
        return dim1;
    }

    public int getDim2() {
        return dim2;
    }

    /**
     * @param i
     * @param j
     * @return true iff the planed move is doable
     */
    public boolean subMatrixCellChecker(int i, int j) {

        for (int k = 0; k < 2; k++) {
            for (int h = 0; h < 2; h++) {
                if (board[i + k][j + h].is_full) {
                    if (!board[i + k][j + h].getCornerFromCell().is_visible) {
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

        Corner corner2 = game_card.getCornerAtNE();

        if (!corner2.is_visible)
            throw new IllegalMoveException();

        i = corner2.board_coordinate.getX();
        j = corner2.board_coordinate.getY();

        /**
         * i + 1 is a technicality: the checker starts always
         * form the position (0, 0) of the submatrix 2 x 2 it is
         * analyzing.
         */
        if (!subMatrixCellChecker(i + 1, j))
            throw new IllegalMoveException();

        /**
         * we can forget to take **directly** track of the items or
         * the resources which are currently on the board at a certain position,
         * because we know every corner in a given cell. Hence, we know the resources
         * and/or the items in every cell.
         */
        for (int k = 1; k >= 0; k--) {
            for (int h = 0; h < 2; h++) {
                this.board[i + k][j + h].setCellAsFull(card_to_play.getCornerAt(k, h));
            }
        }
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

        Corner corner2 = game_card.getCornerAtSE();

        if (!corner2.is_visible)
            throw new IllegalMoveException();

        i = corner2.board_coordinate.getX();
        j = corner2.board_coordinate.getY();

        if (!subMatrixCellChecker(i, j))
            throw new IllegalMoveException();

        for (int k = 0; k < 2; k++) {
            for (int h = 0; h < 2; h++) {
                this.board[i + k][j + h].setCellAsFull(card_to_play.getCornerAt(k, h));
            }
        }
    }

    /**
     * @param game_card    is the card already on the PersonalBoard
     * @param card_to_play is the card to put on the PersonalBoard
     * @ensures card_to_play is attached to another card, specifically in the SO corner
     * of the game_card
     */
    public void placeCardAtSO(ResourceCard game_card, ResourceCard card_to_play)
            throws IllegalMoveException {

        int i;
        int j;

        Corner corner2 = game_card.getCornerAtSO();

        if (!corner2.is_visible)
            throw new IllegalMoveException();

        i = corner2.board_coordinate.getX();
        j = corner2.board_coordinate.getY();

        if (!subMatrixCellChecker(i, j - 1))
            throw new IllegalMoveException();

        for (int k = 0; k < 2; k++) {
            for (int h = 1; h >= 0; h--) {
                this.board[i + k][j + h].setCellAsFull(card_to_play.getCornerAt(k, h));
            }
        }
    }

    /**
     * @param game_card    is the card already on the PersonalBoard
     * @param card_to_play is the card to put on the PersonalBoard
     * @ensures card_to_play is attached to another card, specifically in the NO corner
     * of the game_card
     */
    public void placeCardAtNO(ResourceCard game_card, ResourceCard card_to_play)
            throws IllegalMoveException {

        int i;
        int j;

        Corner corner2 = game_card.getCornerAtNO();

        if (!corner2.is_visible)
            throw new IllegalMoveException();

        i = corner2.board_coordinate.getX();
        j = corner2.board_coordinate.getY();

        if (!subMatrixCellChecker(i + 1, j - 1))
            throw new IllegalMoveException();

        for (int k = 1; k >= 0; k--) {
            for (int h = 1; h >= 0; h--) {
                this.board[i + k][j + h].setCellAsFull(card_to_play.getCornerAt(k, h));
            }
        }
    }

    /**
     * These methods implement the score counting for the objective cards.
     */

    /**
     * @param objectiveCard
     * @param l
     * @param m
     * @return true iff we recognised a diagonal pattern
     */
    public boolean isSubMatrixDiagonalPattern(DiagonalPatternObjectiveCard objectiveCard, int l, int m) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!board[l + i][m + j].equals(objectiveCard.aux_personal_board.board[i][j])
                        && objectiveCard.aux_personal_board.board[i][j].is_full) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param objectiveCard
     * @return
     */
    public int counterOfRecognisedDiagonalPatterns(DiagonalPatternObjectiveCard objectiveCard) {

        int count = 0;
        for (int i = 0; i <= this.getDim1() - 4; i++) {
            for (int j = 0; j <= this.getDim2() - 4; j++) {
                if (isSubMatrixDiagonalPattern(objectiveCard, i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * @param objectiveCard
     * @param l
     * @param m
     * @return true iff we recognised a letter pattern
     */
    public boolean isSubMatrixLetterPattern(LetterPatternObjectiveCard objectiveCard, int l, int m) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                if (!board[l + i][m + j].equals(objectiveCard.aux_personal_board.board[i][j])
                        && objectiveCard.aux_personal_board.board[i][j].is_full) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param objectiveCard
     * @return
     */
    public int counterOfRecognisedLetterPatterns(DiagonalPatternObjectiveCard objectiveCard) {

        int count = 0;
        for (int i = 0; i <= this.getDim1() - 5; i++) {
            for (int j = 0; j <= this.getDim2() - 3; j++) {
                if (isSubMatrixDiagonalPattern(objectiveCard, i, j)) {
                    count++;
                }
            }
        }
        return count;
    }




}
