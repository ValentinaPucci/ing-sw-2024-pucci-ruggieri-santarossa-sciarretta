package it.polimi.demo.observer;

import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.Model;
import it.polimi.demo.model.enumerations.Coordinate;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.ModelView;
import it.polimi.demo.model.Player;
import it.polimi.demo.network.utils.StaticPrinter;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the registration, removal, and notification of listeners observing game events.
 * This class is responsible for notifying registered listeners about various game events.
 */
public class ObserverManager implements Serializable {

    @Serial
    private static final long serialVersionUID = 6359099610166201305L;

    private List<Listener> listeners;

    /**
     * Constructs a new ObserverManager with an empty list of listeners.
     */
    public ObserverManager() {
        listeners = new ArrayList<>();
    }

    /**
     * Adds a listener to be notified of game events.
     * @param obj The listener to add.
     */
    public synchronized void addListener(Listener obj) {
        listeners.add(obj);
    }

    /**
     * Removes a listener from the list of listeners.
     * @param lis The listener to remove.
     */
    public synchronized void removeListener(Listener lis) {
        listeners.remove(lis);
    }

    /**
     * Notifies all listeners with the specified action and handles disconnection errors.
     * @param action The action to perform on each listener.
     * @param errorMessage The error message to display in case of exceptions.
     */
    private synchronized void notifyListeners(ListenerAction action, String errorMessage) {
        List<Listener> toRemove = new ArrayList<>();
        for (Listener listener : listeners) {
            try {
                action.perform(listener);
            } catch (RemoteException e) {
                StaticPrinter.staticPrinter("Disconnection detected - " + errorMessage);
                toRemove.add(listener);
            } catch (IOException e) {
                StaticPrinter.staticPrinter("IO error - " + errorMessage);
                // Optionally, handle IOException specifically if needed
            } catch (Exception e) {
                StaticPrinter.staticPrinter("Unexpected error - " + errorMessage);
                // Optionally, handle any other exceptions if needed
            }
        }
        listeners.removeAll(toRemove);
    }

    /**
     * Functional interface for performing actions on listeners.
     */
    @FunctionalInterface
    private interface ListenerAction {
        void perform(Listener listener) throws IOException;
    }

    /**
     * Notifies all listeners that a player has joined the game.
     * @param model The current game model.
     */
    public synchronized void notify_playerJoined(Model model) {
        notifyListeners(listener -> listener.playerJoined(new ModelView(model)), "notify_playerJoined");
    }

    /**
     * Notifies all listeners that a player is ready to start the game.
     * @param model The current game model.
     * @param nick The nickname of the player who is ready.
     */
    public synchronized void notify_PlayerIsReadyToStart(Model model, String nick) {
        notifyListeners(listener -> listener.playerIsReadyToStart(new ModelView(model), nick), "notify_PlayerIsReadyToStart");
    }

    /**
     * Notifies all listeners that the game has started.
     * @param model The current game model.
     */
    public synchronized void notify_GameStarted(Model model) {
        notifyListeners(listener -> listener.gameStarted(new ModelView(model)), "notify_GameStarted");
    }

    /**
     * Notifies all listeners that the game has ended.
     * @param model The current game model.
     */
    public synchronized void notify_GameEnded(Model model) {
        notifyListeners(listener -> listener.gameEnded(new ModelView(model)), "notify_GameEnded");
    }

    /**
     * Notifies all listeners that a starter card has been placed.
     * @param model The current game model.
     * @param o The orientation of the starter card.
     * @param nick The nickname of the player who placed the starter card.
     */
    public synchronized void notify_starterCardPlaced(Model model, Orientation o, String nick) {
        notifyListeners(listener -> listener.starterCardPlaced(new ModelView(model), o, nick), "notify_starterCardPlaced");
    }

    /**
     * Notifies all listeners that a card has been chosen.
     * @param model The current game model.
     * @param which_card The index of the chosen card.
     */
    public synchronized void notify_cardChosen(Model model, int which_card) {
        notifyListeners(listener -> listener.cardChosen(new ModelView(model), which_card), "notify_cardChosen");
    }

