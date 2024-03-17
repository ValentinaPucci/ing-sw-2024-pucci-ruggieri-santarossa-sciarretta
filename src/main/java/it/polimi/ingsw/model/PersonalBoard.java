package src.main.java.it.polimi.ingsw.model;

public class PersonalBoard {
    private Cell[][] board;
    private int points;
    private int delta_points;
    private int num_mushrooms;
    private int num_leaves;
    private int num_butterflies;
    private int num_wolves;
    private int num_parchments;
    private int num_feathers;
    private int num_potions;

    // Quando creo Personal Board è vuota, poi aggiorno quando piazzo la carta iniziale.
    public PersonalBoard() {
        //ipotesi dimensione matrice:
        this.board = new Cell[1001][1001];
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
     *
     * @param mushrooms_placed
     */
    public void updateMushrooms(int mushrooms_placed){
        this.num_mushrooms += mushrooms_placed;
    }

    /**
     *
     * @param leaves_placed
     */
    public void updateLeaves(int leaves_placed){
        this.num_leaves += leaves_placed;
    }

    /**
     *
     * @param butterflies_placed
     */
    public void updateButterflies(int butterflies_placed){
        this.num_butterflies += butterflies_placed;
    }

    /**
     *
     * @param wolves_placed
     */
    public void updateWolves(int wolves_placed){
        this.num_wolves += wolves_placed;
    }

    /**
     *
     * @param parchments_placed
     */
    public void updateParchments(int parchments_placed){
        this.num_parchments += parchments_placed;
    }

    /**
     *
     * @param feathers_placed
     */
    public void updateFeathers(int feathers_placed){
        this.num_feathers += feathers_placed;
    }

    /**
     *
     * @param potions_placed
     */
    public void updatePotions(int potions_placed){
        this.num_potions += potions_placed;
    }
    //Sarà necesario anche aggiornare i punti, ma servono i controlli sulla carta da piazzare.

    /**
     *
     * @param points_of_placed_card
     */
    public void updatePoints(int points_of_placed_card){
        //Se piazzo carta oro che mi fa guadagnare punti, ma solo se rispetta i vincoli correttamente.
        this.points += points_of_placed_card;
        this.delta_points= delta_points - points_of_placed_card;
    }

    public int getNum_mushrooms(){
        return num_mushrooms;
    }
    public int getNum_leaves() { return num_leaves; }
    public int getNum_butterflies() { return num_butterflies; }
    public int getNum_wolves() { return num_wolves; }
    public int getNum_parchments() { return num_parchments; }
    public int getNum_feathers() { return num_feathers; }
    public int getNum_potions() { return num_potions; }
    public int getPoints() { return points; }

    /**
     * Here target_i and target_j stand for the 'first' coordinate
     * in the matrix, namely the upper-leftmost coordinate.
     * @param card
     * @param target_i
     * @param target_j
     */
    public void cardOnBoard(Card card, int target_i, int target_j) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {

            }
        }
    }

    public void placeCardOnBoard(Car)

}