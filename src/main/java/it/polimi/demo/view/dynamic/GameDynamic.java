package it.polimi.demo.view.dynamic;

import it.polimi.demo.model.ModelView;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.*;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.network.rmi.RMIClient;
import it.polimi.demo.observer.Listener;
import it.polimi.demo.view.dynamic.utilities.gameFacts.FactQueue;
import it.polimi.demo.view.dynamic.utilities.gameFacts.FactType;
import it.polimi.demo.view.dynamic.utilities.parser.AbstractReader;
import it.polimi.demo.view.dynamic.utilities.parser.GenericParser;
import it.polimi.demo.view.dynamic.utilities.parser.GuiReader;
import it.polimi.demo.view.dynamic.utilities.parser.TuiReader;
import it.polimi.demo.view.gui.ApplicationGUI;
import it.polimi.demo.view.gui.GUI;
import it.polimi.demo.network.socket.client.ClientSocket;
import it.polimi.demo.view.text.TUI;

import java.io.IOException;
import java.io.Serial;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static it.polimi.demo.view.dynamic.utilities.gameFacts.FactType.*;

public class GameDynamic implements Listener, Runnable, ClientInterface {

    @Serial
    private static final long serialVersionUID = 3620482106619487368L;

    // buffers and parsers
    protected GenericParser generic_parser;
    protected AbstractReader abstract_reader;

    private final UI ui;

    private String nickname = "";
    private final FactQueue facts = new FactQueue();
    private ClientInterface client_interface;

    // Constructor for TUI
    public GameDynamic(TypeConnection selection) {
        this(selection, null);
    }

    // Constructor for GUI
    public GameDynamic(ApplicationGUI guiApplication, TypeConnection selection) {
        this(selection, guiApplication);
    }
    
    private GameDynamic(TypeConnection selection, ApplicationGUI guiApplication) {
        
        startConnection(selection);
        
        if (guiApplication == null) {
            abstract_reader = new TuiReader();
            ui = new TUI();
        } else {
            abstract_reader = new GuiReader();
            ui = new GUI(guiApplication, (GuiReader) abstract_reader);
        }
        generic_parser = new GenericParser(abstract_reader.getBuffer(), this);
        new Thread(this).start();
    }

    public void startConnection(TypeConnection selection) {
        client_interface = (selection == TypeConnection.RMI) 
                ? new RMIClient(this) 
                : new ClientSocket(this);
    }

