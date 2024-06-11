package it.polimi.demo.view.dynamic;

import it.polimi.demo.model.ModelView;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.*;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.network.StaticPrinter;
import it.polimi.demo.network.rmi.RMIClient;
import it.polimi.demo.view.dynamic.utilities.*;
import it.polimi.demo.view.dynamic.utilities.gameFacts.FactQueue;
import it.polimi.demo.view.dynamic.utilities.gameFacts.FactType;
import it.polimi.demo.view.gui.ApplicationGUI;
import it.polimi.demo.view.gui.GUI;
import it.polimi.demo.network.socket.client.ClientSocket;
import it.polimi.demo.view.text.TUI;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static it.polimi.demo.view.dynamic.utilities.gameFacts.FactType.*;

public class GameDynamic extends Dynamic implements Runnable, ClientInterface {

    private String nickname;

    private final FactQueue facts = new FactQueue();

    private ClientInterface client_interface;

    private final UI ui;

    protected GenericParser generic_parser;

    protected AbstractReader abstract_reader;

    public GameDynamic(ConnectionSelection connectionSelection) {

        nickname = "";

        switch (connectionSelection) {
            case SOCKET -> client_interface = new ClientSocket(this);
            case RMI -> client_interface = new RMIClient(this);
        }

        this.abstract_reader = new TuiReader();
        ui = new TUI();
        this.generic_parser = new GenericParser(this.abstract_reader.getBuffer(), this);

        new Thread(this).start();
    }

    public GameDynamic(ApplicationGUI guiApplication, ConnectionSelection connectionSelection) {

        nickname = "";

        switch (connectionSelection) {
            case SOCKET -> client_interface = new ClientSocket(this);
            case RMI -> client_interface = new RMIClient(this);
        }

        this.abstract_reader = new GuiReader(); //GuiReader@5184ffd2
        ui = new GUI(guiApplication, (GuiReader) abstract_reader); //GuiReader@5184ffd2
        this.generic_parser = new GenericParser(this.abstract_reader.getBuffer(), this); //GuiReader@5184ffd2

        new Thread(this).start();
    }

