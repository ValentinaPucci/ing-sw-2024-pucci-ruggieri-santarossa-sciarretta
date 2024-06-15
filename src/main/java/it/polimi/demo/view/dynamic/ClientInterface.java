package it.polimi.demo.view.dynamic;

import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.*;
import it.polimi.demo.model.exceptions.GameEndedException;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.io.Serializable;

/**
 * Interface representing operations that a client can perform on the server.
 */
public interface ClientInterface extends Serializable {

    /**
     * Creates a new game with the specified nickname and number of players.
     * @param nickname The nickname of the player creating the game.
     * @param num_of_players The number of players in the game.
     * @throws IOException If an I/O error occurs.
     * @throws InterruptedException If the operation is interrupted.
     * @throws NotBoundException If the remote object is not bound.
     */
    void createGame(String nickname, int num_of_players) throws IOException, InterruptedException, NotBoundException;

    /**
     * Joins an existing game with the specified nickname and game ID.
     * @param nick The nickname of the player joining the game.
     * @param idGame The ID of the game to join.
     * @throws IOException If an I/O error occurs.
     * @throws InterruptedException If the operation is interrupted.
     * @throws NotBoundException If the remote object is not bound.
     */
    void joinGame(String nick, int idGame) throws IOException, InterruptedException, NotBoundException;

    /**
     * Joins a game randomly with the specified nickname.
     * @param nick The nickname of the player joining the game randomly.
     * @throws IOException If an I/O error occurs.
     * @throws InterruptedException If the operation is interrupted.
     * @throws NotBoundException If the remote object is not bound.
     */
    void joinRandomly(String nick) throws IOException, InterruptedException, NotBoundException;

    /**
     * Shows the personal board of another player specified by index.
     * @param player_index The index of the player whose personal board to show.
     * @throws IOException If an I/O error occurs.
     * @throws NotBoundException If the remote object is not bound.
     */
    void showOthersPersonalBoard(int player_index) throws IOException, NotBoundException;

    /**
     * Leaves the game with the specified nickname and game ID.
     * @param nick The nickname of the player leaving the game.
     * @param idGame The ID of the game to leave.
     * @throws IOException If an I/O error occurs.
     * @throws NotBoundException If the remote object is not bound.
     */
    void leave(String nick, int idGame) throws IOException, NotBoundException;

    /**
     * Sets the player as ready to start the game.
     * @throws IOException If an I/O error occurs.
     * @throws NotBoundException If the remote object is not bound.
     */
    void setAsReady() throws IOException, NotBoundException;

    /**
     * Places the starter card with the specified orientation.
     * @param orientation The orientation of the starter card.
     * @throws IOException If an I/O error occurs.
     * @throws GameEndedException If the game has ended unexpectedly.
     * @throws NotBoundException If the remote object is not bound.
     */
    void placeStarterCard(Orientation orientation) throws IOException, GameEndedException, NotBoundException;

    /**
     * Chooses a card to play with the specified index.
     * @param which_card The index of the card to choose.
     * @throws IOException If an I/O error occurs.
     * @throws NotBoundException If the remote object is not bound.
     * @throws GameEndedException If the game has ended unexpectedly.
     */
    void chooseCard(int which_card) throws IOException, NotBoundException, GameEndedException;

    /**
     * Places a card at the specified coordinates with the specified orientation.
     * @param where_to_place_x The x-coordinate where the card is to be placed.
     * @param where_to_place_y The y-coordinate where the card is to be placed.
     * @param orientation The orientation of the card.
     * @throws IOException If an I/O error occurs.
     * @throws NotBoundException If the remote object is not bound.
     * @throws GameEndedException If the game has ended unexpectedly.
     */
    void placeCard(int where_to_place_x, int where_to_place_y, Orientation orientation) throws IOException, NotBoundException, GameEndedException;

    /**
     * Draws a card from the specified index.
     * @param index The index of the card to draw.
     * @throws IOException If an I/O error occurs.
     * @throws GameEndedException If the game has ended unexpectedly.
     * @throws NotBoundException If the remote object is not bound.
     */
    void drawCard(int index) throws IOException, GameEndedException, NotBoundException;

    /**
     * Sends a message to the specified receiver.
     * @param receiver The receiver of the message.
     * @param msg The message to send.
     * @throws IOException If an I/O error occurs.
     * @throws NotBoundException If the remote object is not bound.
     */
    void sendMessage(String receiver, Message msg) throws IOException, NotBoundException;

    /**
     * Pings the server.
     * @throws IOException If an I/O error occurs.
     * @throws NotBoundException If the remote object is not bound.
     */
    void ping() throws IOException, NotBoundException;
}

