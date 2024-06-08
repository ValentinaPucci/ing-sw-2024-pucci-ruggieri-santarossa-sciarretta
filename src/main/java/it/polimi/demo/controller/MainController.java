package it.polimi.demo.controller;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.DefaultValues;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.model.Player;
import it.polimi.demo.networking.remoteInterfaces.GameControllerInterface;
import it.polimi.demo.networking.remoteInterfaces.MainControllerInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;

import static it.polimi.demo.networking.PrintAsync.printAsync;
import static it.polimi.demo.networking.PrintAsync.printAsyncNoLine;

/**
 * MainController Class
 * It is the controller of the controllers, it manages all the available games that are running
 * Allowing players to create, join, reconnect, leave and delete games
 * Therefore, the MainController is unique across the app and thus implements the Singleton Pattern
 */
// todo: from main controller down to all other classes javadocs must be checked
public class MainController implements MainControllerInterface, Serializable {

    /**
     * Singleton Pattern, instance of the class
     */
    private static MainController instance = null;

    /**
     * Map of games which are currently running. For simplicity, the key is the game ID
     * and the value is the GameController associated to the game. By doing so, we can easily retrieve
     * the GameController of a specific game by its ID
     * For implementing AF: "Multiple games"
     */
    private Map<Integer, GameController> games;

    /**
     * Init an empty List of GameController
     * For implementing AF: "Multiple games"
     */
    private MainController() {
        games = new HashMap<>();
    }

    /**
     * Singleton Pattern
     *
     * @return the only one instance of the MainController class
     */
    public synchronized static MainController getControllerInstance() {
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }

    /**
     * Every player can create a game. By doing so, he/her joins the game as the first player
     *
     * @param listener of the player who is creating the game
     * @param nickname  of the player who is creating the game
     * @return GameControllerInterface associated to the created game
     * @throws RemoteException if the connection fails
     */
    @Override
    public synchronized GameControllerInterface createGame(Listener listener, String nickname, int num_of_players)
            throws RemoteException {

        int game_id;

        if (games.isEmpty())
            game_id = 0;
        else
            game_id = Collections.max(games.keySet()) + 1;

        Player player = new Player(nickname);
        GameController game = new GameController(game_id, num_of_players, player);
        // Here we add the player to the 'statical' list of players
        game.addListener(listener);
        game.addPlayer(player);

        games.put(game_id, game);
        System.out.println(games.get(game_id).getStatus());
        games.get(game_id).setNumPlayersToPlay(num_of_players);

        printAsync("\t>Player:\" " + nickname + " \"" + " created game " + game_id);
        printRunningGames();

        return game;
    }

    /**
     * Allows a player to join a specific game by its ID.
     *
     * @param listener The Listener of the player attempting to join the game.
     * @param nickname The nickname of the player attempting to join the game.
     * @param gameId The ID of the game to join.
     * @return The GameControllerInterface associated with the game if the player successfully joins,
     *         or null if the specific game does not exist or is unable to accept players.
     * @throws RemoteException If there is a communication-related issue during the method execution.
     */
    @Override
    public synchronized GameControllerInterface joinGame(Listener listener, String nickname, int gameId)
            throws RemoteException {

        Player player = new Player(nickname);

        if (!games.containsKey(gameId) ||
                games.get(gameId).getStatus() != GameStatus.WAIT ||
                games.get(gameId).getNumPlayersToPlay() == games.get(gameId).getNumPlayers()) {
            listener.genericErrorWhenEnteringGame("Game not found or not available");
            return null;
        }

        games.get(gameId).addListener(listener);
        games.get(gameId).addPlayer(player);
        System.out.println("\t>Game " + games.get(gameId).getGameId() + " player:\"" + nickname + "\" entered player");
        printRunningGames();

        return games.get(gameId);
    }

