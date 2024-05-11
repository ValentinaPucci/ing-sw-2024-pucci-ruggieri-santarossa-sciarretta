package it.polimi.demo.networking;

import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.model.exceptions.GameNotStartedException;
import it.polimi.demo.model.exceptions.InvalidChoiceException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * This interface represents all the methods that can be called by the client on the server.
 * Namely, client ----> server through Server interface!
 */
public interface Server extends Remote {
    /**
     * Register a client to the server.
     * @param client the client to register
     */
    void register(Client client) throws RemoteException;

    /**
     * This method is called by the client to join an already existing game.
     * @param gameID the ID of the game to join
     * @param username the username of the player
     * @throws GameNotStartedException if the player cannot join the game (e.g., invalid gameID, the game is full, the username is already taken, etc.)
     */
    void addPlayerToGame(int gameID, String username) throws RemoteException, GameNotStartedException, InvalidChoiceException;

    /**
     * This method is called by the client to create a new game.
     * @param numberOfPlayers the number of players in the game
     * @param username the username of the player
     * @throws GameNotStartedException if the player cannot create the game
     */
    void create(String username, int numberOfPlayers) throws RemoteException, GameNotStartedException;

    /**
     * This method is called by the client to get the list of games.
     * The client will be notified of the list of games using the VirtualClient method.
     */
    void getGamesList() throws RemoteException;

    /**
     * This method is called by the client to respond to a ping sent by the server.
     */
    void pong() throws RemoteException;

//    void performTurn();

    GameStatus getGameStatus();

    void placeStarterCard();

    void drawCard(int x);

    void placeCard(ResourceCard chosenCard, int x, int y);

    void calculateFinalScores();

    List<ResourceCard> getPlayerHand();
}