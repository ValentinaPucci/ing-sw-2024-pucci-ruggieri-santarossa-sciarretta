package it.polimi.demo.view.dynamic;

import it.polimi.demo.model.ModelView;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.*;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.network.rmi.RMIClient;
import it.polimi.demo.network.socket.client.SocketClient;
import it.polimi.demo.observer.Listener;
import it.polimi.demo.view.dynamic.utilities.TypeConnection;
import it.polimi.demo.view.dynamic.utilities.gameFacts.FactQueue;
import it.polimi.demo.view.dynamic.utilities.gameFacts.FactType;
import it.polimi.demo.view.dynamic.utilities.QueueParser;
import it.polimi.demo.view.gui.FXApplication;
import it.polimi.demo.view.gui.GUI;
import it.polimi.demo.view.text.StaticPrinterTUI;
import it.polimi.demo.view.text.TUI;

import java.io.IOException;
import java.io.Serial;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

/**
 * The GameDynamic class represents the dynamic part of the game, handling game events and state changes.
 */
public class GameDynamic implements Listener, Runnable, ClientInterface {

    @Serial
    private static final long serialVersionUID = -6369242392957782222L;

    // Buffers and parsers
    protected LinkedBlockingQueue<String> reader_queue;
    protected QueueParser parser;

    // User Interface instance
    private final UI ui;

    // Game-related attributes
    private String nickname;
    private final FactQueue facts = new FactQueue();
    private ClientInterface client_interface;

    /**
     * Constructor for initializing the GameDynamic instance in TUI mode.
     * @param selection The type of connection (RMI or Socket).
     */
    public GameDynamic(TypeConnection selection) {
        this(selection, null);
    }

    /**
     * Constructor for initializing the GameDynamic instance in GUI mode.
     * @param guiApplication The GUI application instance.
     * @param selection The type of connection (RMI or Socket).
     */
    public GameDynamic(FXApplication guiApplication, TypeConnection selection) {
        this(selection, guiApplication);
    }

    /**
     * Constructor for initializing the GameDynamic instance based on the connection type and GUI application.
     * @param selection The type of connection (RMI or Socket).
     * @param guiApplication The GUI application instance (null for TUI).
     */
    public GameDynamic(TypeConnection selection, FXApplication guiApplication) {
        startConnection(selection);
        reader_queue = new LinkedBlockingQueue<>();

        if (guiApplication == null) {
            // TUI initialization (reader thread)
            new Thread(() -> {
                try (Scanner scanner = new Scanner(System.in)) {
                    while (!Thread.currentThread().isInterrupted()) {
                        if (scanner.hasNextLine()) {
                            String input = scanner.nextLine();
                            reader_queue.add(input);
                            StaticPrinterTUI.print("\033[1A\033[2K");
                        }
                    }
                } catch (Exception e) {
                    // Handle other unexpected exceptions if necessary
                    e.printStackTrace();
                }
            }).start();
            ui = new TUI();
        } else {
            // GUI initialization
            ui = new GUI(guiApplication, reader_queue);
        }
        parser = new QueueParser(reader_queue, this);
        new Thread(this).start();
    }

    /**
     * Starts the connection based on the selected type (RMI or Socket).
     * @param selection The type of connection to start.
     */
    public void startConnection(TypeConnection selection) {
        client_interface = (selection == TypeConnection.RMI)
                ? new RMIClient(this)
                : new SocketClient(this);
    }

