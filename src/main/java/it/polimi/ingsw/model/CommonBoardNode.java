package src.main.java.it.polimi.ingsw.model;

class CommonBoardNode {
    private final int nodeNumber; // Number of the node
    private final boolean[] players; // Array of booleans to indicate player presence

    public CommonBoardNode(int nodeNumber) {
        this.nodeNumber = nodeNumber;
        players = new boolean[4]; // 4 players
    }

    // Method to set player presence in this node
    public void setPlayer(int playerIndex, boolean present) {
        if (playerIndex >= 0 && playerIndex < players.length) {
            players[playerIndex] = present;
        }
    }

    // Method to check if a player is present in this node
    public boolean isPlayerPresent(int playerIndex) {
        if (playerIndex >= 0 && playerIndex < players.length) {
            return players[playerIndex];
        }
        return false;
    }


    // Method to get the node number
    public int getNodeNumber() {
        return nodeNumber;
    }
}