    /**
     * Notifies all listeners that a card has been placed on the game board.
     * @param model The current game model.
     * @param where_to_place_x The x-coordinate where the card is placed.
     * @param where_to_place_y The y-coordinate where the card is placed.
     * @param o The orientation in which the card is placed.
     */
    public synchronized void notify_cardPlaced(Model model, int where_to_place_x, int where_to_place_y, Orientation o) {
        notifyListeners(listener -> listener.cardPlaced(new ModelView(model), where_to_place_x, where_to_place_y, o), "notify_cardPlaced");
    }

    /**
     * Notifies all listeners to show the personal board of another player.
     * @param model The current game model.
     * @param player_index The index of the player whose board to show.
     * @param playerNickname The nickname of the player whose board to show.
     */
    public void notify_showOthersPersonalBoard(Model model, int player_index, String playerNickname) {
        notifyListeners(listener -> listener.showOthersPersonalBoard(new ModelView(model), playerNickname, player_index), "notify_showOthersPersonalBoard");
    }

    /**
     * Notifies all listeners that an illegal move was attempted.
     * @param model The current game model.
     */
    public synchronized void notify_illegalMove(Model model) {
        notifyListeners(listener -> listener.illegalMove(new ModelView(model)), "notify_illegalMove");
    }

    /**
     * Notifies all listeners that an illegal move was attempted due to a specific reason.
     * @param model The current game model.
     * @param reason_why The reason for the illegal move.
     */
    public synchronized void notify_illegalMoveBecauseOf(Model model, String reason_why) {
        notifyListeners(listener -> listener.illegalMoveBecauseOf(new ModelView(model), reason_why), "notify_illegalMoveBecauseOf");
    }

    /**
     * Notifies all listeners that a card has been drawn.
     * @param model The current game model.
     * @param index The index of the drawn card.
     */
    public synchronized void notify_cardDrawn(Model model, int index) {
        notifyListeners(listener -> listener.cardDrawn(new ModelView(model), index), "notify_cardDrawn");
    }

    /**
     * Notifies all listeners that the second last round of the game is starting.
     * @param model The current game model.
     */
    public synchronized void notify_secondLastRound(Model model) {
        notifyListeners(listener -> listener.secondLastRound(new ModelView(model)), "notify_secondLastRound");
    }

    /**
     * Notifies all listeners that the last round of the game is starting.
     * @param model The current game model.
     */
    public synchronized void notify_lastRound(Model model) {
        notifyListeners(listener -> listener.lastRound(new ModelView(model)), "notify_lastRound");
    }

    /**
     * Notifies all listeners that a message was sent in the game chat.
     * @param model The current game model.
     * @param nick The nickname of the player who sent the message.
     * @param message The message that was sent.
     */
    public synchronized void notify_messageSent(Model model, String nick, Message message) {
        notifyListeners(listener -> listener.messageSent(new ModelView(model), nick, message), "notify_messageSent");
    }

    /**
     * Notifies all listeners that it's the next player's turn.
     * @param model The current game model.
     */
    public synchronized void notify_nextTurn(Model model) {
        notifyListeners(listener -> listener.nextTurn(new ModelView(model)), "notify_nextTurn");
    }
    
    /**
     * Notifies all listeners that a player has left the game.
     * @param model The current game model.
     * @param nick The nickname of the player who left.
     */
    public void notify_playerLeft(Model model, String nick) {
        notifyListeners(listener -> listener.playerLeft(new ModelView(model), nick), "notify_playerLeft");
    }

    /**
     * Notifies all listeners that a player has successfully moved.
     * @param model The current game model.
     * @param coordinate The coordinate where the player moved.
     */
    public void notify_successMove(Model model, Coordinate coordinate) {
        notifyListeners(listener -> listener.successfulMove(new ModelView(model), coordinate), "notify_successMove");
    }
}