    @Override
    public void run() {

        facts.offer(null, LOBBY_INFO);

        Map<GameStatus, Consumer<HashMap<ModelView, FactType>>> statusHandlers = Map.of(
                GameStatus.WAIT, this::safeStatusWait,
                GameStatus.FIRST_ROUND, this::safeStatusFirstRound,
                GameStatus.RUNNING, this::safeStatusRunning,
                GameStatus.SECOND_LAST_ROUND, this::safeStatusRunning,
                GameStatus.LAST_ROUND, this::safeStatusLastRound,
                GameStatus.ENDED, this::statusEnded
        );

        while (!Thread.currentThread().isInterrupted()) {
            Runnable event =
                    facts.isToHandle() ? () -> handleEvent(statusHandlers)
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

        if (map == null) {
            return;
        }
        if (map.containsKey(null)) {
            return;
        }

        ModelView model_view = map.keySet().iterator().next();

        if (model_view != null) {
            handlers.getOrDefault(model_view.getStatus(), this::doNothing).accept(map);
        }
    }

    private void handleFallbackEvent(Consumer<HashMap<ModelView, FactType>> fallback) {

        HashMap<ModelView, FactType> map = facts.poll();

        if (map != null) {
            fallback.accept(map);
        }
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

        Map<FactType, Runnable> actions = Map.of(
                FactType.LOBBY_INFO, () -> {
                    boolean selectionOk;
                    do {
                        selectionOk = askSelectGame();
                    } while (!selectionOk);
                },
                FactType.ALREADY_USED_NICKNAME, () -> {
                    nickname = null;
                    facts.offer(null, FactType.LOBBY_INFO);
                    ui.addImportantEvent("WARNING> Nickname already used!");
                },
                FactType.FULL_GAME, () -> {
                    nickname = null;
                    facts.offer(null, FactType.LOBBY_INFO);
                    ui.addImportantEvent("WARNING> Game is Full!");
                },
                FactType.GENERIC_ERROR, () -> {
                    nickname = null;
                    ui.show_returnToMenuMsg();
                    try {
                        this.generic_parser.getProcessedDataQueue().take();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
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
                    this.generic_parser.setPlayer(model.getPlayerEntity(nickname));
                    this.generic_parser.setGameId(model.getGameId());
                },
                FactType.NEXT_TURN, () -> {
                    ui.show_nextTurn(model, nickname);
                    if (model.getCurrentPlayerNickname().equals(nickname)) {
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
                    if (model.getCurrentPlayerNickname().equals(nickname)) {
                        ui.show_personalObjectiveCard(model);
                        ui.show_playerHand(model, nickname);
                        askWhichCard();
                    }
                },
                FactType.ASK_WHICH_ORIENTATION, () -> handleOrientationOrIllegalMove(map),
                FactType.ILLEGAL_MOVE, () -> handleOrientationOrIllegalMove(map),
                FactType.CARD_PLACED, () -> {
                    if (model.getCurrentPlayerNickname().equals(nickname)) {
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
                    if (model.getCurrentPlayerNickname().equals(nickname)) {
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
                    ui.show_returnToMenuMsg();
                    this.generic_parser.getProcessedDataQueue().clear();
                    try {
                        this.generic_parser.getProcessedDataQueue().take();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    this.leave(nickname, model.getGameId());
                    this.youLeft();
                }
        );

        actions.getOrDefault(type, () -> {}).run();
    }

    private void handleOrientationOrIllegalMove(HashMap<ModelView, FactType> map) {

        ModelView model = map.keySet().iterator().next();

        if (model.getCurrentPlayerNickname().equals(nickname)) {
            ui.show_cardChosen(nickname, model);
            askGameCardOrientationAndPlace();
        }
    }

    public void youLeft() {
        ui.resetImportantEvents();
        facts.offer(null, LOBBY_INFO);

        this.generic_parser.setPlayer(null);
        this.generic_parser.setGameId(null);
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
                if (temp.equals(".")) return -1;
                num_of_players = Integer.parseInt(temp);
            } catch (NumberFormatException e) {
                ui.show_NaNMsg();
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
        if (num_players < 2 || num_players > 4) return false;
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
        if (gameId == -1) return false;
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
                if (temp.equals(".")) return -1;
                gameId = Integer.parseInt(temp);
            } catch (NumberFormatException e) {
                ui.show_NaNMsg();
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

    // real getter from the buffer

    private String getProcessedData() {
        try {
            return this.generic_parser.getProcessedDataQueue().take();
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
            ui.show_genericMessage("Choose the ** " + coordinate + " ** coordinates where to place the card (insert a number between -250 and 250)");
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

    @Override
    public void createGame(String nickname, int num_of_players) {
        ui.show_creatingNewGameMsg(nickname);
        try {
            client_interface.createGame(nickname, num_of_players);
            System.out.println("Created game");
        } catch (IOException | InterruptedException | NotBoundException e) {
            StaticPrinter.staticPrinter("Error in here, createGame gameFlow!");
            noConnectionError();
        }
    }

    @Override
    public void joinRandomly(String nick) {
        ui.show_joiningFirstAvailableMsg(nick);
        try {
            client_interface.joinRandomly(nick);
        } catch (IOException | InterruptedException | NotBoundException e) {
            noConnectionError();
        }
    }


    @Override
    public void joinGame(String nick, int game_id) {
        ui.show_joiningToGameIdMsg(game_id, nick);
        try {
            client_interface.joinGame(nick, game_id);
        } catch (IOException | InterruptedException | NotBoundException e) {
            noConnectionError();
        }
    }

    @Override
    public void setAsReady() {
        try {
            client_interface.setAsReady();
        } catch (IOException e) {
            noConnectionError();
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void placeStarterCard(Orientation orientation) throws RemoteException, GameEndedException, NotBoundException {
        try {
            client_interface.placeStarterCard(orientation);
        } catch (GameEndedException | NotBoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void placeCard(int where_to_place_x, int where_to_place_y, Orientation orientation) throws RemoteException {
        try {
            client_interface.placeCard(where_to_place_x, where_to_place_y, orientation);
        } catch (GameEndedException | NotBoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void chooseCard(int which_card) throws RemoteException {
        try {
            client_interface.chooseCard(which_card);
        } catch (GameEndedException | NotBoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void drawCard(int index) throws RemoteException, GameEndedException {
        try {
            client_interface.drawCard(index);
        } catch (GameEndedException | NotBoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendMessage(String receiver, Message msg) {
        try {
            client_interface.sendMessage(receiver, msg);
        } catch (RemoteException e) {
            noConnectionError();
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void showOthersPersonalBoard(int player_index) {
        try {
            client_interface.showOthersPersonalBoard(player_index);
        } catch (IOException | NotBoundException e) {
            noConnectionError();
        }
    }

    @Override
    public void leave(String nick, int idGame) {
        try {
            client_interface.leave(nick, idGame);
        } catch (IOException | NotBoundException e) {
            noConnectionError();
        }
    }

    @Override
    public void ping() {

    }

    // Server --------> Client

    @Override
    public void starterCardPlaced(ModelView model, Orientation orientation, String nick) {
        if (model.getCurrentPlayerNickname().equals(nickname))
            ui.show_personalBoard(nickname, model);
    }

    @Override
    public void cardChosen(ModelView model, int which_card) {
        if (model.getCurrentPlayerNickname().equals(nickname)) {
            facts.offer(model, ASK_WHICH_ORIENTATION);
        }
    }

    @Override
    public void cardPlaced(ModelView model, int where_to_place_x, int where_to_place_y, Orientation orientation) {
        if (model.getCurrentPlayerNickname().equals(nickname)) {
            ui.show_personalBoard(nickname, model);
            ui.show_commonBoard(model);
            facts.offer(model, CARD_PLACED);
        }
    }

    @Override
    public void illegalMove(ModelView model) {
        if (model.getCurrentPlayerNickname().equals(nickname)) {
            ui.show_illegalMove();
            ui.show_genericMessage("Here we show you again your personal board:");
            ui.show_personalBoard(nickname, model);
            facts.offer(model, ILLEGAL_MOVE);
        }
    }

    @Override
    public void successfulMove(ModelView model) throws RemoteException {
        if (model.getCurrentPlayerNickname().equals(nickname)) {
            ui.show_successfulMove();
        }
    }

    @Override
    public void illegalMoveBecauseOf(ModelView model, String reason_why) {
        if (model.getCurrentPlayerNickname().equals(nickname)) {
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
    public void nextTurn(ModelView gameModel) {
        if (!gameModel.getCurrentPlayerNickname().equals(nickname) &&
                (gameModel.getStatus() == GameStatus.RUNNING ||
                        gameModel.getStatus() == GameStatus.SECOND_LAST_ROUND))
            ui.show_myTurnIsFinished();
        facts.offer(gameModel, FactType.NEXT_TURN);
        this.generic_parser.getProcessedDataQueue().clear();
    }

    @Override
    public void playerJoined(ModelView gameModel) {
        facts.offer(gameModel, FactType.PLAYER_JOINED);
        ui.show_playerJoined(gameModel, nickname);
    }

    @Override
    public void playerIsReadyToStart(ModelView gameModel, String nick) throws IOException {
        ui.show_playerJoined(gameModel, nickname);
        if (nick.equals(nickname)) {
            ui.show_ReadyToStart(gameModel, nickname);
        }
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

        if (gameModel.getStatus().equals(GameStatus.WAIT)) {
            ui.show_playerJoined(gameModel, nickname);
        }
    }

    @Override
    public void showOthersPersonalBoard(ModelView modelView, String playerNickname, int playerIndex) throws RemoteException {
        //se sono chi ha chiesto di vedere le personal board
        if(modelView.getAllPlayers().size() > playerIndex) {
            if (this.nickname.equals(playerNickname)) {
                ui.show_othersPersonalBoard(modelView, playerIndex);
            }
        }else{
            if (this.nickname.equals(playerNickname)) {
                ui.show_genericMessage("Player index out of bounds");
            }
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
