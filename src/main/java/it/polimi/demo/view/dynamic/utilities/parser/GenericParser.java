package it.polimi.demo.view.dynamic.utilities.parser;

import it.polimi.demo.model.Player;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.view.dynamic.GameDynamic;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenericParser extends Thread {

    private final LinkedBlockingQueue<String> input_queue;
    private final LinkedBlockingQueue<String> processed_data_queue;
    private final GameDynamic game_dyn;
    private Player current_player = null;
    private Integer current_game_id = null;

    // Updated regex patterns to ensure proper matching
    private static final Pattern PRIVATE_MESSAGE_PATTERN = Pattern.compile("^/cs\\s+(\\S+)\\s+(.+)$");
    private static final Pattern PUBLIC_MESSAGE_PATTERN = Pattern.compile("^/c\\s+(.+)$");

    /**
     * Constructs a GenericParser instance.
     *
     * @param input_queue the queue containing input commands
     * @param game_dyn   the game dynamic instance to interact with
     */
    public GenericParser(LinkedBlockingQueue<String> input_queue, GameDynamic game_dyn) {
        this.input_queue = input_queue;
        this.processed_data_queue = new LinkedBlockingQueue<>();
        this.game_dyn = game_dyn;
        this.start();
    }

    /**
     * binds the player to parser
     * @param id game id
     * @param p the player to bind
     */
    public void bindPlayerToParser(Integer id, Player p) {
        current_game_id = id;
        current_player = p;
    }

    /**
     * Gets the queue of processed data.
     *
     * @return the queue of processed data
     */
    public LinkedBlockingQueue<String> getProcessed_data_queue() {
        return processed_data_queue;
    }

    /**
     * The main run method that continuously processes input from the queue.
     */
    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                String inputCommand = input_queue.take();
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
        } else if (s.startsWith("/pb0")) {
            showOthersPersonalBoard(0);
        } else if (s.startsWith("/pb1")) {
            showOthersPersonalBoard(1);
        } else if (s.startsWith("/pb2")) {
            showOthersPersonalBoard(2);
        } else if (s.startsWith("/pb3")) {
            showOthersPersonalBoard(3);
        } else if (s.startsWith("/quit") || s.startsWith("/leave")) {
            handleQuit();
        } else {
            processed_data_queue.add(s);
        }
    }

    /**
     * Shows the personal board of the player with the given index.
     * @param player_index the index of the player whose personal board to show
     */
    private void showOthersPersonalBoard(int player_index) {
        if (current_player == null) {
            return;
        }
        game_dyn.showOthersPersonalBoard(player_index);
    }

    /**
     * Handles the private message command.
     *
     * @param command the command string
     */
    private void handlePrivateMessage(String command) {
        if (current_player == null) {
            return;
        }
        Matcher matcher = PRIVATE_MESSAGE_PATTERN.matcher(command);
        if (matcher.matches()) {
            String receiver = matcher.group(1);
            String messageContent = matcher.group(2);
            game_dyn.sendMessage(receiver, new Message(messageContent, current_player));
        }
        else {
            System.err.println("Private message command format is incorrect: " + command);
        }
    }

    /**
     * Handles the public message command.
     *
     * @param command the command string
     */
    private void handlePublicMessage(String command) {
        if (current_player == null) {
            return;
        }
        Matcher matcher = PUBLIC_MESSAGE_PATTERN.matcher(command);
        if (matcher.matches()) {
            String messageContent = matcher.group(1);
            game_dyn.sendMessage("all", new Message(messageContent, current_player));
        }
        else {
            System.err.println("Public message command format is incorrect: " + command);
        }
    }

    /**
     * Handles the quit command by making the player leave the game and exiting the system.
     */
    private void handleQuit() {
        if (current_player != null) {
            game_dyn.leave(current_player.getNickname(), current_game_id);
            game_dyn.youLeft();
        }
        System.exit(1);
    }
}




