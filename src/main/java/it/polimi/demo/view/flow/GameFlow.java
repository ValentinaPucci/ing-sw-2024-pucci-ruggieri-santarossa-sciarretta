package it.polimi.demo.view.flow;

import it.polimi.demo.DefaultValues;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.*;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;
import it.polimi.demo.networking.rmi.RMIClient;
import it.polimi.demo.view.flow.utilities.*;
import it.polimi.demo.view.flow.utilities.events.EventElement;
import it.polimi.demo.view.flow.utilities.events.EventList;
import it.polimi.demo.view.flow.utilities.events.EventType;
import it.polimi.demo.view.text.TUI;
import it.polimi.demo.networking.socket.client.ClientSocket;
import org.fusesource.jansi.Ansi;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;

import static it.polimi.demo.networking.PrintAsync.printAsync;
import static it.polimi.demo.view.flow.utilities.events.EventType.*;

/**
 * GameFlow class<br>
 * As a workaround to the different GUI and TUI implementations, we created this class<br>
 * The TUI obviously has a different pace in the game that the GUI, as the TUI is more linear<br>
 * -> Only one action can be performed at any point in time, <br>
 * where the GUi is more complex, a message can be sent while the tiles are being picked up, and so on<br>
 * We opted for this class hat sets the pace of the game to be the same for both TUI and GUI.<br>
 * we have two additional threads that help us do so: InputReader and InputParser.<br>
 * GameFlow is directly connected to InputParser, which creates InputReader.<br>
 * InputReader reads from the input (in TUI) or matches any action performed by the client as a TUI input (in GUI)<br>
 * For example, if the player was to click on a tile in the GUI's playground, it would register the click as three inputs in TUI (row, column and direction)<br>
 * Then adds the data read to a buffer queue.<br>
 * <br>
 * InputParser then pops entries from this queue and parses them, to understand what each means.<br>
 * If he understands that the player wanted to send a message, the message is created and sent,<br>
 * if not, the data is sent to the gameFlow, which will then perform an action accordingly (pick up tiles, place tiles, ecc)<br>
 */
public class GameFlow extends Flow implements Runnable, CommonClientActions {

    /**
     * Nickname of the player {@link Player}
     */
    private String nickname;

    /**
     * The list of events {@link EventList}
     */
    private final EventList events = new EventList();

    /**
     * The action that the client can perform {@link CommonClientActions}
     */
    private CommonClientActions clientActions;
    /**
     * FileDisconnection {@link FileDisconnection} to handle the disconnection
     */
    private final FileDisconnection fileDisconnection;

    /**
     * The last player that reconnected
     */
    private String lastPlayerReconnected;
    private final UI ui;
    /**
     * InputReader {@link InputReader} to read the input, and add it to the buffer.
     * InputParser {@link InputParser} pops the input from the buffer and parses it
     */
    protected InputParser inputParser;
    protected InputReader inputReader;
    /**
     * Events that always need to be shown on the screen
     */
    protected List<String> importantEvents;
    private boolean ended = false;

    /**
     * Constructor of the class, based on the connection type it creates the clientActions and initializes the UI {@link UI}(TUI)
     * the FileDisconnection {@link FileDisconnection}, the InputReader {@link InputReader} and the InputParser {@link InputParser}
     *
     * @param connectionSelection the connection type
     */
    public GameFlow(ConnectionSelection connectionSelection) {
        //Invoked for starting with TUI
        switch (connectionSelection) {
            case SOCKET -> clientActions = new ClientSocket(this);
            case RMI -> clientActions = new RMIClient(this);
        }
        ui = new TUI();

        importantEvents = new ArrayList<>();
        nickname = "";
        fileDisconnection = new FileDisconnection();
        // Thread for reading the input (TUI)
        this.inputReader = new inputReaderTUI();
        // We bind inputParser and inputReader
        this.inputParser = new InputParser(this.inputReader.getBuffer(), this);

        new Thread(this).start();
    }

//    /**
//     * Constructor of the class, based on the connection type it creates the clientActions and initializes the UI {@link UI} (GUI)
//     *
//     * @param guiApplication      the GUI application {@link GUIApplication}
//     * @param connectionSelection the connection type {@link ConnectionSelection}
//     */
//    public GameFlow(GUIApplication guiApplication, ConnectionSelection connectionSelection) {
//        //Invoked for starting with GUI
//        switch (connectionSelection) {
//            // case SOCKET -> clientActions = new ClientSocket(this);
//            case RMI -> clientActions = new RMIClient(this);
//        }
//        this.inputReader = new inputReaderGUI();
//
//        ui = new GUI(guiApplication, (inputReaderGUI) inputReader);
//        importantEvents = new ArrayList<>();
//        nickname = "";
//        fileDisconnection = new FileDisconnection();
//
//        this.inputParser = new InputParser(this.inputReader.getBuffer(), this);
//        new Thread(this).start();
//    }

