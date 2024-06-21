package it.polimi.demo.controller;

import it.polimi.demo.network.GameControllerInterface;
import it.polimi.demo.network.MainControllerInterface;
import it.polimi.demo.observer.Listener;
import it.polimi.demo.Constants;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.model.Player;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The MainController class manages game instances and player interactions.
 * It implements the MainControllerInterface and handles game creation, joining,
 * and player actions within the game.
 */
public class MainController implements MainControllerInterface, Serializable {

    @Serial
    private static final long serialVersionUID = -2625500969528591903L;

    /**
     * Map of ongoing matches.
     */
    private final Map<Integer, GameController> games = new ConcurrentHashMap<>();

    /**
     * Private constructor to prevent instantiation.
     */
    private MainController() {}

    /**
     * Helper class for singleton pattern.
     */
    private static class SingletonHelper {
        private static final MainController INSTANCE = new MainController();
    }

    /**
     * Returns the singleton instance of MainController.
     *
     * @return the singleton instance
     */
    public static MainController getControllerInstance() {
        return SingletonHelper.INSTANCE;
    }

    /**
     * Auxiliary method to offer a player to a game.
     *
     * @param g the game
     * @param p the player
     * @param l the listener
     */
    public void aux_adder(GameController g, Player p, Listener l) {
        g.addListener(l);
        g.addPlayer(p);
    }

    /**
     * Creates a new game.
     *
     * @param listener the listener
     * @param nickname the nickname of the player
     * @param num_of_players the number of players
     * @return the game controller
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
        GameController game = new GameController(game_id, num_of_players);
        aux_adder(game, player, listener);
        games.put(game_id, game);
        games.get(game_id).setNumPlayersToPlay(num_of_players);

        return game;
    }

    /**
     * Joins a game.
     *
     * @param listener the listener
     * @param nickname the nickname of the player
     * @param gameId the id of the game
     * @return the game controller
     * @throws RemoteException if the connection fails
     */
    @Override
    public synchronized GameControllerInterface joinGame(Listener listener, String nickname, int gameId)
            throws RemoteException {

        Player player = new Player(nickname);

        if (!games.containsKey(gameId)) {
            listener.genericErrorWhenEnteringGame("Game not found");
            return null;
        } else if (games.get(gameId).getStatus() != GameStatus.WAIT) {
            listener.genericErrorWhenEnteringGame("Game currently not available: already started");
            return null;
        } else if (games.get(gameId).getNumPlayersToPlay() == games.get(gameId).getNumPlayers()) {
            listener.genericErrorWhenEnteringGame("Game is full");
            return null;
        } else if (games.get(gameId).getConnectedPlayers().stream().anyMatch(p -> p.getNickname().equals(nickname))) {
            listener.genericErrorWhenEnteringGame("Nickname already in use in this game");
            return null;
        }

        aux_adder(games.get(gameId), player, listener);

        return games.get(gameId);
    }

    /**
     * Joins a game randomly.
     *
     * @param listener the listener
     * @param nickname the nickname of the player
     * @return the game controller
     * @throws RemoteException if the connection fails
     */
    @Override
    public synchronized GameControllerInterface joinRandomly(Listener listener, String nickname)
            throws RemoteException {
        Optional<GameController> av_game = games.values().stream()
                .filter(game -> game.getStatus() == GameStatus.WAIT && game.getNumPlayers() < Constants.MaxNumOfPlayer)
                .filter(game -> game.getConnectedPlayers().stream().noneMatch(p -> p.getNickname().equals(nickname)))
                .findFirst();
        if (av_game.isPresent()) {
            GameController game = av_game.get();
            Player player = new Player(nickname);
            aux_adder(game, player, listener);
            return game;
        } else {
            listener.genericErrorWhenEnteringGame("No games currently available to join...");
            return null;
        }
    }

    /**
     * Sets a player as ready.
     *
     * @param lis the listener
     * @param nickname the nickname of the player
     * @param gameId the id of the game
     * @throws RemoteException if the connection fails
     */
    @Override
    public synchronized void setAsReady(Listener lis, String nickname, int gameId)
            throws RemoteException {
        games.get(gameId).playerIsReadyToStart(nickname);
    }

