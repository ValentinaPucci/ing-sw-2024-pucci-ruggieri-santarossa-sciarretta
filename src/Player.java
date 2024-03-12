package src;

import java.util.List;

public class Player {
    private String id;
    private List<Card> cardHand; // Assuming Card is a class defined elsewhere
    private PersonalBoard personalBoard; // Assuming PersonalBoard is a class defined elsewhere
    private List<ObjectiveCard> secretObjectives; // Assuming ObjectiveCard is a class defined elsewhere
    private StarterCard starterCard; // Assuming StarterCard is a class defined elsewhere

    public Player(String id) {
        this.id = id;
        // Initialize other fields as necessary
    }

    public void chooseGoalCard() {
        // Implementation
    }

    public void playBeginningCard() {
        // Implementation
    }

    public void playGameCard() {
        // Implementation
    }

    public void chooseGameCard() {
        // Implementation
    }

    public void takeTurn() {
        // Implementation
    }

    // Additional getter and setter methods as needed
}

