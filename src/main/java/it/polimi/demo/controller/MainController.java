package it.polimi.demo.controller;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.DefaultValues;
import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.model.exceptions.MaxPlayersLimitException;
import it.polimi.demo.model.exceptions.PlayerAlreadyConnectedException;
import it.polimi.demo.model.Player;
import it.polimi.demo.networking.rmi.remoteInterfaces.*;
import it.polimi.demo.view.GameDetails;

import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * MainController Class
 * It is the controller of the controllers, it manages all the available games that are running
 * Allowing players to create, join, reconnect, leave and delete games
 * Therefore, the MainController is unique across the app and thus implements the Singleton Pattern
 */
public class MainController implements MainControllerInterface {

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
    private final Map<Integer, GameController> games;

    /**
     * Init an empty List of GameController
     * For implementing AF: "Multiple games"
     */
    public MainController() {
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

    public Map<Integer, GameController> getGames() {
        return games;
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
    public GameControllerInterface createGame(GameListener listener, String nickname, int num_of_players)
            throws RemoteException {

        int game_id;

        if (games.isEmpty())
            game_id = 0;
        else
            game_id = Collections.max(games.keySet()) + 1;

        Player player = new Player(nickname);
        GameController game = new GameController(game_id, num_of_players, new Player(nickname));
        game.addListener(listener, player);

        synchronized (games) {
            // By adding this check, we avoid the possibility of having two games with the same ID.
            if (games.containsKey(game_id)) {
                throw new RuntimeException("Game ID already exists");
            }
            games.put(game_id, game);
            games.get(game_id).setNumPlayersToPlay(num_of_players);
        }

        System.out.println("\t>Player:\" " + nickname + " \"" + " created game " + game_id);
        printRunningGames();

        // Here we add the player to the 'statical' list of players
        game.addPlayer(player.getNickname());
        System.out.println("Player added to the game: "
                + player.getNickname() + " "
                + game.getPlayers().size() + " "
                + game.getNumPlayersToPlay());
        // Here we add the player to the 'dynamic' list of players connected (the queue!)
        game.setPlayerAsConnected(player);

        return game;
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
        GameController game = games.get(gameId);

        return Optional.ofNullable(game)
                .filter(g -> g.getStatus() == GameStatus.WAIT ||
                        g.getStatus() == GameStatus.READY_TO_START ||
                        g.getStatus() == GameStatus.FIRST_ROUND ||
                        g.getStatus() == GameStatus.RUNNING ||
                        g.getStatus() == GameStatus.SECOND_LAST_ROUND ||
                        g.getStatus() == GameStatus.LAST_ROUND)
                .filter(g -> g.getNumPlayers() < DefaultValues.MaxNumOfPlayer &&
                        g.getPlayers().stream().noneMatch(p -> p.getNickname().equals(nickname)))
                .map(g -> {
                    try {
                        // todo: check if we add correctly the listener
                        g.addListener(listener, player);
                        // Here we add the player to the 'statical' list of players
                        g.addPlayer(player.getNickname());
                        // Here we add the player to the 'dynamic' list of players connected (the queue!)
                        g.setPlayerAsConnected(player);
                        System.out.println("\t>Game " + g.getGameId() + " player:\"" + nickname + "\" entered player");
                        printRunningGames();
                        return g;
                    } catch (MaxPlayersLimitException | PlayerAlreadyConnectedException e) {
                        return null;
                    }
                })
                .orElse(null);

    }

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
                                game.getModel().reconnectPlayer(player);
                                return game;
                            } catch (MaxPlayersLimitException | PlayerAlreadyConnectedException e) {
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
     * Prints the IDs of all games currently running.
     */
    private void printRunningGames() {
        System.out.println("RUNNING GAMES: ");
        if(games.isEmpty()){
            System.out.println("No running games available");
        }
        games.values().forEach(game -> System.out.println( "GAME #" + game.getGameId() + " "));
        System.out.println("");
    }


    @Override
    public List<GameDetails> getGamesDetails() {
        return games.values().stream()
                .map(gameController -> new GameDetails(
                        gameController.getModel().getGameId(),
                        gameController.getModel().getPlayersDetails(),
                        gameController.getModel().getNumPlayersToPlay(),
                        gameController.getModel().isStarted(),
                        gameController.getModel().isFull()))
                .collect(Collectors.toList());
    }

}