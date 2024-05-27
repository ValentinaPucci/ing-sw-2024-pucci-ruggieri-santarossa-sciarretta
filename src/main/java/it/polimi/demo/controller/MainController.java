package it.polimi.demo.controller;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.DefaultValues;
import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.model.exceptions.MaxPlayersLimitException;
import it.polimi.demo.model.exceptions.PlayerAlreadyConnectedException;
import it.polimi.demo.model.Player;
import it.polimi.demo.networking.rmi.remoteInterfaces.*;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;

import static it.polimi.demo.networking.PrintAsync.printAsync;
import static it.polimi.demo.networking.PrintAsync.printAsyncNoLine;

/**
 * MainController Class
 * It is the controller of the controllers, it manages all the available games that are running
 * Allowing players to create, join, reconnect, leave and delete games
 * Therefore, the MainController is unique across the app and thus implements the Singleton Pattern
 */
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
    public synchronized GameControllerInterface createGame(GameListener listener, String nickname, int num_of_players)
            throws RemoteException {

        int game_id;

        if (games.isEmpty())
            game_id = 0;
        else
            game_id = Collections.max(games.keySet()) + 1;

        Player player = new Player(nickname);
        GameController game = new GameController(game_id, num_of_players, player);
        // Here we add the player to the 'statical' list of players
        game.addListener(listener, player);
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
     * @param listener The GameListener of the player attempting to join the game.
     * @param nickname The nickname of the player attempting to join the game.
     * @param gameId The ID of the game to join.
     * @return The GameControllerInterface associated with the game if the player successfully joins,
     *         or null if the specific game does not exist or is unable to accept players.
     * @throws RemoteException If there is a communication-related issue during the method execution.
     */
    @Override
    public synchronized GameControllerInterface joinGame(GameListener listener, String nickname, int gameId)
            throws RemoteException {

        Player player = new Player(nickname);

        if (!games.containsKey(gameId) ||
                games.get(gameId).getStatus() != GameStatus.WAIT ||
                games.get(gameId).getNumPlayersToPlay() == games.get(gameId).getNumPlayers()) {
            listener.genericErrorWhenEnteringGame("Game not found or not available");
            return null;
        }

        games.get(gameId).addListener(listener, player);
        games.get(gameId).addPlayer(player);
        System.out.println("\t>Game " + games.get(gameId).getGameId() + " player:\"" + nickname + "\" entered player");
        printRunningGames();

        return games.get(gameId);
    }

    @Override
    public synchronized GameControllerInterface joinFirstAvailableGame(GameListener listener, String nickname)
            throws RemoteException {
        Optional<GameController> firstAvailableGame = games.values().stream()
                .filter(game -> game.getStatus() == GameStatus.WAIT && game.getNumPlayers() < DefaultValues.MaxNumOfPlayer)
                .findFirst();

        if (firstAvailableGame.isPresent()) {
            GameController game = firstAvailableGame.get();
            Player player = new Player(nickname);
            game.addListener(listener, player);
            game.addPlayer(player);
            System.out.println("\t>Game " + game.getGameId() + " player:\"" + nickname + "\" entered player");
            printRunningGames();
            return game;
        }

        listener.genericErrorWhenEnteringGame("No games currently available to join...");
        return null;
    }

    @Override
    public synchronized GameControllerInterface setAsReady(GameListener listener, String nickname, int gameId)
            throws RemoteException {
        games.get(gameId).playerIsReadyToStart(nickname);
        return games.get(gameId);
    }

    @Override
    public synchronized GameControllerInterface placeStarterCard(GameListener listener, String nickname, Orientation o, int gameId)
            throws RemoteException, GameEndedException {
        games.get(gameId).placeStarterCard(nickname, o);
        return games.get(gameId);
    }

    @Override
    public synchronized GameControllerInterface chooseCard(GameListener listener, String nickname, int cardIndex, int gameId)
            throws RemoteException, GameEndedException {
        games.get(gameId).chooseCardFromHand(nickname, cardIndex);
        return games.get(gameId);
    }

    @Override
    public synchronized GameControllerInterface placeCard(GameListener listener, String nickname, int x, int y, Orientation o, int gameId)
            throws RemoteException, GameEndedException {
        games.get(gameId).placeCard(nickname, x, y, o);
        return games.get(gameId);
    }

    @Override
    public synchronized GameControllerInterface drawCard(GameListener listener, String nickname, int index, int gameId)
            throws RemoteException, GameEndedException {
        games.get(gameId).drawCard(nickname, index);
        return games.get(gameId);
    }

//    /**
//     * Allows a player to join the first available game.
//     * If there are no available games (i.e., all games are either full or not in the waiting state),
//     * an error message is sent to the provided GameListener.
//     *
//     * @param listener The GameListener representing the player attempting to join the game.
//     * @param nickname The nickname of the player attempting to join the game.
//     * @return The GameControllerInterface associated with the game if the player successfully joins,
//     *         or null if no available games are found.
//     * @throws RemoteException If there is a communication-related issue during the method execution.
//     */
//    @Override
//    public GameControllerInterface joinFirstAvailableGame(GameListener listener, String nickname)
//            throws RemoteException {
//
//        Player player = new Player(nickname);
//
//        synchronized (games) {
//            Optional<GameController> firstAvailableGame = games.values().stream()
//                    .filter(game -> game.getStatus() == GameStatus.WAIT && game.getNumPlayers() < DefaultValues.MaxNumOfPlayer)
//                    .findFirst();
//
//            if (firstAvailableGame.isPresent()) {
//                GameController game = firstAvailableGame.get();
//                try {
//                    //game.addListener(listener, player);
//                    game.addPlayer(player.getNickname());
//                    // When a player joins a game, he/she is set also as connected!!
//                    game.setPlayerAsConnected(player);
//
//                    System.out.println("Player \"" + nickname + "\" joined Game " + game.getGameId());
//                    printRunningGames();
//
//                    return (GameControllerInterface) game;
//                } catch (MaxPlayersLimitException | PlayerAlreadyConnectedException e) {
//                    //game.removeListener(listener, player);
//                }
//            }
//        }
//
//        return null;
//    }

//    public synchronized GameControllerInterface joinGame(GameListener listener, String nickname, int gameId)
//            throws RemoteException {
//
//        Player player = new Player(nickname);
//        GameController game = games.get(gameId);
//
//        // todo: check if this code makes sense! In fact, it is 99% incorrect
//        if (game != null &&
//                (game.getStatus() == GameStatus.WAIT ||
//                        game.getStatus() == GameStatus.FIRST_ROUND ||
//                        game.getStatus() == GameStatus.RUNNING ||
//                        game.getStatus() == GameStatus.SECOND_LAST_ROUND ||
//                        game.getStatus() == GameStatus.LAST_ROUND) &&
//                game.getNumPlayers() < DefaultValues.MaxNumOfPlayer &&
//                game.getPlayers().stream().noneMatch(p -> p.getNickname().equals(nickname))) {
//
//            try {
//                // Here we add the player to the 'statical' list of players
//                game.addPlayer(player.getNickname());
//                // Here we add the player to the 'dynamic' list of players connected (the queue!)
//                game.setPlayerAsConnected(player);
//                notifyListeners(games.get(gameId).getModel().getListeners(), GameListener::playerJoinedGame);
//                System.out.println("\t>Game " + game.getGameId() + " player:\"" + nickname + "\" entered player");
//                printRunningGames();
//            } catch (MaxPlayersLimitException | PlayerAlreadyConnectedException e) {
//                // Handle exceptions if needed
//            }
//        }
//        return null;
//    }


    /**
     * Reconnects a player to a game specified by its ID.
     *
     * @param listener The GameListener of the player attempting to reconnect to the game.
     * @param nickname The nickname of the player attempting to reconnect to the game.
     * @param gameId The ID of the game to reconnect to.
     * @return The GameControllerInterface associated with the game if the player successfully reconnects,
     *         or null if the game does not exist or the player was not previously connected.
     * @throws RemoteException If there is a communication-related issue during the method execution.
     */
    @Override
    public synchronized GameControllerInterface reconnect(GameListener listener, String nickname, int gameId)
            throws RemoteException {

        return games.values().stream()
                .filter(game -> game.getGameId() == gameId)
                .findFirst()
                .flatMap(game -> game.getPlayers().stream()
                        .filter(player -> player.getNickname().equals(nickname))
                        .findFirst()
                        .map(player -> {
                            try {
                                //game.addListener(listener, player);
                                game.reconnectPlayer(player);
                                return game;
                            } catch (MaxPlayersLimitException | PlayerAlreadyConnectedException | RemoteException e) {
                                return null;
                            }
                        }))
                .orElseGet(() -> null);

    }

    /**
     * Allows a player to leave a game.
     *
     * @param listener The GameListener of the player who wants to leave.
     * @param nickname The nickname of the player who wants to leave.
     * @param gameId The ID of the game to leave.
     * @return The GameControllerInterface associated with the game.
     * @throws RemoteException If there is a communication-related issue during the method execution.
     */
    @Override
    public synchronized GameControllerInterface leaveGame(GameListener listener, String nickname, int gameId)
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