package it.polimi.demo.view.dynamic.utilities;

import it.polimi.demo.model.Player;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.view.dynamic.GameDynamic;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * QueueParser is a thread that continuously processes input commands from a queue.
 */
public class QueueParser extends Thread {

    private final LinkedBlockingQueue<String> inputQueue;
    private final LinkedBlockingQueue<String> processedDataQueue;
    private final GameDynamic gameDynamic;
    private volatile Player currentPlayer = null;
    private volatile Integer currentGameId = null;

    private static final Pattern PRIVATE_MESSAGE_PATTERN = Pattern.compile("^/cs\\s+(\\S+)\\s+(.+)$");
    private static final Pattern PUBLIC_MESSAGE_PATTERN = Pattern.compile("^/c\\s+(.+)$");

    /**
     * Constructs a QueueParser instance.
     *
     * @param inputQueue the queue containing input commands
     * @param gameDynamic the game dynamic instance to interact with
     */
    public QueueParser(LinkedBlockingQueue<String> inputQueue, GameDynamic gameDynamic) {
        this.inputQueue = inputQueue;
        this.processedDataQueue = new LinkedBlockingQueue<>();
        this.gameDynamic = gameDynamic;
        this.start();
    }

    /**
     * Binds the player to the live buffer.
     * @param id game id
     * @param player the player to bind
     */
    public void bindPlayerToParser(Integer id, Player player) {
        this.currentGameId = id;
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

    /**
     * The main run method that continuously processes input from the queue.
     */
    @Override
    public void run() {
        try {
            while (!this.isInterrupted()) {
                String inputCommand = inputQueue.take();
                processInput(inputCommand);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
        }
    }

    /**
     * Processes the input command by finding and executing the appropriate handler.
     *
     * @param command the input command to process
     */
    private void processInput(String command) {
        if (command == null || command.isEmpty()) {
            return;
        }

        if (command.startsWith("/cs")) {
            handlePrivateMessage(command);
        } else if (command.startsWith("/c")) {
            handlePublicMessage(command);
        } else {
            switch (command) {
                case "/pb0":
                    showOthersPersonalBoard(0);
                    break;
                case "/pb1":
                    showOthersPersonalBoard(1);
                    break;
                case "/pb2":
                    showOthersPersonalBoard(2);
                    break;
                case "/pb3":
                    showOthersPersonalBoard(3);
                    break;
                case "/leave", "/quit", "/exit":
                    handleQuit();
                    break;
                default:
                    processedDataQueue.add(command);
                    break;
            }
        }
    }

    /**
     * Shows the personal board of the player with the given index.
     * @param playerIndex the index of the player whose personal board to show
     */
    private void showOthersPersonalBoard(int playerIndex) {
        if (currentPlayer != null) {
            gameDynamic.showOthersPersonalBoard(playerIndex);
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
            gameDynamic.sendMessage(receiver, new Message(messageContent, currentPlayer));
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
            gameDynamic.sendMessage("Everyone", new Message(messageContent, currentPlayer));
        } else {
            System.err.println("Public message command format is incorrect: " + command);
        }
    }

    /**
     * Handles the quit command by making the player leave the game and exiting the system.
     */
    private void handleQuit() {
        if (currentPlayer != null) {
            gameDynamic.leave(currentPlayer.getNickname(), currentGameId);
            gameDynamic.youLeft();
        }
        System.exit(1);
    }
}





