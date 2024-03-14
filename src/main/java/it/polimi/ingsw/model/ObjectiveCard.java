package src.main.java.it.polimi.ingsw.model;

public class ObjectiveCard extends Card {
    private int points; //every objective card has it.
    private Optional<String> color; //che in realtà corrisponde univocamente ad una risorsa.
    private int num_parchments;
    private int num_feathers;
    private int num_potions;
    private int num_mushrooms;
    private int num_leaves;
    private int num_butterflies;
    private int num_wolves;
    // Third type of objective card: the one with the layout, NB: to get points you need to respect both the layout and the color
    // specified in the objective card.
    private Optional<String> layout; //che corrisponde allo schema indicato dalla carta obiettivo


}
