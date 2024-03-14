package src.main.java.it.polimi.ingsw.model;

public class PersonalBoard {
    private Card[][] board;
    private int points;
    private int delta_points;
    private int num_mushrooms;
    private int num_leaves;
    private int num_butterflies;
    private int num_wolves;
    private int num_parchments;
    private int num_feathers;
    private int num_potions;

    //Quando creo Personal Board è vuota, poi aggiorno quando piazzo la carta iniziale.
    public PersonalBoard() {
        //ipotesi dimensione matrice:
        this.board = new Card[156][156];
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


}