    /**
     * Places the starter card.
     *
     * @param listener the listener
     * @param nickname the nickname of the player
     * @param o the orientation
     * @param gameId the id of the game
     * @throws RemoteException if the connection fails
     * @throws GameEndedException if the game ends
     */
    @Override
    public synchronized void placeStarterCard(Listener listener, String nickname, Orientation o, int gameId)
            throws RemoteException, GameEndedException {
        games.get(gameId).placeStarterCard(nickname, o);
    }

    /**
     * Chooses a card from the hand.
     *
     * @param listener the listener
     * @param nickname the nickname of the player
     * @param cardIndex the index of the card
     * @param gameId the id of the game
     * @throws RemoteException if the connection fails
     */
    @Override
    public synchronized void chooseCard(Listener listener, String nickname, int cardIndex, int gameId)
            throws RemoteException {
        games.get(gameId).chooseCardFromHand(nickname, cardIndex);
    }

    /**
     * Places a card on the game board.
     *
     * @param listener the listener
     * @param nickname the nickname of the player
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param o the orientation
     * @param gameId the id of the game
     * @throws RemoteException if the connection fails
     * @throws GameEndedException if the game ends
     */
    @Override
    public synchronized void placeCard(Listener listener, String nickname, int x, int y, Orientation o, int gameId)
            throws RemoteException, GameEndedException {
        games.get(gameId).placeCard(nickname, x, y, o);
    }

    /**
     * Draws a card.
     *
     * @param listener the listener
     * @param nickname the nickname of the player
     * @param index the index of the card
     * @param gameId the id of the game
     * @throws RemoteException if the connection fails
     * @throws GameEndedException if the game ends
     */
    @Override
    public synchronized void drawCard(Listener listener, String nickname, int index, int gameId)
            throws RemoteException, GameEndedException {
        games.get(gameId).drawCard(nickname, index);
    }

    /**
     * Sends a message in the game chat.
     *
     * @param listener the listener
     * @param nickname the nickname of the player
     * @param message the message
     * @param gameId the id of the game
     * @throws RemoteException if the connection fails
     */
    @Override
    public synchronized void sendMessage(Listener listener, String nickname, Message message, int gameId)
            throws RemoteException {
        games.get(gameId).sendMessage(nickname, message);
    }

    /**
     * Shows the personal board of a player.
     * @param listener the listener
     * @param nickname the nickname of the player
     * @param playerIndex the index of the player
     * @param gameId the id of the game
     * @throws RemoteException if the connection fails
     */
    @Override
    public synchronized void showOthersPersonalBoard(Listener listener, String nickname, int playerIndex, int gameId)
            throws RemoteException {
        games.get(gameId).showOthersPersonalBoard(nickname, playerIndex);
    }


    /**
     * Adds a ping for a player to check connectivity.
     *
     * @param listener the listener
     * @param nickname the nickname of the player
     * @param gameId the id of the game
     * @throws RemoteException if the connection fails
     */
    @Override
    public synchronized void addPing(Listener listener, String nickname, int gameId)
            throws RemoteException {
        if (games.containsKey(gameId))
            games.get(gameId).addPing(nickname, listener);
    }

    /**
     * Allows a player to leave the game.
     *
     * @param listener the listener
     * @param nickname the nickname of the player
     * @param gameId the id of the game
     * @return the game controller
     * @throws RemoteException if the connection fails
     */
    @Override
    public synchronized void leaveGame(Listener listener, String nickname, int gameId)
            throws RemoteException {
        GameController game = games.get(gameId);
        if (game != null) {
            game.leave(listener, nickname);
            if (game.getConnectedPlayers().isEmpty()) {
                deleteGame(gameId);
            }
        }
    }

    /**
     * Deletes a game by its id.
     *
     * @param gameId the id of the game
     */
    public synchronized void deleteGame(int gameId) {
        games.remove(gameId);
    }

    /**
     * Gets the GameController for a given gameId.
     *
     * @param gameId the id of the game
     * @return the game controller
     */
    public synchronized GameControllerInterface getGameController(int gameId) {
        return games.get(gameId);
    }


}
