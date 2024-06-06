package it.polimi.demo.view.flow;

import it.polimi.demo.model.ModelView;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.*;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.networking.rmi.RMIClient;
import it.polimi.demo.view.flow.utilities.*;
import it.polimi.demo.view.flow.utilities.gameFacts.ModelFact;
import it.polimi.demo.view.flow.utilities.gameFacts.FactQueue;
import it.polimi.demo.view.flow.utilities.gameFacts.FactType;
import it.polimi.demo.view.gui.ApplicationGUI;
import it.polimi.demo.view.gui.GUI;
import it.polimi.demo.networking.socket.client.ClientSocket;
import it.polimi.demo.view.text.TUI;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static it.polimi.demo.networking.PrintAsync.printAsync;
import static it.polimi.demo.view.flow.utilities.gameFacts.FactType.*;

public class GameDynamics extends Dynamics implements Runnable, ClientInterface {

    private String nickname;

    private int game_id;

    private final FactQueue facts = new FactQueue();

    private ClientInterface client_interface;

    private final UI ui;

    protected GenericParser generic_parser;

    protected AbstractReader abstract_reader;

    protected List<String> relevant_facts;

    private boolean ended = false;

    public GameDynamics(ConnectionSelection connectionSelection) {
        //Invoked for starting with TUI
        switch (connectionSelection) {
            case SOCKET -> client_interface = new ClientSocket(this);
            case RMI -> client_interface = new RMIClient(this);
        }
        ui = new TUI();

        relevant_facts = new ArrayList<>();
        nickname = "";
        // Thread for reading the input (TUI)
        this.abstract_reader = new TuiReader();
        // We bind generic_parser and abstract_reader
        this.generic_parser = new GenericParser(this.abstract_reader.getBuffer(), this);

        new Thread(this).start();
    }

    public GameDynamics(ApplicationGUI guiApplication, ConnectionSelection connectionSelection) {
        //Invoked for starting with GUI
        switch (connectionSelection) {
            case SOCKET -> client_interface = new ClientSocket(this);
            case RMI -> client_interface = new RMIClient(this);
        }

        this.abstract_reader = new GuiReader(); //GuiReader@5184ffd2

        ui = new GUI(guiApplication, (GuiReader) abstract_reader); //GuiReader@5184ffd2
        relevant_facts = new ArrayList<>();
        nickname = "";

        this.generic_parser = new GenericParser(this.abstract_reader.getBuffer(), this); //GuiReader@5184ffd2
        new Thread(this).start();
    }

