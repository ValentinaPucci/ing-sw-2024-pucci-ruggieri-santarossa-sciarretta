//26/4/2024 Riscrivo il ListenerHandler

package it.polimi.demo.listener;

import it.polimi.demo.model.GameModel;
import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.chat.Message;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;


import static it.polimi.demo.networking.PrintAsync.printAsync;

/**
 * The ListenersHandler class is responsible for managing a set of GameListener objects.
 * It provides methods to add and remove listeners, as well as various notify methods to
 * inform listeners of specific events.
 * Each notify method creates a new GameModelImmutable object from the provided GameModel
 * and passes it to the corresponding method of each listener.
 * If a RemoteException is thrown during a notify method call, the offending listener is
 * removed from the set.
 * All methods are synchronized to prevent concurrent modification issues.
 */
public class ListenersHandler {

    private final Set<GameListener> listeners;

    public ListenersHandler() {
        listeners = Collections.synchronizedSet(new HashSet<>());
    }

    /**
     * The addListener method adds a listener to the set of listeners
     * @param lis is the GameListener to add
     */
    public void addListener(GameListener lis) {
        listeners.add(lis);
    }

    /**
     * The getListeners method returns the set of listeners
     * @return the set of listeners
     */
    public Set<GameListener> getListeners() {
        return listeners;
    }

    /**
     * Notifies the listeners that a player has joined the game.
     *
     * @param model the GameModel representing the current game state.
     */
    public synchronized void notify_playerJoined(GameModel model) {
        // Use removeIf method with lambda expression to handle removals
        listeners.removeIf(listener -> {
            try {
                listener.playerJoined(new GameModelImmutable(model));
                return false; // Keep the listener
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_playerJoined");
                return true; // Remove the listener
            }
        });
    }

    /**
     * Notifies the listeners that a player has reconnected to the game.
     *
     * @param model the GameModel representing the current game state.
     * @param nick the nickname of the player who has reconnected.
     */
    public synchronized void notify_playerReconnected(GameModel model, String nick) {
        // Use removeIf method with lambda expression to handle removals
        listeners.removeIf(listener -> {
            try {
                listener.playerReconnected(new GameModelImmutable(model), nick);
                return false; // Keep the listener
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_playerReconnected");
                return true; // Remove the listener
            }
        });
    }

    /**
     * Notifies the listeners that a player cannot join the game because the game is full.
     *
     * @param p the player who wanted to join the game.
     * @param model               the GameModel representing the current game state.
     */
    public synchronized void notify_JoinUnableGameFull(Player p, GameModel model) {
        // Use removeIf method with lambda expression to handle removals
        listeners.removeIf(listener -> {
            try {
                listener.joinUnableGameFull(p, new GameModelImmutable(model));
                return false; // Keep the listener
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_joinUnableGameFull");
                return true; // Remove the listener
            }
        });
    }


    /**
     * The notify_JoinUnableNicknameAlreadyIn method notifies that a player cannot join the game because
     * the nickname is already in use
     * @param p is the player that wanted to join the game
     */

    public synchronized void notify_JoinUnableNicknameAlreadyIn(Player p) {
        listeners.removeIf(listener -> {
            try {
                listener.joinUnableNicknameAlreadyIn(p);
                return false; // Keep the listener
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_JoinUnableNicknameAlreadyIn");
                return true; // Remove the listener
            }
        });
    }

    /**
     * The notify_PlayerIsReadyToStart method notifies that a player is ready to start the game
     * @param model is the GameModel to pass as a new GameModelImmutable
     * @param nick is the nickname of the player that is ready to start the game
     */

    public synchronized void notify_PlayerIsReadyToStart(GameModel model, String nick) {
        listeners.removeIf(listener -> {
            try {
                listener.playerIsReadyToStart(new GameModelImmutable(model), nick);
                return false; // Keep the listener
            } catch (IOException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_PlayerIsReadyToStart");
                return true; // Remove the listener
            }
        });
    }



    /**
     * The notify_GameStarted method notifies that the game has started
     * @param model is the GameModel to pass as a new GameModelImmutable
     */

