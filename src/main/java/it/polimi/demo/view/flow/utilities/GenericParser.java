package it.polimi.demo.view.flow.utilities;

import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.interfaces.PlayerIC;
import it.polimi.demo.view.flow.GameDynamics;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A thread class that processes input commands from a queue and interacts with the game flow.
 */
// todo: checked
public class GenericParser extends Thread {

    private final LinkedBlockingQueue<String> inputQueue;
    private final LinkedBlockingQueue<String> processedDataQueue;
    private final GameDynamics gameDynamics;
    private PlayerIC currentPlayer = null;
    private Integer currentGameId = null;

    // Updated regex patterns to ensure proper matching
    private static final Pattern PRIVATE_MESSAGE_PATTERN = Pattern.compile("^/cs\\s+(\\S+)\\s+(.+)$");
    private static final Pattern PUBLIC_MESSAGE_PATTERN = Pattern.compile("^/c\\s+(.+)$");

    /**
     * Constructs a GenericParser instance.
     *
     * @param inputQueue the queue containing input commands
     * @param gameDynamics   the game flow instance to interact with
     */
    public GenericParser(LinkedBlockingQueue<String> inputQueue, GameDynamics gameDynamics) {
        this.inputQueue = inputQueue;
        this.processedDataQueue = new LinkedBlockingQueue<>();
        this.gameDynamics = gameDynamics;
        this.start();
    }

    /**
     * The main run method that continuously processes input from the queue.
     */
    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                String inputCommand = inputQueue.take();
                processInput(inputCommand);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
                return; // Exit the loop and end the thread
            }
        }
    }

    /**
     * Processes the input command by finding and executing the appropriate handler.
     *
     * @param s the input command to process
     */
    private void processInput(String s) {
        if (s.startsWith("/cs")) {
            handlePrivateMessage(s);
        } else if (s.startsWith("/c")) {
            handlePublicMessage(s);
        } else if (s.startsWith("/quit") || s.startsWith("/leave")) {
            handleQuit();
        } else {
            processedDataQueue.add(s);
        }
    }

    /**
     * Handles the private message command.
     *
     * @param command the command string
     */
    private void handlePrivateMessage(String command) {
        if (currentPlayer == null) {
            return;
        }
        Matcher matcher = PRIVATE_MESSAGE_PATTERN.matcher(command);
        if (matcher.matches()) {
            String receiver = matcher.group(1);
            String messageContent = matcher.group(2);
            gameDynamics.sendMessage(receiver, new Message(messageContent, currentPlayer));
        } else {
            System.err.println("Private message command format is incorrect: " + command);
        }
    }

    /**
     * Handles the public message command.
     *
     * @param command the command string
     */
    private void handlePublicMessage(String command) {
        if (currentPlayer == null) {
            return;
        }
        Matcher matcher = PUBLIC_MESSAGE_PATTERN.matcher(command);
        if (matcher.matches()) {
            String messageContent = matcher.group(1);
            gameDynamics.sendMessage("all", new Message(messageContent, currentPlayer));
        } else {
            System.err.println("Public message command format is incorrect: " + command);
        }
    }

    /**
     * Handles the quit command by making the player leave the game and exiting the system.
     */
    private void handleQuit() {
        if (currentPlayer != null) {
            gameDynamics.leave(currentPlayer.getNickname(), currentGameId);
            gameDynamics.youLeft();
        }
        System.exit(1);
    }

    /**
     * Sets the current game ID.
     *
     * @param gameId the game ID to set
     */
    public void setGameId(Integer gameId) {
        this.currentGameId = gameId;
    }

    /**
     * Sets the current player.
     *
     * @param player the player to set
     */
    public void setPlayer(PlayerIC player) {
        this.currentPlayer = player;
    }

    /**
     * Gets the queue of processed data.
     *
     * @return the queue of processed data
     */
    public LinkedBlockingQueue<String> getProcessedDataQueue() {
        return processedDataQueue;
    }
}