    @Override
    public void run() {

        facts.add(null, APP_MENU);

        Map<GameStatus, Consumer<ModelFact>> statusHandlers = Map.of(
                GameStatus.WAIT, this::safeStatusWait,
                GameStatus.FIRST_ROUND, this::safeStatusFirstRound,
                GameStatus.RUNNING, this::safeStatusRunning,
                GameStatus.SECOND_LAST_ROUND, this::safeStatusRunning,
                GameStatus.LAST_ROUND, this::safeStatusLastRound,
                GameStatus.ENDED, this::statusEnded
        );

        while (!Thread.interrupted()) {
            if (facts.isJoined()) {
                handleEvent(statusHandlers);
            } else {
                handleFallbackEvent(this::safeStatusNotInAGame);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void handleEvent(Map<GameStatus, Consumer<ModelFact>> handlers) {
        ModelFact event = facts.pop();
        if (event != null) {
            handlers.getOrDefault(event.getModel().getStatus(), this::doNothing).accept(event);
        }
    }

    private void handleFallbackEvent(Consumer<ModelFact> fallback) {
        ModelFact event = facts.pop();
        if (event != null) {
            fallback.accept(event);
        }
    }

    private void safeStatusWait(ModelFact event) {
        try {
            statusWait(event);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void safeStatusFirstRound(ModelFact event) {
        try {
            statusFirstRound(event);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void safeStatusRunning(ModelFact event) {
        try {
            statusRunning(event);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void safeStatusLastRound(ModelFact event) {
        try {
            statusLastRound(event);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void safeStatusNotInAGame(ModelFact event) {
        statusNotInAGame(event);
    }

    private void doNothing(ModelFact event) {
        // No action required for undefined statuses
    }

    // GameDynamics

    private void statusNotInAGame(ModelFact fact) {
        Map<FactType, Runnable> actions = Map.of(
                FactType.APP_MENU, () -> {
                    boolean selectionOk;
                    do {
                        selectionOk = askSelectGame();
                    } while (!selectionOk);
                },
                FactType.JOIN_UNABLE_NICKNAME_ALREADY_IN, () -> {
                    nickname = null;
                    facts.add(null, FactType.APP_MENU);
                    ui.addImportantEvent("WARNING> Nickname already used!");
                },
                FactType.JOIN_UNABLE_GAME_FULL, () -> {
                    nickname = null;
                    facts.add(null, FactType.APP_MENU);
                    ui.addImportantEvent("WARNING> Game is Full!");
                },
                FactType.GENERIC_ERROR_WHEN_ENTERING_GAME, () -> {
                    nickname = null;
                    ui.show_returnToMenuMsg();
                    try {
                        this.generic_parser.getProcessedDataQueue().take();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    facts.add(null, FactType.APP_MENU);
                }
        );

        actions.getOrDefault(fact.getType(), () -> {}).run();
    }

    private void statusWait(ModelFact fact) throws IOException, InterruptedException {
        Map<FactType, Runnable> actions = Map.of(
                FactType.PLAYER_JOINED, () -> {
                    String nickLastPlayer = fact.getModel().getPlayersConnected().getLast().getNickname();
                    if (nickLastPlayer.equals(nickname)) {
                        ui.show_playerJoined(fact.getModel(), nickname);
                        askReadyToStart();
                    }
                }
        );

        actions.getOrDefault(fact.getType(), () -> {}).run();
    }

    private void statusFirstRound(ModelFact fact) throws IOException, InterruptedException {
        Map<FactType, Runnable> actions = Map.of(
                FactType.GAME_STARTED, () -> {
                    ui.show_gameStarted(fact.getModel());
                    this.generic_parser.setPlayer(fact.getModel().getPlayerEntity(nickname));
                    this.generic_parser.setGameId(fact.getModel().getGameId());
                },
                FactType.NEXT_TURN, () -> {
                    ui.show_nextTurn(fact.getModel(), nickname);
                    if (fact.getModel().getCurrentPlayerNickname().equals(nickname)) {
                        ui.show_objectiveCards(fact.getModel());
                        askWhichObjectiveCard();
                        ui.show_starterCards(fact.getModel());
                        askStarterCardOrientationAndPlace();
                    }
                }
        );

        actions.getOrDefault(fact.getType(), () -> {}).run();
    }

    private void statusRunning(ModelFact fact) throws IOException, InterruptedException {
        Map<FactType, Runnable> actions = Map.of(
                FactType.NEXT_TURN, () -> {
                    if (fact.getModel().getCurrentPlayerNickname().equals(nickname)) {
                        ui.show_personalObjectiveCard(fact.getModel());
                        ui.show_playerHand(fact.getModel(), nickname);
                        askWhichCard();
                    }
                },
                FactType.ASK_WHICH_ORIENTATION, () -> handleOrientationOrIllegalMove(fact),
                FactType.ILLEGAL_MOVE, () -> handleOrientationOrIllegalMove(fact),
                FactType.CARD_PLACED, () -> {
                    if (fact.getModel().getCurrentPlayerNickname().equals(nickname)) {
                        askWhereToDrawFrom();
                    }
                }
        );

        actions.getOrDefault(fact.getType(), () -> {}).run();
    }

    private void statusLastRound(ModelFact fact) throws IOException, InterruptedException {
        Map<FactType, Runnable> actions = Map.of(
                FactType.NEXT_TURN, () -> {
                    if (fact.getModel().getCurrentPlayerNickname().equals(nickname)) {
                        ui.show_personalObjectiveCard(fact.getModel());
                        ui.show_playerHand(fact.getModel(), nickname);
                        askWhichCard();
                    }
                },
                FactType.ASK_WHICH_ORIENTATION, () -> handleOrientationOrIllegalMove(fact),
                FactType.ILLEGAL_MOVE, () -> handleOrientationOrIllegalMove(fact)
        );

        actions.getOrDefault(fact.getType(), () -> {}).run();
    }

    private void statusEnded(ModelFact fact) {
        Map<FactType, Runnable> actions = Map.of(
                FactType.GAME_ENDED, () -> {
                    ui.show_returnToMenuMsg();
                    this.generic_parser.getProcessedDataQueue().clear();
                    try {
                        this.generic_parser.getProcessedDataQueue().take();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    this.leave(nickname, fact.getModel().getGameId());
                    this.youLeft();
                }
        );

        actions.getOrDefault(fact.getType(), () -> {}).run();
    }

    private void handleOrientationOrIllegalMove(ModelFact fact) {
        if (fact.getModel().getCurrentPlayerNickname().equals(nickname)) {
            ui.show_cardChosen(nickname, fact.getModel());
            askGameCardOrientationAndPlace();
        }
    }


    public void youLeft() {
        ended = true;
        ui.resetImportantEvents();
        facts.add(null, APP_MENU);

        this.generic_parser.setPlayer(null);
        this.generic_parser.setGameId(null);
    }

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
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
        joinFirstAvailableGame(nickname);
        return true;
    }

    private boolean handleJoinSpecificGame() {
        Integer gameId = askGameId();
        if (gameId == -1) return false;
        this.game_id = gameId;
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

    /*============ Methods that the client can request to the server ============*/

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
            printAsync("Error in here, createGame gameFlow!");
            noConnectionError();
        }
    }

    @Override
    public void joinFirstAvailableGame(String nick) {
        ui.show_joiningFirstAvailableMsg(nick);
        try {
            client_interface.joinFirstAvailableGame(nick);
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

    //-----------------------------RUNNING METHODS-----------------------------------------

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
    public void leave(String nick, int idGame) {
        try {
            client_interface.leave(nick, idGame);
        } catch (IOException | NotBoundException e) {
            noConnectionError();
        }
    }

    @Override
    public void heartbeat() {

    }

    /*============ Server event received ============*/

    @Override
    public void starterCardPlaced(ModelView model, Orientation orientation, String nick) {
        if (model.getCurrentPlayerNickname().equals(nickname))
            ui.show_personalBoard(nickname, model);
    }

    @Override
    public void cardChosen(ModelView model, int which_card) {
        if (model.getCurrentPlayerNickname().equals(nickname)) {
            facts.add(model, ASK_WHICH_ORIENTATION);
        }
    }

    @Override
    public void cardPlaced(ModelView model, int where_to_place_x, int where_to_place_y, Orientation orientation) {
        if (model.getCurrentPlayerNickname().equals(nickname)) {
            ui.show_personalBoard(nickname, model);
            ui.show_commonBoard(model);
            facts.add(model, CARD_PLACED);
        }
    }

    @Override
    public void illegalMove(ModelView model) {
        if (model.getCurrentPlayerNickname().equals(nickname)) {
            ui.show_illegalMove();
            ui.show_genericMessage("Here we show you again your personal board:");
            ui.show_personalBoard(nickname, model);
            facts.add(model, ILLEGAL_MOVE);
        }
    }

//    @Override
//    public void successfulMove(ModelView modelView) throws RemoteException {
//
//    }

    @Override
    public void illegalMoveBecauseOf(ModelView model, String reason_why) {
        if (model.getCurrentPlayerNickname().equals(nickname)) {
            ui.show_illegalMoveBecauseOf(reason_why);
            ui.show_personalBoard(nickname, model);
            facts.add(model, ILLEGAL_MOVE);
        }
    }

    @Override
    public void cardDrawn(ModelView model, int index) {
        if (model.getCurrentPlayerNickname().equals(nickname)) {

        }
    }

    @Override
    public void nextTurn(ModelView gameModel) {
        if (!gameModel.getCurrentPlayerNickname().equals(nickname) &&
                (gameModel.getStatus() == GameStatus.RUNNING ||
                        gameModel.getStatus() == GameStatus.SECOND_LAST_ROUND))
            ui.show_myTurnIsFinished();
        facts.add(gameModel, FactType.NEXT_TURN);
        //I remove all the input that the user sends when It is not his turn
        this.generic_parser.getProcessedDataQueue().clear();
    }

    @Override
    public void playerJoined(ModelView gameModel) {
        // shared.setLastModelReceived(gameModel);
        game_id = gameModel.getGameId();
        facts.add(gameModel, FactType.PLAYER_JOINED);
        //Print also here because: If a player is in askReadyToStart is blocked and cannot showPlayerJoined by watching the gameFacts
        ui.show_playerJoined(gameModel, nickname);
    }

    @Override
    public void playerIsReadyToStart(ModelView gameModel, String nick) throws IOException {
        ui.show_playerJoined(gameModel, nickname);
        if (nick.equals(nickname)) {
            ui.show_ReadyToStart(gameModel, nickname);
        }
        facts.add(gameModel, PLAYER_IS_READY_TO_START);
    }

    @Override
    public void playerLeft(ModelView gameModel, String nick) throws RemoteException {
        ui.addImportantEvent("[EVENT]: Player " + nick + " decided to leave the game!");
    }

    @Override
    public void joinUnableGameFull(Player wantedToJoin, ModelView gameModel) throws RemoteException {
        facts.add(null, JOIN_UNABLE_GAME_FULL);
    }

//    @Override
//    public void playerReconnected(ModelView gamemodel, String nickPlayerReconnected) throws RemoteException {
//
//    }

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
        //System.out.println("[EVENT]: "+ wantedToJoin.getNickname() + " has already in");
        facts.add(null, JOIN_UNABLE_NICKNAME_ALREADY_IN);
    }

    @Override
    public void gameIdNotExists(int gameid) throws RemoteException {
        ui.show_noAvailableGamesToJoin("No currently game available with the following GameID: " + gameid);
        facts.add(null, GENERIC_ERROR_WHEN_ENTERING_GAME);
    }

    @Override
    public void genericErrorWhenEnteringGame(String why) throws RemoteException {
        ui.show_noAvailableGamesToJoin(why);
        facts.add(null, GENERIC_ERROR_WHEN_ENTERING_GAME);
    }

    @Override
    public void gameStarted(ModelView gameModel) {
        ui.addImportantEvent("All players are connected, the game will start soon!");
        facts.add(gameModel, FactType.GAME_STARTED);
    }

    @Override
    public void gameEnded(ModelView gameModel) {
        ended = true;
        facts.add(gameModel, FactType.GAME_ENDED);
        ui.show_gameEnded(gameModel);
    }

    @Override
    public void playerDisconnected(ModelView gameModel, String nick) {
        ui.addImportantEvent("Player " + nick + " has just disconnected");

        if (gameModel.getStatus().equals(GameStatus.WAIT)) {
            ui.show_playerJoined(gameModel, nickname);
        }
    }

//    @Override
//    public void onlyOnePlayerConnected(ModelView gameModel, int secondsToWaitUntilGameEnded) throws RemoteException {
//
//    }

    @Override
    public void secondLastRound(ModelView gameModel) {
        ui.show_genericMessage("*** Second last round begins! ***");
    }

    @Override
    public void lastRound(ModelView gameModel) throws RemoteException {
        ui.show_genericMessage("*** Last round begins! Now you will not be able to draw any additional card! ***");
    }

}