    public synchronized void notify_GameStarted(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.gameStarted(new GameModelImmutable(model));
                return false; // Keep the listener
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_GameStarted");
                return true; // Remove the listener
            }
        });
    }



    /**
     * The notify_GameEnded method notifies that the game has ended
     * @param model is the GameModel to pass as a new GameModelImmutable
     */

    public synchronized void notify_GameEnded(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.gameEnded(new GameModelImmutable(model));
                return false; // Keep the listener
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_GameEnded");
                return true; // Remove the listener
            }
        });
    }



    /**
     * Notifies all listeners about the extraction of common objective cards.
     * @param model Current game state.
     */
    public synchronized void notify_commonObjectiveCardExtracted(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.commonObjectiveCardsExtracted(new GameModelImmutable(model));
                return false; // Keep the listener
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_commonObjectiveCardExtracted");
                return true; // Remove the listener
            }
        });
    }



    /**
     * Notifies all listeners about the extraction of common resource cards from the deck.
     * @param model Current game state.
     */
    public synchronized void notify_resourceCardExtractedFromDeck(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.resourceCardExtractedFromDeck(new GameModelImmutable(model));
                return false; // Keep the listener
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_resourceCardExtractedFromDeck");
                return true; // Remove the listener
            }
        });
    }



    /**
     * Notifies all listeners about the extraction of common resource cards from table.
     * @param model Current game state.
     */

    public synchronized void notify_resourceCardExtractedFromTable(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.resourceCardExtractedFromTable(new GameModelImmutable(model));
                return false; // Keep the listener
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_resourceCardExtractedFromTable");
                return true; // Remove the listener
            }
        });
    }



    /**
     * Notifies all listeners about the extraction of common gold cards from the deck.
     * @param model Current game state.
     */
    public synchronized void notify_goldCardExtractedFromDeck(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.goldCardExtractedFromDeck(new GameModelImmutable(model));
                return false; // Keep the listener
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_goldCardExtractedFromDeck");
                return true; // Remove the listener
            }
        });
    }



    /**
     * Notifies all listeners about the extraction of common gold cards from table.
     * @param model Current game state.
     */

    public synchronized void notify_goldCardExtractedFromTable(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.goldCardExtractedFromTable(new GameModelImmutable(model));
                return false; // Keep the listener
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_goldCardExtractedFromTable");
                return true; // Remove the listener
            }
        });
    }

    /**
     * Notifies all listeners about the extraction of objective cards from empty deck.
     * @param model Current game state.
     */

    public synchronized void notify_objectiveCardExtractedFromEmptyDeck(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.objectiveCardExtractedFromEmptyDeck(new GameModelImmutable(model));
                return false; // Keep the listener
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_objectiveCardExtractedFromEmptyDeck");
                return true; // Remove the listener
            }
        });
    }


    /**
     * Notifies all listeners about the extraction of common resource cards from empty deck.
     * @param model Current game state.
     */

    public synchronized void notify_resourceCardExtractedFromEmptyDeck(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.resourceCardExtractedFromEmptyDeck(new GameModelImmutable(model));
                return false; // Keep the listener
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_resourceCardExtractedFromEmptyDeck");
                return true; // Remove the listener
            }
        });
    }

    /**
     * Notifies all listeners about the extraction of common resource cards from empty table.
     * @param model Current game state.
     */

    public synchronized void notify_resourceCardExtractedFromEmptyTable(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.resourceCardExtractedFromEmptyTable(new GameModelImmutable(model));
                return false; // Keep the listener
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_resourceCardExtractedFromEmptyTable");
                return true; // Remove the listener
            }
        });
    }

    /**
     * Notifies all listeners about the extraction of common gold cards from empty deck.
     * @param model Current game state.
     */

    public synchronized void notify_goldCardExtractedFromEmptyDeck(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.goldCardExtractedFromEmptyDeck(new GameModelImmutable(model));
                return false; // Keep the listener
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_goldCardExtractedFromEmptyDeck");
                return true; // Remove the listener
            }
        });
    }

    /**
     * Notifies all listeners about the extraction of common gold cards from empty table.
     * @param model Current game state.
     */

    public synchronized void notify_goldCardExtractedFromEmptyTable(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.goldCardExtractedFromEmptyTable(new GameModelImmutable(model));
                return false; // Keep the listener
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_goldCardExtractedFromEmptyTable");
                return true; // Remove the listener
            }
        });
    }

    /**
     * Notifies all listeners about the placement of a card on PersonalBoard.
     * @param model Current game state.
     */

    public synchronized void notify_cardPlacedOnPersonalBoard(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.cardPlacedOnPersonalBoard(new GameModelImmutable(model));
                return false; // Keep the listener
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_cardPlacedOnPersonalBoard");
                return true; // Remove the listener
            }
        });
    }

    /**
     * The notify_SentMessage method notifies that a message has been sent
     * @param gameModel is the GameModel to pass as a new GameModelImmutable
     * @param msg is the message that has been sent
     */

    public synchronized void notify_SentMessage(GameModel gameModel, Message msg) {
        listeners.removeIf(listener -> {
            try {
                listener.sentMessage(new GameModelImmutable(gameModel), msg);
                return false; // Keep the listener
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_SentMessage");
                return true; // Remove the listener
            }
        });
    }


    /**
     * This method notifies that the next turn has started
     * @param model is the current game state
     */

    public synchronized void notify_nextTurn(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.nextTurn(new GameModelImmutable(model));
                return false; // Keep the listener
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_nextTurn");
                return true; // Remove the listener
            }
        });
    }


    /**
     * This method notifies that the last turn has started
     * @param model is the current game state
     */

    public synchronized void notify_LastRound(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.lastRound(new GameModelImmutable(model));
                return false; // Keep the listener
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_LastRound");
                return true; // Remove the listener
            }
        });
    }

    /**
     * This method notifies that the second last turn has started
     * @param model is the current game state
     */
    public synchronized void notify_SecondLastRound(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.secondLastRound(new GameModelImmutable(model));
                return false; // Keep the listener
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_SecondLastRound");
                return true; // Remove the listener
            }
        });
    }


    /**
     * This method notifies that a player has disconnected
     * @param model is the current game state
     * @param nick is the nickname of the player that has disconnected
     */
    public synchronized void notify_playerDisconnected(GameModel model, String nick) {
        listeners.removeIf(listener -> {
            try {
                listener.playerDisconnected(new GameModelImmutable(model), nick);
                return false; // Keep the listener
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_playerDisconnected");
                return true; // Remove the listener
            }
        });
    }


    /**
     * This method notifies that a player has left the game
     * @param model is the current game state
     * @param nick is the nickname of the player that has left the game
     */
    public synchronized void notify_playerLeft(GameModel model, String nick) {
        listeners.removeIf(listener -> {
            try {
                listener.playerLeft(new GameModelImmutable(model), nick);
                return false; // Keep the listener
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_playerLeft");
                return true; // Remove the listener
            }
        });
    }


    /**
     * This method notifies that only one player is connected
     * @param model is the current game state
     * @param secondsToWaitUntilGameEnded is the number of seconds to wait until the game ends
     */
    public synchronized void notify_onlyOnePlayerConnected(GameModel model, int secondsToWaitUntilGameEnded) {
        listeners.removeIf(listener -> {
            try {
                listener.onlyOnePlayerConnected(new GameModelImmutable(model), secondsToWaitUntilGameEnded);
                return false; // Keep the listener
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_onlyOnePlayerConnected");
                return true; // Remove the listener
            }
        });
    }


    /**
     * This method notifies that a player has moved on CommonBoard
     * @param model is the current game state
     * @param secondsToWaitUntilGameEnded is the number of seconds to wait until the game ends
     */
    public synchronized void notify_playerHasMovedOnCommonBoard(GameModel model, int secondsToWaitUntilGameEnded) {
        listeners.removeIf(listener -> {
            try {
                listener.playerHasMovedOnCommonBoard(new GameModelImmutable(model));
                return false; // Keep the listener
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_playerHasMovedOnCommonBoard");
                return true; // Remove the listener
            }
        });
    }

    /**
     * This method removes a listener from the set of listeners
     * @param lis is the listener to remove;
     */
    public synchronized void removeListener(GameListener lis) {
        listeners.remove(lis);
    }


}
