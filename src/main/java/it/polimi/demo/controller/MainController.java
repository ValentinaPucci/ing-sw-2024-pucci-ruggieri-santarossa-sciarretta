package it.polimi.demo.controller;

import it.polimi.demo.networking.remoteInterfaces.GameControllerInterface;
import it.polimi.demo.networking.remoteInterfaces.MainControllerInterface;
import it.polimi.demo.observer.Listener;
import it.polimi.demo.DefaultValues;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.model.Player;
import it.polimi.demo.networking.remoteInterfaces.*;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;

import static it.polimi.demo.networking.PrintAsync.printAsync;
import static it.polimi.demo.networking.PrintAsync.printAsyncNoLine;


public class MainController implements MainControllerInterface, Serializable {

    private static MainController instance = null;

    private Map<Integer, GameController> games;

    private MainController() {
        games = new HashMap<>();
    }

    public synchronized static MainController getControllerInstance() {
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }

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

        return game;
    }

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

        return games.get(gameId);
    }

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

                    if (game.getConnectedPlayers().isEmpty()) {
                        deleteGame(gameId);
                    }
                    return game;
                })
                .orElse(null);
    }

    public synchronized void deleteGame(int gameId) {

        games.values().stream()
                .filter(game -> game.getGameId() == gameId)
                .findFirst()
                .ifPresent(game -> {
                    games.remove(gameId);
                    System.out.println("\t>Game " + gameId + " removed from games");
                });
    }

    public synchronized GameControllerInterface getGameController(int gameId) {
        return games.get(gameId);
    }

}