    /**
     * The gameFlow works with a list of events<br>
     * each event maps the status of the game, whether it's not started, running, in its last cycle or ended<br>
     */
    @SuppressWarnings("BusyWait")
    @Override
    public void run() {
        EventElement event;
        try {
            ui.show_publisher();
            events.add(null, APP_MENU);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        while (!Thread.interrupted()) {
            if (events.isJoined()) {
                // Get one event
                event = events.pop();
                if (event != null) {
                    // if something happened
                    switch (event.getModel().getStatus()) {
                        case WAIT -> {
                            try {
                                statusWait(event);
                            } catch (IOException | InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case FIRST_ROUND -> {
                            try {
                                statusFirstRound(event);
                            } catch (IOException | InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case RUNNING, LAST_ROUND -> {
                            try {
                                statusRunning(event);
                            } catch (IOException | InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case ENDED -> statusEnded(event);
                    }
                }
            } else {
                event = events.pop();
                if (event != null) {
                    statusNotInAGame(event);
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * This method is called when a player is not added to a game<br>
     *
     * @param event from here we get the updated model and can understand why the player was not put in a game<br>
     *              it says if he's just joined or if he's been kicked and why
     */
    private void statusNotInAGame(EventElement event) {

        switch (event.getType()) {

            case APP_MENU -> {
                boolean selectionok;
                do {
                    selectionok = askSelectGame();
                } while (!selectionok);
            }

            case JOIN_UNABLE_NICKNAME_ALREADY_IN -> {
                nickname = null;
                events.add(null, APP_MENU);
                ui.addImportantEvent("WARNING> Nickname already used!");
            }

            case JOIN_UNABLE_GAME_FULL -> {
                nickname = null;
                events.add(null, APP_MENU);
                ui.addImportantEvent("WARNING> Game is Full!");
            }

            case GENERIC_ERROR_WHEN_ENTERING_GAME -> {
                nickname = null;
                ui.show_returnToMenuMsg();
                try {
                    this.inputParser.getDataToProcess().popData();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                events.add(null, APP_MENU);
            }
        }
    }

    /**
     * The player is in this stage when he joins a lobby, but the game has not yet started<br>
     *
     * @param event from here we get the updated model and game status
     * @throws IOException          if there are problems with the input or output
     * @throws InterruptedException if there are problems with the connection
     */
    private void statusWait(EventElement event) throws IOException, InterruptedException {
        String nickLastPlayer = event.getModel().getPlayersConnected().getLast().getNickname();
        // If the event is that I joined then I wait until the user inputs 'y'
        switch (event.getType()) {
            case PLAYER_JOINED -> {
                if (nickLastPlayer.equals(nickname)) {
                    ui.show_playerJoined(event.getModel(), nickname);
                    saveGameId(fileDisconnection, nickname, event.getModel().getGameId());
                    askReadyToStart();
                }
            }
        }
    }

    private void statusFirstRound(EventElement event) throws IOException, InterruptedException {

//        switch (event.getType()) {
//
//        }
    }

    /**
     * The player is here for all the game's duration<br>
     * Each eventType maps a different action that can happen in the game, while it's running<br>
     *
     * @param event from here we get the updated model and game status
     * @throws IOException          if there are problems with the input or output
     * @throws InterruptedException if there are problems with the connection
     */
    private void statusRunning(EventElement event) throws IOException, InterruptedException {
        
        switch (event.getType()) {

            case GAME_STARTED -> {
                ui.show_gameStarted(event.getModel());
                this.inputParser.setPlayer(event.getModel().getPlayerEntity(nickname));
                this.inputParser.setIdGame(event.getModel().getGameId());
            }
            
            case MESSAGE_SENT -> {
                ui.show_messageSent(event.getModel(), nickname);
            }

            case NEXT_TURN, PLAYER_RECONNECTED -> {

            }

            case ASK_WHICH_CARD_TO_PLACE -> {

            }

            case ASK_WHICH_ORIENTATION -> {

            }

            case ASK_COORDINATES_TO_PLACE_CARD -> {

            }

            case ERRONEOUS_COORDINATES -> {

            }

            case CARD_PLACED -> {

            }

            case ASK_WHERE_TO_DRAW_CARD -> {

            }

        }

    }

    /**
     * The player is here once the game has ended<br>
     * Here the leaderboard will be shown, and then he'll be prompted with the choice to close the application <br>
     * or return to the main menu, to play another game<br>
     *
     * @param event from here we get the updated model and game status
     */
    private void statusEnded(EventElement event) {
        switch (event.getType()) {
            case GAME_ENDED -> {
                ui.show_returnToMenuMsg();
                //new Scanner(System.in).nextLine();
                this.inputParser.getDataToProcess().popAllData();
                try {
                    this.inputParser.getDataToProcess().popData();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                this.leave(nickname, event.getModel().getGameId());
                this.youLeft();
            }
        }
    }

    /**
     *
     */
    public void youLeft() {
        ended = true;
        ui.resetImportantEvents();
        events.add(null, APP_MENU);

        this.inputParser.setPlayer(null);
        this.inputParser.setIdGame(null);
    }

    /**
     * @return true if the game has ended, false otherwise
     */
    public boolean isEnded() {
        return ended;
    }

    /**
     * Sets the ended attribute
     *
     * @param ended true if the game has ended, false otherwise
     */
    public void setEnded(boolean ended) {
        this.ended = ended;
    }


    /*===============ASK METHODS===============*/

    /**
     * Asks the nickname to the player<br>
     */
    private void askNickname() {
        ui.show_insertNicknameMsg();
        try {
            nickname = this.inputParser.getDataToProcess().popData();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // ui.show_chosenNickname(nickname);
    }

    /**
     * Ask the player how many players he wants to play with

     * @return number of tiles to pick up
     */
    private Integer askNumOfPlayers() {
        String temp;
        Integer num_of_players = null;
        do {
            ui.show_insertNumOfPlayersMsg();
            try {
                try {
                    temp = this.inputParser.getDataToProcess().popData();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (temp.equals(".")) {
                    return -1;
                }
                num_of_players = Integer.parseInt(temp);
            } catch (NumberFormatException e) {
                ui.show_NaNMsg();
            }

        } while (num_of_players == null);
        ui.show_chosenNumOfPLayers(num_of_players);
        return num_of_players;
    }

    /**
     * Ask the player to select a game to join
     *
     * @return ture if the player has selected a game, false otherwise
     */
    private boolean askSelectGame() {
        String optionChoose;
        ended = false;
        ui.show_menuOptions();

        try {
            optionChoose = this.inputParser.getDataToProcess().popData();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (optionChoose.equals("."))
            System.exit(1);
        askNickname();

        switch (optionChoose) {
            case "c" -> {
                Integer num_players = askNumOfPlayers();
                if (num_players < 2 || num_players > 4)
                    return false;
                else
                    createGame(nickname, num_players);
            }
            case "js" -> {
                Integer gameId = askGameId();
                if (gameId == -1)
                    return false;
                else
                    joinGame(nickname, gameId);
            }

            case "x" -> reconnect(nickname, fileDisconnection.getLastGameId(nickname));

            default -> {
                return false;
            }
        }
        return true;
    }

    /**
     * Ask the player the game id to join
     *
     * @return the game id
     */
    private Integer askGameId() {
        String temp;
        Integer gameId = null;
        // ui.show_gamesList();
        do {
            ui.show_inputGameIdMsg();
            try {
                try {
                    temp = this.inputParser.getDataToProcess().popData();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (temp.equals(".")) {
                    return -1;
                }
                gameId = Integer.parseInt(temp);
            } catch (NumberFormatException e) {
                ui.show_NaNMsg();
            }

        } while (gameId == null);
        return gameId;
    }

    /**
     * Ask the player if it's ready to start the game
     */
    public void askReadyToStart() {
        String ris;
        do {
            try {
                ris = this.inputParser.getDataToProcess().popData();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while (!ris.equals("y"));
        setAsReady();
    }


    /*============ Methods that the client can request to the server ============*/

    /**
     * Throw a nonConnection error
     */
    public void noConnectionError() {
        ui.show_noConnectionError();
    }

    @Override
    public void createGame(String nickname, int num_of_players) {
        ui.show_creatingNewGameMsg(nickname);
        try {
            clientActions.createGame(nickname, num_of_players);
        } catch (IOException | InterruptedException | NotBoundException e) {
            noConnectionError();
        }
    }

    /**
     * The client asks the server to join a specific game
     *
     * @param nick   nickname of the player
     * @param game_id id of the game to join
     */
    @Override
    public void joinGame(String nick, int game_id) {
        ui.show_joiningToGameIdMsg(game_id, nick);
        try {
            clientActions.joinGame(nick, game_id);
        } catch (IOException | InterruptedException | NotBoundException e) {
            noConnectionError();
        }
    }

    /**
     * The client asks the server to reconnect to a specific game
     *
     * @param nick   nickname of the player
     * @param idGame id of the game to reconnect
     */
    @Override
    public void reconnect(String nick, int idGame) {
        if (idGame != -1) {
            ui.show_joiningToGameIdMsg(idGame, nick);
            try {
                clientActions.reconnect(nickname, idGame);
            } catch (IOException | InterruptedException | NotBoundException e) {
                noConnectionError();
            }
        } else {
            ui.show_noAvailableGamesToJoin("No disconnection previously detected");
            try {
                this.inputParser.getDataToProcess().popData();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            events.add(null, APP_MENU);
        }
    }

    /**
     * The client asks the server to leave the game
     *
     * @param nick   nickname of the player
     * @param idGame id of the game to leave
     */
    @Override
    public void leave(String nick, int idGame) {
        try {
            clientActions.leave(nick, idGame);
        } catch (IOException | NotBoundException e) {
            noConnectionError();
        }
    }

    /**
     * The client set himself as ready
     */
    @Override
    public void setAsReady() {
        try {
            clientActions.setAsReady();
        } catch (IOException e) {
            noConnectionError();
        }
    }

    @Override
    public boolean isMyTurn() {
        return false;
    }

    @Override
    public void placeStarterCard(Orientation orientation) throws RemoteException, GameEndedException, NotBoundException {

    }

    @Override
    public void chooseCard(int which_card) throws RemoteException {

    }

    @Override
    public void placeCard(int where_to_place_x, int where_to_place_y, Orientation orientation) throws RemoteException {

    }

    @Override
    public void drawCard(int index) throws RemoteException, GameEndedException {

    }

    @Override
    public void heartbeat() {

    }

    /**
     * The client asks the server to send a message
     *
     * @param msg message to send {@link Message}
     */
    @Override
    public void sendMessage(Message msg) {
        try {
            clientActions.sendMessage(msg);
        } catch (RemoteException e) {
            noConnectionError();
        }
    }

    /*============ Server event received ============*/

    @Override
    public void starterCardPlaced(GameModelImmutable model, Orientation orientation) {
        
    }

    @Override
    public void cardChosen(GameModelImmutable model, int which_card) {

    }

    @Override
    public void cardPlaced(GameModelImmutable model, int where_to_place_x, int where_to_place_y, Orientation orientation) {

    }

    @Override
    public void cardDrawn(GameModelImmutable model, int index) {

    }

    /**
     * A player has joined the game
     * @param gameModel game model {@link GameModelImmutable}
     */
    @Override
    public void playerJoined(GameModelImmutable gameModel) {
        //shared.setLastModelReceived(gameModel);
        events.add(gameModel, EventType.PLAYER_JOINED);
        //Print also here because: If a player is in askReadyToStart is blocked and cannot showPlayerJoined by watching the events
        ui.show_playerJoined(gameModel, nickname);
    }

    /**
     * A player has left the game
     * @param gamemodel game model {@link GameModelImmutable}
     * @param nick nickname of the player
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void playerLeft(GameModelImmutable gamemodel, String nick) throws RemoteException {
        if (gamemodel.getStatus().equals(GameStatus.WAIT)) {
            ui.show_playerJoined(gamemodel, nickname);
        } else {
            ui.addImportantEvent("[EVENT]: Player " + nick + " decided to leave the game!");
        }

    }

    /**
     * A player wanted to join a game but the game is full
     * @param wantedToJoin player that wanted to join
     * @param gameModel game model {@link GameModelImmutable}
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void joinUnableGameFull(Player wantedToJoin, GameModelImmutable gameModel) throws RemoteException {
        events.add(null, JOIN_UNABLE_GAME_FULL);
    }

    /**
     * A player reconnected to the game
     * @param gameModel game model {@link GameModelImmutable}
     * @param nickPlayerReconnected nickname of the player
     */
    @Override
    public void playerReconnected(GameModelImmutable gameModel, String nickPlayerReconnected) {
        lastPlayerReconnected = nickPlayerReconnected;
        events.add(gameModel, PLAYER_RECONNECTED);
        ui.addImportantEvent("[EVENT]: Player reconnected!");
        //events.add(gameModel, EventType.PLAYER_JOINED);
    }

    /**
     * A player has sent a message
     * @param gameModel game model {@link GameModelImmutable}
     * @param msg message sent {@link Message}
     */
    @Override
    public void messageSent(GameModelImmutable gameModel, Message msg) {
        //Show the message only if is for everyone or is for me (or I sent it)
        if (msg.whoIsReceiver().equals("*") || msg.whoIsReceiver().equalsIgnoreCase(nickname) || msg.getSender().getNickname().equalsIgnoreCase(nickname)) {
            // ui.addMessage(msg, gameModel);
            events.add(gameModel, MESSAGE_SENT);
            //msg.setText("[PRIVATE]: " + msg.getText());
        }
    }


    /**
     * A player wanted to join a game but the nickname is already in
     * @param wantedToJoin player that wanted to join {@link Player}
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {
        //System.out.println("[EVENT]: "+ wantedToJoin.getNickname() + " has already in");
        events.add(null, JOIN_UNABLE_NICKNAME_ALREADY_IN);
    }

    /**
     * A player wanted to join a game but the gameID is not valid
     * @param gameid game id
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void gameIdNotExists(int gameid) throws RemoteException {
        ui.show_noAvailableGamesToJoin("No currently game available with the following GameID: " + gameid);
        events.add(null, GENERIC_ERROR_WHEN_ENTERING_GAME);
    }

    /**
     * Generic error when entering a game
     * @param why why the error occurred
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void genericErrorWhenEnteringGame(String why) throws RemoteException {
        ui.show_noAvailableGamesToJoin(why);
        events.add(null, GENERIC_ERROR_WHEN_ENTERING_GAME);
    }

    /**
     * A player is ready to start
     * @param gameModel game model {@link GameModelImmutable}
     * @param nick nickname of the player
     * @throws IOException if the reference could not be accessed
     */
    @Override
    public void playerIsReadyToStart(GameModelImmutable gameModel, String nick) throws IOException {
        ui.show_playerJoined(gameModel, nickname);

        if (nick.equals(nickname)) {
            ui.show_youReadyToStart(gameModel, nickname);
        }
        // if(nick.equals(nickname))
        //    toldIAmReady=true;
        events.add(gameModel, PLAYER_IS_READY_TO_START);
    }

    /**
     * The game started
     * @param gameModel game model {@link GameModelImmutable}
     */
    @Override
    public void gameStarted(GameModelImmutable gameModel) {
        ui.addImportantEvent("All players are connected, the game will start soon!");
        events.add(gameModel, EventType.GAME_STARTED);
    }

    /**
     * The game ended
     * @param gameModel game model {@link GameModelImmutable}
     */
    @Override
    public void gameEnded(GameModelImmutable gameModel) {
        ended = true;
        events.add(gameModel, EventType.GAME_ENDED);
        ui.show_gameEnded(gameModel);
        resetGameId(fileDisconnection, gameModel);

    }

    /**
     * It adds the NextTurn event to the event list
     * @param gameModel game model {@link GameModelImmutable}
     */
    @Override
    public void nextTurn(GameModelImmutable gameModel) {
        events.add(gameModel, EventType.NEXT_TURN);

        //I remove all the input that the user sends when It is not his turn
        this.inputParser.getDataToProcess().popAllData();
    }

    /**
     * A player has been disconnected
     * @param gameModel game model {@link GameModelImmutable}
     * @param nick nickname of the player
     */
    @Override
    public void playerDisconnected(GameModelImmutable gameModel, String nick) {
        ui.addImportantEvent("Player " + nick + " has just disconnected");

        //Print also here because: If a player is in askReadyToStart is blocked and cannot showPlayerJoined by watching the events
        if (gameModel.getStatus().equals(GameStatus.WAIT)) {
            ui.show_playerJoined(gameModel, nickname);
        }
    }

    /**
     * Only one player is connected
     * @param gameModel game model {@link GameModelImmutable}
     * @param secondsToWaitUntilGameEnded seconds to wait until the game ends
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void onlyOnePlayerConnected(GameModelImmutable gameModel, int secondsToWaitUntilGameEnded) throws RemoteException {
        ui.addImportantEvent("Only one player is connected, waiting " + secondsToWaitUntilGameEnded + " seconds before calling Game Ended!");
    }

    /**
     * Last circle begins
     * @param gameModel game model {@link GameModelImmutable}
     * @throws RemoteException if the reference could not be accessed
     */
    @Override
    public void lastRound(GameModelImmutable gameModel) throws RemoteException {
        ui.addImportantEvent("Last round begins!");
    }


    /*==Testing purpose==*/
    
    @Deprecated
    public BufferData getBuffer_ForTesting() {
        return this.inputReader.getBuffer();
    }

    @Deprecated
    public boolean isEnded_ForTesting() {
        return this.ended;
    }

}
