package it.polimi.demo.model.board;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Class to represent a node of the common board
 */
public class CommonBoardNode implements Serializable {

    @Serial
    private static final long serialVersionUID = 2331862302425243237L;
    private int node_number; // Number of the node
    private boolean[] players; // Array of booleans to indicate player presence

    /**
     * Constructor for the class CommonBoard
     * @param nodeNumber the number of the node
     */
    public CommonBoardNode(int nodeNumber) {
        this.node_number = nodeNumber;
        players = new boolean[4]; // 4 players
        Arrays.fill(players, false);
    }

    /**
     * Method to set the player presence in this node
     * @param playerIndex the index of the player
     * @param present true if the player is present, false otherwise
     */
    public void setPlayer(int playerIndex, boolean present) {
        if (playerIndex >= 0 && playerIndex < players.length) {
            players[playerIndex] = present;
        }
    }

    /**
     * Method to check if a player is present in this node
     * @param playerIndex the index of the player
     * @return true if the player is present, false otherwise
     */
    public boolean isPlayerPresent(int playerIndex) {
        if (playerIndex >= 0 && playerIndex < players.length) {
            return players[playerIndex];
        }
        return false;
    }

    /**
     * Method to get the number of the node
     * @return the number of the node
     */
    public int getNodeNumber() {
        return node_number;
    }

    /**
     * Method to get the players present in this node
     * @return an array of booleans indicating the players present
     */
    public boolean[] getPlayers(){
        return players;
    }

}