    @Override
    public void run() {

        facts.offer(null, LOBBY_INFO);

        // In - game handler
        Map<GameStatus, Consumer<HashMap<ModelView, FactType>>> statusHandlers = Map.of(
                GameStatus.WAIT, this::safeStatusWait,
                GameStatus.FIRST_ROUND, this::safeStatusFirstRound,
                GameStatus.RUNNING, this::safeStatusRunning,
                GameStatus.SECOND_LAST_ROUND, this::safeStatusRunning,
                GameStatus.LAST_ROUND, this::safeStatusLastRound,
                GameStatus.ENDED, this::statusEnded
        );

        // Out - game handler
        while (!Thread.currentThread().isInterrupted()) {
            Runnable event = facts.isToHandle() 
                            ? () -> handleEvent(statusHandlers)
                            : () -> handleFallbackEvent(this::safeStatusNotInAGame);
            event.run();
            try { Thread.sleep(200); }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Preserve interrupt status
                throw new RuntimeException(e);
            }
        }

    }

    private void handleEvent(Map<GameStatus, Consumer<HashMap<ModelView, FactType>>> handlers) {

        HashMap<ModelView, FactType> map = facts.poll();

        if (map == null || map.containsKey(null) ) 
            return;
        
        // Here we take the only model_view associated to the given fact
        ModelView model_view = map.keySet().iterator().next();

        if (model_view != null) 
            handlers.getOrDefault(model_view.getStatus(), this::doNothing).accept(map);
    }

    private void handleFallbackEvent(Consumer<HashMap<ModelView, FactType>> fallback) {

        HashMap<ModelView, FactType> map = facts.poll();

        if (map != null) 
            fallback.accept(map);
        
    }

    private void safeStatusWait(HashMap<ModelView, FactType> map) {
        try {
            statusWait(map);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void safeStatusFirstRound(HashMap<ModelView, FactType> map) {
        try {
            statusFirstRound(map);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void safeStatusRunning(HashMap<ModelView, FactType> map) {
        try {
            statusRunning(map);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void safeStatusLastRound(HashMap<ModelView, FactType> map) {
        try {
            statusLastRound(map);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void safeStatusNotInAGame(HashMap<ModelView, FactType> map) {
        statusNotInAGame(map);
    }

    private void doNothing(HashMap<ModelView, FactType> map) {
        // No action required for undefined statuses
    }

    // GameDynamic

    private void statusNotInAGame(HashMap<ModelView, FactType> map) {

        FactType type = map.values().iterator().next();

        // Helper method to handle common nickname and fact operations
        Consumer<String> handleNicknameAndOffer = (s) -> {
            nickname = null;
            facts.offer(null, FactType.LOBBY_INFO);
            ui.addImportantEvent(s);
        };

        Map<FactType, Runnable> actions = Map.of(
                FactType.LOBBY_INFO, () -> {
                    boolean selectionOk;
                    do {
                        selectionOk = askSelectGame();
                    } while (!selectionOk);
                },
                FactType.ALREADY_USED_NICKNAME, () -> handleNicknameAndOffer.accept("[WARNING]: Already used nickname!"),
                FactType.FULL_GAME, () -> handleNicknameAndOffer.accept("[WARNING] Full game!"),
                FactType.GENERIC_ERROR, () -> {
                    ui.show_menu();
                    updateParser();
                    nickname = null;
                    facts.offer(null, FactType.LOBBY_INFO);
                }
        );

        actions.getOrDefault(type, () -> {}).run();
    }


    private void statusWait(HashMap<ModelView, FactType> map) throws IOException, InterruptedException {

        ModelView model = map.keySet().iterator().next();
        FactType type = map.values().iterator().next();

        Map<FactType, Runnable> actions = Map.of(
                FactType.PLAYER_JOINED, () -> {
                    String nickLastPlayer = model.getPlayersConnected().getLast().getNickname();
                    if (nickLastPlayer.equals(nickname)) {
                        ui.show_playerJoined(model, nickname);
                        askReadyToStart();
                    }
                }
        );

        actions.getOrDefault(type, () -> {}).run();
    }

    private void statusFirstRound(HashMap<ModelView, FactType> map) throws IOException, InterruptedException {

        ModelView model = map.keySet().iterator().next();
        FactType type = map.values().iterator().next();

        Map<FactType, Runnable> actions = Map.of(
                FactType.GAME_STARTED, () -> {
                    ui.show_gameStarted(model);
                    generic_parser.bindPlayerToParser(model.getGameId(), model.getPlayerEntity(nickname));
                },
                FactType.NEXT_TURN, () -> {
                    ui.show_nextTurn(model, nickname);
                    if (amI(model)) {
                        ui.show_objectiveCards(model);
                        askWhichObjectiveCard();
                        ui.show_starterCards(model);
                        askStarterCardOrientationAndPlace();
                    }
                }
        );

        actions.getOrDefault(type, () -> {}).run();
    }

    private void statusRunning(HashMap<ModelView, FactType> map) throws IOException, InterruptedException {

        ModelView model = map.keySet().iterator().next();
        FactType type = map.values().iterator().next();

        Map<FactType, Runnable> actions = Map.of(
                FactType.NEXT_TURN, () -> {
                    if (amI(model)) {
                        ui.show_personalObjectiveCard(model);
                        ui.show_playerHand(model, nickname);
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

    private void statusLastRound(HashMap<ModelView, FactType> map) throws IOException, InterruptedException {

        ModelView model = map.keySet().iterator().next();
        FactType type = map.values().iterator().next();

        Map<FactType, Runnable> actions = Map.of(
                FactType.NEXT_TURN, () -> {
                    if (amI(model)) {
                        ui.show_personalObjectiveCard(model);
                        ui.show_playerHand(model, nickname);
                        askWhichCard();
                    }
                },
                FactType.ASK_WHICH_ORIENTATION, () -> handleOrientationOrIllegalMove(map),
                FactType.ILLEGAL_MOVE, () -> handleOrientationOrIllegalMove(map)
        );

        actions.getOrDefault(type, () -> {}).run();
    }

    private void statusEnded(HashMap<ModelView, FactType> map) {

        ModelView model = map.keySet().iterator().next();
        FactType type = map.values().iterator().next();

        Map<FactType, Runnable> actions = Map.of(
                FactType.GAME_ENDED, () -> {
                    ui.show_menu();
                    generic_parser.getProcessed_data_queue().clear();
                    updateParser();
                    leave(nickname, model.getGameId());
                    youLeft();
                }
        );

        actions.getOrDefault(type, () -> {}).run();
    }
    
    private void updateParser() {
        try {
            generic_parser.getProcessed_data_queue().take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleOrientationOrIllegalMove(HashMap<ModelView, FactType> map) {

        ModelView model = map.keySet().iterator().next();

        if (amI(model)) {
            ui.show_cardChosen(nickname, model);
            askGameCardOrientationAndPlace();
        }
    }

    public void youLeft() {
        ui.resetImportantEvents();
        facts.offer(null, LOBBY_INFO);
        generic_parser.bindPlayerToParser(null, null);
    }

    /*===============ASK METHODS===============*/

    private void askNickname() {
        ui.show_insertNicknameMsg();
        nickname = getProcessedData();
        ui.show_chosenNickname(nickname);
    }

    private Integer askNumOfPlayers() {
        Integer num_of_players = null;
        while (num_of_players == null) {
            ui.show_insertNumOfPlayersMsg();
            try {
                String temp = getProcessedData();
                num_of_players = Integer.parseInt(temp);
            } catch (NumberFormatException e) {
                ui.show_invalidInput();
            }
        }
        return num_of_players;
    }

    private boolean askSelectGame() {
        ui.show_menuOptions();
        String optionChoose = getProcessedData();
        if (optionChoose.equals(".")) System.exit(1);

        return switch (optionChoose) {
            case "c" -> handleCreateGame();
            case "j" -> handleJoinGame();
            case "js" -> handleJoinSpecificGame();
            default -> false;
        };
    }

    private boolean handleCreateGame() {
        Integer num_players = askNumOfPlayers();
        if (num_players < 2 || num_players > 4)
            return false;
        askNickname();
        createGame(nickname, num_players);
        return true;
    }

    private boolean handleJoinGame() {
        askNickname();
        joinRandomly(nickname);
        return true;
    }

    private boolean handleJoinSpecificGame() {
        Integer gameId = askGameId();
        if (gameId == -1)
            return false;
        askNickname();
        joinGame(nickname, gameId);
        return true;
    }

    private Integer askGameId() {
        int gameId = -1;
        while (gameId < 0) {
            ui.show_inputGameIdMsg();
            try {
                String temp = getProcessedData();
                gameId = Integer.parseInt(temp);
            } catch (NumberFormatException e) {
                ui.show_invalidInput();
            }
        }
        return gameId;
    }

    public void askReadyToStart() {
        ui.show_genericMessage("Are you ready to start? (y)");
        waitForValidInput("y");
        setAsReady();
    }

    public void askWhichObjectiveCard() {
        ui.show_whichObjectiveToChooseMsg();
        String choice = waitForValidInput("1", "2");
        try {
            chooseCard(Integer.parseInt(choice) - 1);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void askWhichCard() {
        ui.show_whichCardToPlaceMsg();
        String choice = waitForValidInput("1", "2", "3");
        try {
            chooseCard(Integer.parseInt(choice) - 1);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void askStarterCardOrientationAndPlace() {
        ui.show_orientation("Choose the orientation of the starter card");
        String orientation = waitForValidInput("f", "b").equals("f") ? "FRONT" : "BACK";
        try {
            placeStarterCard(Orientation.valueOf(orientation));
        } catch (RemoteException | GameEndedException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void askGameCardOrientationAndPlace() {
        ui.show_orientation("Choose the orientation of the card to place");
        String orientation = waitForValidInput("f", "b").equals("f") ? "FRONT" : "BACK";
        int x = getValidatedCoordinate("x");
        int y = getValidatedCoordinate("y");
        try {
            placeCard(x + 250, y + 250, Orientation.valueOf(orientation));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void askWhereToDrawFrom() {
        ui.show_whereToDrawFrom();
        int choice = Integer.parseInt(waitForValidInput("1", "2", "3", "4", "5", "6"));
        try {
            drawCard(choice);
        } catch (RemoteException | GameEndedException e) {
            throw new RuntimeException(e);
        }
    }

    // real getters from the buffer

    private String getProcessedData() {
        try {
            return this.generic_parser.getProcessed_data_queue().take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String waitForValidInput(String... validInputs) {
        String input;
        do {
            input = getProcessedData();
            if (!List.of(validInputs).contains(input))
                ui.show_genericMessage("Invalid input. Please enter a valid input.");
        } while (!List.of(validInputs).contains(input));
        return input;
    }

    private int getValidatedCoordinate(String coordinate) {
        String input;
        do {
            ui.show_genericMessage("Choose the ** " + coordinate +
                    " ** coordinates where to place the card (insert a number between -250 and 250)");
            input = getProcessedData();
        } while (!isCoordinateValid(input));
        return Integer.parseInt(input.trim());
    }

    private boolean isCoordinateValid(String input) {
        try {
            int value = Integer.parseInt(input.trim());
            return value >= -250 && value <= 250;
        } catch (NumberFormatException e) {
            ui.show_genericError("Invalid input. Please enter a valid number between -250 and 250.");
            return false;
        }
    }

    // Client --------> Server

    public void noConnectionError() {
        ui.show_noConnectionError();
    }

    // Helper method for handling common try-catch structure
    private void handleAction(Action action) {
        try {
            action.execute();
        } catch (IOException | InterruptedException | NotBoundException e) {
            noConnectionError();
        } catch (GameEndedException e) {
            throw new RuntimeException(e);
        }
    }

    @FunctionalInterface
    private interface Action {
        void execute() throws IOException, InterruptedException, NotBoundException, GameEndedException;
    }

    @Override
    public void createGame(String nickname, int num_of_players) {
        ui.show_creatingNewGameMsg(nickname);
        handleAction(() -> client_interface.createGame(nickname, num_of_players));
    }

    @Override
    public void joinRandomly(String nick) {
        ui.show_joiningFirstAvailableMsg(nick);
        handleAction(() -> client_interface.joinRandomly(nick));
    }

    @Override
    public void joinGame(String nick, int game_id) {
        ui.show_joiningToGameIdMsg(game_id, nick);
        handleAction(() -> client_interface.joinGame(nick, game_id));
    }

    @Override
    public void setAsReady() {
        handleAction(() -> client_interface.setAsReady());
    }

    @Override
    public void placeStarterCard(Orientation orientation) throws RemoteException, GameEndedException, NotBoundException {
        handleAction(() -> client_interface.placeStarterCard(orientation));
    }

    @Override
    public void placeCard(int where_to_place_x, int where_to_place_y, Orientation orientation) throws RemoteException {
        handleAction(() -> client_interface.placeCard(where_to_place_x, where_to_place_y, orientation));
    }

    @Override
    public void chooseCard(int which_card) throws RemoteException {
        handleAction(() -> client_interface.chooseCard(which_card));
    }

    @Override
    public void drawCard(int index) throws RemoteException, GameEndedException {
        handleAction(() -> client_interface.drawCard(index));
    }

    @Override
    public void sendMessage(String receiver, Message msg) {
        handleAction(() -> client_interface.sendMessage(receiver, msg));
    }

    @Override
    public void showOthersPersonalBoard(int player_index) {
        handleAction(() -> client_interface.showOthersPersonalBoard(player_index));
    }

    @Override
    public void leave(String nick, int idGame) {
        handleAction(() -> client_interface.leave(nick, idGame));
    }

    @Override
    public void ping() {}

    // Server --------> Client
    
    public boolean amI(ModelView model) {
        return model.getCurrentPlayerNickname().equals(nickname);
    }

    @Override
    public void starterCardPlaced(ModelView model, Orientation orientation, String nick) {
        if (amI(model))
            ui.show_personalBoard(nickname, model);
    }

    @Override
    public void cardChosen(ModelView model, int which_card) {
        if (amI(model))
            facts.offer(model, ASK_WHICH_ORIENTATION);
    }

    @Override
    public void cardPlaced(ModelView model, int where_to_place_x, int where_to_place_y, Orientation orientation) {
        if (amI(model)) {
            ui.show_personalBoard(nickname, model);
            ui.show_commonBoard(model);
            facts.offer(model, CARD_PLACED);
        }
    }

    @Override
    public void illegalMove(ModelView model) {
        if (amI(model)) {
            ui.show_illegalMove();
            ui.show_genericMessage("Here we show you again your personal board:");
            ui.show_personalBoard(nickname, model);
            facts.offer(model, ILLEGAL_MOVE);
        }
    }

    @Override
    public void successfulMove(ModelView model, Coordinate coord) throws RemoteException {
        if (amI(model))
            ui.show_successfulMove(coord);
    }

    @Override
    public void illegalMoveBecauseOf(ModelView model, String reason_why) {
        if (amI(model)) {
            ui.show_illegalMoveBecauseOf(reason_why);
            ui.show_personalBoard(nickname, model);
            facts.offer(model, ILLEGAL_MOVE);
        }
    }

    @Override
    public void cardDrawn(ModelView model, int index) {
        ui.show_cardDrawn(model, nickname);
    }

    @Override
    public void nextTurn(ModelView model) {
        if (!amI(model) &&
                (model.getStatus() == GameStatus.RUNNING ||
                        model.getStatus() == GameStatus.SECOND_LAST_ROUND))
            ui.show_myTurnIsFinished();
        facts.offer(model, FactType.NEXT_TURN);
        generic_parser.getProcessed_data_queue().clear();
    }

    @Override
    public void playerJoined(ModelView gameModel) {
        facts.offer(gameModel, FactType.PLAYER_JOINED);
        ui.show_playerJoined(gameModel, nickname);
    }

    @Override
    public void playerIsReadyToStart(ModelView gameModel, String nick) throws IOException {
        ui.show_playerJoined(gameModel, nickname);
        if (nick.equals(nickname))
            ui.show_ReadyToStart(gameModel, nickname);
        facts.offer(gameModel, PLAYER_IS_READY_TO_START);
    }

    @Override
    public void playerLeft(ModelView gameModel, String nick) throws RemoteException {
        ui.addImportantEvent("[EVENT]: Player " + nick + " decided to leave the game!");
    }

    @Override
    public void joinUnableGameFull(Player wantedToJoin, ModelView gameModel) throws RemoteException {
        facts.offer(null, FULL_GAME);
    }

    @Override
    public void messageSent(ModelView gameModel, String nick, Message msg) {
        if (!msg.getSender().getNickname().equals(nickname)) {
            if (nickname.equals(nick)) {
                // async
                ui.show_messageSent(gameModel, nick);
            } else if (nick.equals("all")) {
                // async
                ui.show_messageSent(gameModel, nick);
            }
        }
    }

    @Override
    public void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {
        facts.offer(null, ALREADY_USED_NICKNAME);
    }

    @Override
    public void gameIdNotExists(int gameid) throws RemoteException {
        ui.show_noAvailableGamesToJoin("No currently game available with the following GameID: " + gameid);
        facts.offer(null, GENERIC_ERROR);
    }

    @Override
    public void genericErrorWhenEnteringGame(String why) throws RemoteException {
        ui.show_noAvailableGamesToJoin(why);
        facts.offer(null, GENERIC_ERROR);
    }

    @Override
    public void gameStarted(ModelView gameModel) {
        ui.addImportantEvent("All players are connected, the game will start soon!");
        facts.offer(gameModel, FactType.GAME_STARTED);
    }

    @Override
    public void gameEnded(ModelView gameModel) {
        facts.offer(gameModel, FactType.GAME_ENDED);
        ui.show_gameEnded(gameModel);
    }

    @Override
    public void playerDisconnected(ModelView gameModel, String nick) {
        ui.addImportantEvent("Player " + nick + " has just disconnected");
    }

    @Override
    public void showOthersPersonalBoard(ModelView modelView, String playerNickname, int playerIndex) throws RemoteException {
        if (modelView.getAllPlayers().size() > playerIndex) {
            if (this.nickname.equals(playerNickname))
                ui.show_othersPersonalBoard(modelView, playerIndex);
        }
        else {
            if (this.nickname.equals(playerNickname))
                ui.show_genericMessage("Player index out of bounds");
        }
    }

    @Override
    public void secondLastRound(ModelView gameModel) {
        ui.show_genericMessage("*** Second last round begins! ***");
    }

    @Override
    public void lastRound(ModelView gameModel) throws RemoteException {
        ui.show_genericMessage("*** Last round begins! Now you will not be able to draw any additional card! ***");
    }
}