    /**
     * Main execution method that runs in a separate thread, handling game events and state changes.
     */
    @Override
    public void run() {
        facts.offer(null, FactType.LOBBY_INFO);

        // Game status handlers
        Map<GameStatus, Consumer<HashMap<ModelView, FactType>>> statusHandlers = Map.of(
                GameStatus.WAIT, this::safeStatusWait,
                GameStatus.FIRST_ROUND, this::safeStatusFirstRound,
                GameStatus.RUNNING, this::safeStatusRunning,
                GameStatus.SECOND_LAST_ROUND, this::safeStatusRunning,
                GameStatus.LAST_ROUND, this::safeStatusLastRound,
                GameStatus.ENDED, this::statusEnded
        );

        // Main event loop
        while (!Thread.currentThread().isInterrupted()) {
            Runnable event = facts.isToHandle()
                    ? () -> handleEvent(statusHandlers)
                    : () -> handleFallbackEvent(this::safeStatusNotInAGame);
            event.run();
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Preserve interrupt status
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Handles a game event based on its status, invoking the corresponding handler.
     * @param handlers The map of handlers for different game statuses.
     */
    private void handleEvent(Map<GameStatus, Consumer<HashMap<ModelView, FactType>>> handlers) {

        // Here we poll the facts queue to get the next event
        HashMap<ModelView, FactType> map = facts.poll();

        if (map == null || map.containsKey(null)) {
            return;
        }

        ModelView model_view = map.keySet().iterator().next();

        if (model_view != null) {
            handlers.getOrDefault(model_view.getStatus(), this::doNothing).accept(map);
        }
    }

    /**
     * Handles a fallback event when no specific handler is found for the current game status.
     * @param fallback The fallback handler to invoke.
     */
    private void handleFallbackEvent(Consumer<HashMap<ModelView, FactType>> fallback) {

        HashMap<ModelView, FactType> map = facts.poll();

        if (map != null) {
            fallback.accept(map);
        }
    }

    // Game status handlers

    /**
     * Executes the action for handling WAIT game status.
     * @param map The map containing ModelView and FactType associated with the event.
     */
    private void safeStatusWait(HashMap<ModelView, FactType> map) {
        try {
            statusWait(map);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Executes the action for handling FIRST_ROUND game status.
     * @param map The map containing ModelView and FactType associated with the event.
     */
    private void safeStatusFirstRound(HashMap<ModelView, FactType> map) {
        try {
            statusFirstRound(map);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Executes the action for handling RUNNING game status.
     * @param map The map containing ModelView and FactType associated with the event.
     */
    private void safeStatusRunning(HashMap<ModelView, FactType> map) {
        try {
            statusRunning(map);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Executes the action for handling SECOND_LAST_ROUND game status.
     * @param map The map containing ModelView and FactType associated with the event.
     */
    private void safeStatusLastRound(HashMap<ModelView, FactType> map) {
        try {
            statusLastRound(map);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Executes the action for handling game status when not in a game.
     * @param map The map containing ModelView and FactType associated with the event.
     */
    private void safeStatusNotInAGame(HashMap<ModelView, FactType> map) {
        statusNotInAGame(map);
    }

    /**
     * Placeholder method for handling undefined game statuses.
     * @param map The map containing ModelView and FactType associated with the event.
     */
    private void doNothing(HashMap<ModelView, FactType> map) {}

    // GameDynamic

    /**
     * Handles actions when the game status indicates the player is not in a game.
     * @param map The map containing ModelView and FactType associated with the event.
     */
    private void statusNotInAGame(HashMap<ModelView, FactType> map) {

        FactType type = map.values().iterator().next();

        Map<FactType, Runnable> actions = Map.of(FactType.GENERIC_ERROR, () -> {
                    ui.displayMenu();
                    nickname = null;
                    parser.getProcessedDataQueue().clear();
                    facts.offer(null, FactType.LOBBY_INFO);
                },
                FactType.LOBBY_INFO, () -> {
                    boolean selectionOk;
                    do {
                        selectionOk = askSelectGame();
                    } while (!selectionOk);
                }
        );

        actions.getOrDefault(type, () -> {}).run();
    }

    /**
     * Handles actions when the game status is WAIT.
     * @param map The map containing ModelView and FactType associated with the event.
     * @throws IOException If an I/O error occurs.
     * @throws InterruptedException If the current thread is interrupted.
     */
    private void statusWait(HashMap<ModelView, FactType> map) throws IOException, InterruptedException {
        ModelView model = map.keySet().iterator().next();
        FactType type = map.values().iterator().next();

        Map<FactType, Runnable> actions = Map.of(
                FactType.PLAYER_JOINED, () -> {
                    String nickLastPlayer = model.getPlayersConnected().getLast().getNickname();
                    if (nickLastPlayer.equals(nickname)) {
                        ui.displayPlayerJoined(model, nickname);
                        askReadyToStart();
                    }
                }
        );

        actions.getOrDefault(type, () -> {}).run();
    }

    /**
     * Handles actions when the game status is FIRST_ROUND.
     * @param map The map containing ModelView and FactType associated with the event.
     * @throws IOException If an I/O error occurs.
     * @throws InterruptedException If the current thread is interrupted.
     */
    private void statusFirstRound(HashMap<ModelView, FactType> map) throws IOException, InterruptedException {
        ModelView model = map.keySet().iterator().next();
        FactType type = map.values().iterator().next();

        Map<FactType, Runnable> actions = Map.of(
                FactType.GAME_STARTED, () -> {
                    ui.displayGameStarted(model);
                    parser.bindPlayerToParser(model.getGameId(), model.getPlayerEntity(nickname));
                },
                FactType.NEXT_TURN, () -> {
                    ui.displayNextTurn(model, nickname);
                    if (amI(model)) {
                        ui.displayObjectiveCards(model);
                        askWhichObjectiveCard();
                        ui.displayStarterCards(model);
                        askStarterCardOrientationAndPlace();
                        //ui.show_StarterCardPB(nickname, model);
                    }
                }
        );

        actions.getOrDefault(type, () -> {}).run();
    }

    /**
     * Handles actions when the game status is RUNNING.
     * @param map The map containing ModelView and FactType associated with the event.
     * @throws IOException If an I/O error occurs.
     * @throws InterruptedException If the current thread is interrupted.
     */
    private void statusRunning(HashMap<ModelView, FactType> map) throws IOException, InterruptedException {
        ModelView model = map.keySet().iterator().next();
        FactType type = map.values().iterator().next();

        Map<FactType, Runnable> actions = Map.of(
                FactType.NEXT_TURN, () -> {
                    if (amI(model)) {
                        ui.displayPersonalObjectiveCard(model);
                        ui.displayPlayerHand(model, nickname);
                        askWhichCard();
                    }
                },
                FactType.ASK_WHICH_ORIENTATION, () -> handleOrientationOrIllegalMove(map),
                FactType.ILLEGAL_MOVE, () -> handleOrientationOrIllegalMove(map),
                FactType.CARD_PLACED, () -> {
                    if (amI(model)) {
                        askWhereToDrawFrom();
                    }
                }
        );

        actions.getOrDefault(type, () -> {}).run();
    }

    /**
     * Handles actions when the game status is LAST_ROUND.
     * @param map The map containing ModelView and FactType associated with the event.
     * @throws IOException If an I/O error occurs.
     * @throws InterruptedException If the current thread is interrupted.
     */
    private void statusLastRound(HashMap<ModelView, FactType> map) throws IOException, InterruptedException {
        ModelView model = map.keySet().iterator().next();
        FactType type = map.values().iterator().next();

        Map<FactType, Runnable> actions = Map.of(
                FactType.NEXT_TURN, () -> {
                    if (amI(model)) {
                        ui.displayPersonalObjectiveCard(model);
                        ui.displayPlayerHand(model, nickname);
                        ui.displayPawnPositions(model);
                        askWhichCard();
                    }
                },
                FactType.ASK_WHICH_ORIENTATION, () -> handleOrientationOrIllegalMove(map),
                FactType.ILLEGAL_MOVE, () -> handleOrientationOrIllegalMove(map)
        );

        actions.getOrDefault(type, () -> {}).run();
    }

    /**
     * Handles actions when the game status is ENDED.
     * @param map The map containing ModelView and FactType associated with the event.
     */
    private void statusEnded(HashMap<ModelView, FactType> map) {
        ModelView model = map.keySet().iterator().next();
        FactType type = map.values().iterator().next();

        Map<FactType, Runnable> actions = Map.of(
                FactType.GAME_ENDED, () -> {
                    ui.displayMenu();
                    parser.getProcessedDataQueue().clear();
                    updateParser();
                    leave(nickname, model.getGameId());
                    youLeft();
                }
        );

        actions.getOrDefault(type, () -> {}).run();
    }

    /**
     * Updates the parser by removing the processed data from the queue.
     */
    private void updateParser() {
        try {
            parser.getProcessedDataQueue().take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles orientation or illegal move events in the game.
     * @param map The map containing ModelView and FactType associated with the event.
     */
    private void handleOrientationOrIllegalMove(HashMap<ModelView, FactType> map) {
        ModelView model = map.keySet().iterator().next();

        if (amI(model)) {
            ui.displayCardChosen(nickname, model);
            askGameCardOrientationAndPlace();
        }
    }

    /**
     * Performs cleanup actions when the player leaves the game.
     */
    public void youLeft() {
        facts.offer(null, FactType.LOBBY_INFO);
        parser.bindPlayerToParser(null, null);
    }

    /*===============ASK METHODS===============*/

    /**
     * Prompts the user to enter a nickname and displays the chosen nickname.
     */
    private void askNickname() {
        ui.displayInsertNickname();
        nickname = getProcessedData();
        ui.displayChosenNickname(nickname);
    }

    /**
     * Prompts the user to enter the number of players and validates the input.
     * @return The number of players entered by the user.
     */
    private Integer askNumOfPlayers() {
        Integer num_of_players = null;
        while (num_of_players == null) {
            ui.displayInsertNumOfPlayers();
            try {
                String temp = getProcessedData();
                num_of_players = Integer.parseInt(temp);
            } catch (NumberFormatException e) {
                ui.displayInvalidInput();
            }
        }
        return num_of_players;
    }

    /**
     * Prompts the user to select a game option and handles the input accordingly.
     * @return `true` if the selection was successful, `false` otherwise.
     */
    private boolean askSelectGame() {
        ui.startTheGame();
        String optionChoose = getProcessedData();
        if (optionChoose.equals(".")) System.exit(1);

        return switch (optionChoose) {
            case "c" -> handleCreateGame();
            case "j" -> handleJoinGame();
            case "js" -> handleJoinSpecificGame();
            default -> false;
        };
    }

    /**
     * Handles the creation of a new game based on user input.
     * @return `true` if the game creation was successful, `false` otherwise.
     */
    private boolean handleCreateGame() {
        Integer num_players = askNumOfPlayers();
        if (num_players < 2 || num_players > 4)
            return false;
        askNickname();
        createGame(nickname, num_players);
        return true;
    }

    /**
     * Handles the joining of a random game based on user input.
     * @return `true` if joining the game was successful, `false` otherwise.
     */
    private boolean handleJoinGame() {
        askNickname();
        joinRandomly(nickname);
        return true;
    }

    /**
     * Handles the joining of a specific game based on user input.
     * @return `true` if joining the game was successful, `false` otherwise.
     */
    private boolean handleJoinSpecificGame() {
        Integer gameId = askGameId();
        if (gameId == -1)
            return false;
        askNickname();
        joinGame(nickname, gameId);
        return true;
    }

    /**
     * Prompts the user to enter a game ID and validates the input.
     * @return The game ID entered by the user.
     */
    private Integer askGameId() {
        int gameId = -1;
        while (gameId < 0) {
            ui.displayInsertGameId();
            try {
                String temp = getProcessedData();
                gameId = Integer.parseInt(temp);
            } catch (NumberFormatException e) {
                ui.displayInvalidInput();
            }
        }
        return gameId;
    }

    /**
     * Prompts the user to confirm readiness to start the game.
     */
    public void askReadyToStart() {
        ui.displayGenericMessage("Are you ready to start? (y)");
        waitForValidInput("y");
        setAsReady();
    }

    /**
     * Prompts the user to select an objective card.
     */
    public void askWhichObjectiveCard() {
        ui.displayWhichObjectiveToChoose();
        String choice = waitForValidInput("1", "2");
        try {
            chooseCard(Integer.parseInt(choice) - 1);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Prompts the user to select a card.
     */
    public void askWhichCard() {
        ui.displayWhichCardToPlace();
        String choice = waitForValidInput("1", "2", "3");
        try {
            chooseCard(Integer.parseInt(choice) - 1);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Prompts the user to select the orientation and placement of a starter card.
     */
    public void askStarterCardOrientationAndPlace() {
        ui.displayOrientation("Choose the orientation of the starter card");
        String orientation = waitForValidInput("f", "b").equals("f") ? "FRONT" : "BACK";
        try {
            placeStarterCard(Orientation.valueOf(orientation));
        } catch (RemoteException | GameEndedException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Prompts the user to select the orientation and placement of a game card.
     */
    public void askGameCardOrientationAndPlace() {
        ui.displayOrientation("Choose the orientation of the card to place");
        String orientation = waitForValidInput("f", "b").equals("f") ? "FRONT" : "BACK";
        int x = getValidatedCoordinate("x");
        int y = getValidatedCoordinate("y");
        try {
            placeCard(x + 25, y + 25, Orientation.valueOf(orientation));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Prompts the user to select where to draw a card from.
     */
    public void askWhereToDrawFrom() {
        ui.displayWhereToDrawFrom();
        int choice = Integer.parseInt(waitForValidInput("1", "2", "3", "4", "5", "6"));
        try {
            drawCard(choice);
        } catch (RemoteException | GameEndedException e) {
            throw new RuntimeException(e);
        }
    }

    // real getters from the buffer

    /**
     * Retrieves processed data from the parser's queue, blocking until data is available.
     * @return The processed data retrieved from the queue.
     * @throws RuntimeException If interrupted while waiting for data.
     */
    private String getProcessedData() {
        try {
            return this.parser.getProcessedDataQueue().take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Waits for valid input from the user based on a list of valid inputs.
     * @param validInputs The valid inputs the user can provide.
     * @return The valid input provided by the user.
     */
    private String waitForValidInput(String... validInputs) {
        String input;
        do {
            input = getProcessedData();
            if (!List.of(validInputs).contains(input))
                ui.displayGenericMessage("Invalid input. Please enter a valid input.");
        } while (!List.of(validInputs).contains(input));
        return input;
    }

    /**
     * Gets and validates a coordinate input from the user.
     * @param coordinate The type of coordinate (e.g., "x", "y").
     * @return The validated coordinate input.
     */
    private int getValidatedCoordinate(String coordinate) {
        String input;
        do {
            ui.displayGenericMessage("Choose the ** " + coordinate +
                    " ** coordinates where to place the card (insert a number between -40 and +40)");
            input = getProcessedData();
        } while (!isCoordinateValid(input));
        return Integer.parseInt(input.trim());
    }

    /**
     * Checks if the given input string represents a valid coordinate within the specified range.
     * @param input The input string to validate.
     * @return `true` if the input is a valid coordinate, `false` otherwise.
     */
    private boolean isCoordinateValid(String input) {
        try {
            int value = Integer.parseInt(input.trim());
            return value >= -25 && value <= 25;
        } catch (NumberFormatException e) {
            ui.displayGenericError("Invalid input. Please enter a valid number between -40 and +40.");
            return false;
        }
    }


    // Client --------> SocketServer

    /**
     * Displays ui message indicating a connection error.
     */
    public void noConnectionError() {
        ui.displayNoConnectionError();
    }

    /**
     * Executes the provided action while handling common exceptions.
     * If an IOException, InterruptedException, NotBoundException, or GameEndedException occurs,
     * it displays a connection error message on the ui.
     * @param action The action to execute.
     */
    private void handleAction(Action action) {
        try {
            action.execute();
        } catch (IOException | InterruptedException | NotBoundException e) {
            noConnectionError();
        } catch (GameEndedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Functional interface for defining actions that may throw IOException, InterruptedException,
     * NotBoundException, or GameEndedException.
     */
    @FunctionalInterface
    private interface Action {
        void execute() throws IOException, InterruptedException, NotBoundException, GameEndedException;
    }

    /**
     * Initiates the process of creating a game with the given nickname and number of players.
     * @param nickname The nickname of the player creating the game.
     * @param num_of_players The number of players to join the game.
     */
    @Override
    public void createGame(String nickname, int num_of_players) {
        ui.displayCreateGame(nickname);
        handleAction(() -> client_interface.createGame(nickname, num_of_players));
    }

    /**
     * Initiates the process of joining a game randomly with the given nickname.
     * @param nick The nickname of the player joining randomly.
     */
    @Override
    public void joinRandomly(String nick) {
        ui.displayJoinRandom(nick);
        handleAction(() -> client_interface.joinRandomly(nick));
    }

    /**
     * Initiates the process of joining a specific game with the given nickname and game ID.
     * @param nick The nickname of the player joining the specific game.
     * @param game_id The ID of the game to join.
     */
    @Override
    public void joinGame(String nick, int game_id) {
        ui.displayJoin(game_id, nick);
        handleAction(() -> client_interface.joinGame(nick, game_id));
    }

    /**
     * Sets the player as ready to start the game.
     */
    @Override
    public void setAsReady() {
        handleAction(() -> client_interface.setAsReady());
    }

    /**
     * Places the starter card with the specified orientation.
     * @param orientation The orientation of the starter card.
     * @throws RemoteException If a remote communication error occurs.
     * @throws GameEndedException If the game has already ended.
     * @throws NotBoundException If a remote object is not bound.
     */
    @Override
    public void placeStarterCard(Orientation orientation) throws RemoteException, GameEndedException, NotBoundException {
        handleAction(() -> client_interface.placeStarterCard(orientation));
    }

    /**
     * Places a card at the specified coordinates with the given orientation.
     * @param where_to_place_x The x-coordinate where the card will be placed.
     * @param where_to_place_y The y-coordinate where the card will be placed.
     * @param orientation The orientation of the card to be placed.
     * @throws RemoteException If a remote communication error occurs.
     */
    @Override
    public void placeCard(int where_to_place_x, int where_to_place_y, Orientation orientation) throws RemoteException {
        handleAction(() -> client_interface.placeCard(where_to_place_x, where_to_place_y, orientation));
    }

    /**
     * Chooses a card to play based on the provided index.
     * @param which_card The index of the card to choose.
     * @throws RemoteException If a remote communication error occurs.
     */
    @Override
    public void chooseCard(int which_card) throws RemoteException {
        handleAction(() -> client_interface.chooseCard(which_card));
    }

    /**
     * Draws a card from the specified index in the deck.
     * @param index The index from which to draw the card.
     * @throws RemoteException If a remote communication error occurs.
     * @throws GameEndedException If the game has already ended.
     */
    @Override
    public void drawCard(int index) throws RemoteException, GameEndedException {
        handleAction(() -> client_interface.drawCard(index));
    }

    /**
     * Sends a message to another player.
     * @param receiver The nickname of the player who will receive the message.
     * @param msg The message to be sent.
     */
    @Override
    public void sendMessage(String receiver, Message msg) {
        handleAction(() -> client_interface.sendMessage(receiver, msg));
    }

    /**
     * Displays the personal board of another player based on the player's index.
     * @param player_index The index of the player whose personal board will be shown.
     */
    @Override
    public void showOthersPersonalBoard(int player_index) {
        handleAction(() -> client_interface.showOthersPersonalBoard(player_index));
    }

    /**
     * Initiates the process of leaving the game with the given nickname and game ID.
     * @param nick The nickname of the player leaving the game.
     * @param idGame The ID of the game from which the player is leaving.
     */
    @Override
    public void leave(String nick, int idGame) {
        handleAction(() -> client_interface.leave(nick, idGame));
    }

    /**
     * Pings the server to maintain connection.
     */
    @Override
    public void ping() {}

    /**
     * Checks if the current player is the same as the player associated with this instance.
     * @param model The model view containing game state information.
     * @return True if the current player nickname matches the instance's nickname; false otherwise.
     */
    private boolean amI(ModelView model) {
        return model.getCurrentPlayerNickname().equals(nickname);
    }

    /**
     * Executes a consumer action on the model view if the current player matches the instance's nickname.
     * @param model The model view containing game state information.
     * @param action The consumer action to execute if the current player matches the instance's nickname.
     */
    private void ifAmI(ModelView model, Consumer<ModelView> action) {
        if (amI(model)) {
            action.accept(model);
        }
    }

    /**
     * Handles the event when the starter card is placed, showing the personal board if it's the current player's turn.
     * @param model The model view containing game state information.
     * @param orientation The orientation of the placed starter card.
     * @param nick The nickname of the player who placed the starter card.
     */
    @Override
    public void starterCardPlaced(ModelView model, Orientation orientation, String nick) {
        ifAmI(model, m -> ui.displayPersonalBoard(nickname, m));
    }

    /**
     * Handles the event when a card is chosen, offering a fact about which orientation to choose if it's the current player's turn.
     * @param model The model view containing game state information.
     * @param which_card The index of the chosen card.
     */
    @Override
    public void cardChosen(ModelView model, int which_card) {
        ifAmI(model, m -> facts.offer(m, FactType.ASK_WHICH_ORIENTATION));
    }

    /**
     * Handles the event when a card is placed, showing the personal board and common board if it's the current player's turn.
     * @param model The model view containing game state information.
     * @param where_to_place_x The x-coordinate where the card is placed.
     * @param where_to_place_y The y-coordinate where the card is placed.
     * @param orientation The orientation of the placed card.
     */
    @Override
    public void cardPlaced(ModelView model, int where_to_place_x, int where_to_place_y, Orientation orientation) {
        ifAmI(model, m -> {
            ui.displayPersonalBoard(nickname, m);
            ui.displayCommonBoard(m);
            facts.offer(m, FactType.CARD_PLACED);
        });
    }

    /**
     * Handles the event when an illegal move is attempted, showing an error message and the personal board if it's the current player's turn.
     * @param model The model view containing game state information.
     */
    @Override
    public void illegalMove(ModelView model) {
        ifAmI(model, m -> {
            ui.displayIllegalMove();
            ui.displayGenericMessage("Here we show you again your personal board:");
            ui.displayPersonalBoard(nickname, m);
            facts.offer(m, FactType.ILLEGAL_MOVE);
        });
    }

    /**
     * Handles the event when an illegal move is attempted due to a specific reason, showing an error message and the personal board if it's the current player's turn.
     * @param model The model view containing game state information.
     * @param reason_why The reason why the move is illegal.
     */
    @Override
    public void illegalMoveBecauseOf(ModelView model, String reason_why) {
        ifAmI(model, m -> {
            ui.displayIllegalMoveBecauseOf(reason_why);
            ui.displayPersonalBoard(nickname, m);
            facts.offer(m, FactType.ILLEGAL_MOVE);
        });
    }

    /**
     * Handles the event when a move is successfully executed, showing a success message if it's the current player's turn.
     * @param model The model view containing game state information.
     * @param coord The coordinates of the successful move.
     */
    @Override
    public void successfulMove(ModelView model, Coordinate coord) {
        ifAmI(model, m -> ui.displaySuccessfulMove(coord));
    }

    /**
     * Handles the event when a card is drawn by a player, showing a message if it's the current player's turn.
     * @param model The model view containing game state information.
     * @param index The index of the drawn card.
     */
    @Override
    public void cardDrawn(ModelView model, int index) {
        ui.displayCardDrawn(model, nickname);
    }

    /**
     * Handles the event when it's the next player's turn, showing a message if it's not the current player's turn and the game is running or in the second last round.
     * @param model The model view containing game state information.
     */
    @Override
    public void nextTurn(ModelView model) {
        if (!amI(model) && (model.getStatus() == GameStatus.RUNNING || model.getStatus() == GameStatus.SECOND_LAST_ROUND || model.getStatus()==GameStatus.LAST_ROUND)) {
            ui.displayMyTurnIsFinished();
        }
        facts.offer(model, FactType.NEXT_TURN);
        parser.getProcessedDataQueue().clear();
    }

    /**
     * Handles the event when a player joins the game, showing a message and offering a fact about player joining.
     * @param gameModel The model view containing game state information.
     */
    @Override
    public void playerJoined(ModelView gameModel) {
        ui.displayPlayerJoined(gameModel, nickname);
        facts.offer(gameModel, FactType.PLAYER_JOINED);
    }

    /**
     * Handles the event when a player is ready to start the game, showing a message if it's the current player.
     * @param gameModel The model view containing game state information.
     * @param nick The nickname of the player who is ready.
     */
    @Override
    public void playerIsReadyToStart(ModelView gameModel, String nick) {
        ui.displayPlayerJoined(gameModel, nickname);
        Runnable showReadyToStart = () -> ui.displayReadyToStart(gameModel, nickname);
        Optional.of(nick)
                .filter(nickname::equals)
                .ifPresent(n -> showReadyToStart.run());
        facts.offer(gameModel, FactType.PLAYER_IS_READY_TO_START);
    }


    /**
     * Handles the event when a player leaves the game, showing a relevant game fact.
     * @param gameModel The model view containing game state information.
     * @param nick The nickname of the player who left.
     */
    @Override
    public void playerLeft(ModelView gameModel, String nick) {
        ui.displayGenericMessage("[EVENT]: Player " + nick + " decided to leave the game!");
        ui.displayPlayerLeft(gameModel, nick);
    }

    /**
     * Handles the event when a message is sent between players, showing a message if it's the current player sending or receiving.
     * @param gameModel The model view containing game state information.
     * @param nick The nickname of the player who sent the message.
     * @param msg The message that was sent.
     */
    @Override
    public void messageSent(ModelView gameModel, String nick, Message msg) {
        if (!msg.sender().getNickname().equals(nickname) && (nickname.equals(nick) || "Everyone".equals(nick))) {
            ui.displayMessageSent(gameModel, nick);
        }
    }

    /**
     * Handles the event when a generic error occurs during the process of entering a game, showing a message and offering a fact about the generic error.
     * @param why The reason for the generic error.
     */
    @Override
    public void genericErrorWhenEnteringGame(String why) {
        ui.displayGenericMessage("No available game to join: " + why);
        facts.offer(null, FactType.GENERIC_ERROR);
    }

    /**
     * Handles the event when the game starts, offering a fact about the game starting.
     * @param gameModel The model view containing game state information.
     */
    @Override
    public void gameStarted(ModelView gameModel) {
        ui.displayGenericMessage("All players are connected, the game will start soon!");
        facts.offer(gameModel, FactType.GAME_STARTED);
    }

    /**
     * Handles the event when the game ends, offering a fact about the game ending and showing the game end screen on the ui.
     * @param gameModel The model view containing game state information.
     */
    @Override
    public void gameEnded(ModelView gameModel) {
        facts.offer(gameModel, FactType.GAME_ENDED);
        ui.displayGameEnded(gameModel);
    }

    /**
     * Handles the event when a player disconnects from the game, adding a relevant game fact about the disconnection.
     * @param gameModel The model view containing game state information.
     * @param nick The nickname of the player who disconnected.
     */
    @Override
    public void playerDisconnected(ModelView gameModel, String nick) {
        ui.displayGenericMessage("Player " + nick + " has just disconnected");
    }

    /**
     * Displays the personal board of another player if the index is valid and matches the player's nickname,
     * otherwise shows a generic message indicating the index is out of bounds.
     * @param modelView The model view containing game state information.
     * @param playerNickname The nickname of the player whose personal board is to be shown.
     * @param playerIndex The index of the player whose personal board is to be shown.
     * @throws RemoteException If a remote communication error occurs.
     */
    @Override
    public void showOthersPersonalBoard(ModelView modelView, String playerNickname, int playerIndex) throws RemoteException {
        if (modelView.getAllPlayers().size() > playerIndex && this.nickname.equals(playerNickname)) {
            ui.displayOthersPersonalBoard(modelView, playerIndex);
        } else if (this.nickname.equals(playerNickname)) {
            ui.displayGenericMessage("Player index out of bounds");
        }
    }

    /**
     * Handles the event when the second last round of the game begins, showing a generic message on the ui.
     * @param gameModel The model view containing game state information.
     */
    @Override
    public void secondLastRound(ModelView gameModel) {
        ui.displayGenericMessage("*** Second last round begins! ***");
    }

    /**
     * Handles the event when the last round of the game begins, showing a generic message on the ui indicating no more card drawing is allowed.
     * @param gameModel The model view containing game state information.
     */
    @Override
    public void lastRound(ModelView gameModel) {
        ui.displayGenericMessage("*** Last round begins! Now you will not be able to draw any additional card! ***");
    }

}
