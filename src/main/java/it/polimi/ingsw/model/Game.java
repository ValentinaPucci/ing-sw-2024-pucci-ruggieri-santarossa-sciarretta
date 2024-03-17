package src;

import src.Player;

import java.util.List;

public class PlayersManager {
    private List<Player> players;
    private int currentPlayerIndex;
    private Player firstPlayer;
    private boolean gameEnded;

    public PlayersManager() {
        // Initialize the src.PlayersManager
    }

    public void addPlayer(Player player) {
        // Add a player to the list
    }

    public void nextPlayer() {
        // Move to the next player
    }

    public void dealCards() {
        // Deal cards to players
    }

    public void initializeGame() {
        // Game initialization logic
    }

    public void checkForEndGame() {
        // Check if the game has ended
    }

    public void calculateFinalScores() {
        // Calculate final scores
    }

    public int getCurrentPlayer() {
        return currentPlayerIndex;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }


    // Additional methods and logic as needed
}