    /**
     * Allows a player to join the first available game.
     * @param listener The Listener representing the player attempting to join the game.
     * @param nickname The nickname of the player attempting to join the game.
     * @return The GameControllerInterface associated with the game if the player successfully joins,
     * @throws RemoteException If there is a communication-related issue during the method execution.
     */
    @Override
    public synchronized GameControllerInterface joinFirstAvailableGame(Listener listener, String nickname)
            throws RemoteException {
        Optional<GameController> firstAvailableGame = games.values().stream()
                .filter(game -> game.getStatus() == GameStatus.WAIT && game.getNumPlayers() < DefaultValues.MaxNumOfPlayer)
                .findFirst();

        if (firstAvailableGame.isPresent()) {
            GameController game = firstAvailableGame.get();
            Player player = new Player(nickname);
            game.addListener(listener);
            game.addPlayer(player);
            System.out.println("\t>Game " + game.getGameId() + " player:\"" + nickname + "\" entered player");
            printRunningGames();
            return game;
        }
        listener.genericErrorWhenEnteringGame("No games currently available to join...");
        return null;
    }

    @Override
    public synchronized GameControllerInterface setAsReady(Listener listener, String nickname, int gameId)
            throws RemoteException {
        games.get(gameId).playerIsReadyToStart(nickname);
        return games.get(gameId);
    }

    @Override
    public synchronized GameControllerInterface placeStarterCard(Listener listener, String nickname, Orientation o, int gameId)
            throws RemoteException, GameEndedException {
        games.get(gameId).placeStarterCard(nickname, o);
        return games.get(gameId);
    }

    @Override
    public synchronized GameControllerInterface chooseCard(Listener listener, String nickname, int cardIndex, int gameId)
            throws RemoteException, GameEndedException {
        games.get(gameId).chooseCardFromHand(nickname, cardIndex);
        return games.get(gameId);
    }

    @Override
    public synchronized GameControllerInterface placeCard(Listener listener, String nickname, int x, int y, Orientation o, int gameId)
            throws RemoteException, GameEndedException {
        games.get(gameId).placeCard(nickname, x, y, o);
        return games.get(gameId);
    }

    @Override
    public synchronized GameControllerInterface drawCard(Listener listener, String nickname, int index, int gameId)
            throws RemoteException, GameEndedException {
        games.get(gameId).drawCard(nickname, index);
        return games.get(gameId);
    }

    @Override
    public synchronized GameControllerInterface sendMessage(Listener listener, String nickname, Message message, int gameId)
            throws RemoteException {
        games.get(gameId).sendMessage(nickname, message);
        return games.get(gameId);
    }

    @Override
    public synchronized void addPing(Listener listener, String nickname, int gameId)
            throws RemoteException {
        if (games.containsKey(gameId))
            games.get(gameId).addPing(nickname, listener);
    }

    /**
     * Allows a player to leave a game.
     *
     * @param listener The Listener of the player who wants to leave.
     * @param nickname The nickname of the player who wants to leave.
     * @param gameId The ID of the game to leave.
     * @return The GameControllerInterface associated with the game.
     * @throws RemoteException If there is a communication-related issue during the method execution.
     */
    @Override
    public synchronized GameControllerInterface leaveGame(Listener listener, String nickname, int gameId)
            throws RemoteException, RuntimeException {
        return games.values().stream()
                .filter(game -> game.getGameId() == gameId)
                .findFirst()
                .map(game -> {
                    try {
                        game.leave(listener, nickname);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("\t>Game " + game.getGameId() + " player: \"" + nickname + "\" decided to leave");
                    printRunningGames();

                    if (game.getConnectedPlayers().isEmpty()) {
                        deleteGame(gameId);
                    }
                    return game;
                })
                .orElse(null);
    }

    /**
     * Removes the game with the specified ID from the MainController's games.
     *
     * @param gameId The ID of the game to delete.
     */
    public synchronized void deleteGame(int gameId) {

        games.values().stream()
                .filter(game -> game.getGameId() == gameId)
                .findFirst()
                .ifPresent(game -> {
                    games.remove(gameId);
                    System.out.println("\t>Game " + gameId + " removed from games");
                    printRunningGames();
                });
    }

    /**
     * Print all games currently running
     */
    private void printRunningGames() {
        printAsyncNoLine("\t\trunningGames: ");
        for (Integer n : games.keySet()) {
            printAsync(n + " ");
        }
        printAsync("");
    }

    public synchronized GameControllerInterface getGameController(int gameId) {
        return games.get(gameId);
    }

}