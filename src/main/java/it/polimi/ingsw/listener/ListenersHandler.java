package it.polimi.ingsw.listener;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.gameModelImmutable.GameModelImmutable;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.chat.Message;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;


import static it.polimi.ingsw.networking.PrintAsync.printAsync;

/**
 * The ListenersHandler class is responsible for managing a list of GameListener objects.
 * It provides methods to add and remove listeners, as well as various notify methods to
 * inform listeners of specific events.
 * Each notify method creates a new GameModelImmutable object from the provided GameModel
 * and passes it to the corresponding method of each listener.
 * If a RemoteException is thrown during a notify method call, the offending listener is
 * removed from the list.
 * All methods are synchronized to prevent concurrent modification issues.
 */
public class ListenersHandler {
    private List<GameListener> listeners;

    public ListenersHandler() {
        listeners = new ArrayList<>();
    }

    /**
     * The addListener method adds a new GameListener
     * to the List of GameListener
     *
     * @param obj is the GameListener to add
     */
    public synchronized void addListener(GameListener obj) {
        listeners.add(obj);
    }

    /**
     * The getListeners method returns the List of GameListener
     *
     * @return the List of GameListener
     */
    public synchronized List<GameListener> getListeners() {
        return listeners;
    }

    /**
     * The notify_playerJoined method notifies the view that a player has joined the game
     * @param model is the GameModel to pass as a new GameModelImmutable
     */
    public synchronized void notify_playerJoined(GameModel model) {

        // Temporary list to hold listeners that need to be removed
        List<GameListener> toRemove = new ArrayList<>();

        // Using enhanced for-loop
        for (GameListener l : listeners) {
            try {
                l.playerJoined(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_playerJoined, " +
                        "a disconnection has been detected before heartbeat");
                // Add to temporary list instead of removing directly
                toRemove.add(l);
            }
        }

        // Remove all listeners that encountered RemoteException
        listeners.removeAll(toRemove);
    }


    /**
     * The notify_playerReconnected method notifies the view that a player has reconnected to the game
     * @param model is the GameModel to pass as a new GameModelImmutable
     * @param nickPlayerReconnected is the nickname of the player that has left the game and now is reconnected
     */
    public synchronized void notify_playerReconnected(GameModel model, String nickPlayerReconnected) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.playerReconnected(new GameModelImmutable(model), nickPlayerReconnected);
            } catch (RemoteException e) {
                printAsync("During notification of notify_playerReconnected, " +
                        "a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }


    /**
     * The notify_JoinUnableGameFull method notifies that a player cannot join the game because the game is full
     * @param playerWantedToJoin is the player that wanted to join the game
     * @param model is the GameModel to pass as a new GameModelImmutable
     */
    public synchronized void notify_JoinUnableGameFull(Player playerWantedToJoin, GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.joinUnableGameFull(playerWantedToJoin, new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_JoinUnableGameFull, " +
                        "a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_JoinUnableNicknameAlreadyIn method notifies that a player cannot join the game because
     * the nickname is already in use
     * @param playerWantedToJoin is the player that wanted to join the game
     */
    public synchronized void notify_JoinUnableNicknameAlreadyIn(Player playerWantedToJoin) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.joinUnableNicknameAlreadyIn(playerWantedToJoin);
            } catch (RemoteException e) {
                printAsync("During notification of notify_JoinUnableNicknameAlreadyIn, " +
                        "a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_PlayerIsReadyToStart method notifies that a player is ready to start the game
     * @param model is the GameModel to pass as a new GameModelImmutable
     * @param nick is the nickname of the player that is ready to start the game
     */
    public synchronized void notify_PlayerIsReadyToStart(GameModel model, String nick) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.playerIsReadyToStart(new GameModelImmutable(model), nick);
            } catch (IOException e) {
                printAsync("During notification of notify_PlayerIsReadyToStart, " +
                        "a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_GameStarted method notifies that the game has started
     * @param model is the GameModel to pass as a new GameModelImmutable
     */
    public synchronized void notify_GameStarted(GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.gameStarted(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_GameStarted, " +
                        "a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_GameEnded method notifies that the game has ended
     * @param model is the GameModel to pass as a new GameModelImmutable
     */
    public synchronized void notify_GameEnded(GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.gameEnded(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_GameEnded, " +
                        "a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * Notifies all listeners about the extraction of common objective cards.
     * @param model Current game state.
     */
    public synchronized void notify_commonObjectiveCardExtracted(GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.commonObjectiveCardsExtracted(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_commonObjectiveCardExtracted, " +
                        "a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * Notifies all listeners about the extraction of common resource cards from the deck.
     * @param model Current game state.
     */
    public synchronized void notify_resourceCardExtractedFromDeck(GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.resourceCardExtractedFromDeck(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_resourceCardExtractedFromDeck, " +
                        "a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * Notifies all listeners about the extraction of common resource cards from table.
     * @param model Current game state.
     */
    public synchronized void notify_resourceCardExtractedFromTable(GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.resourceCardExtractedFromTable(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_resourceCardExtractedFromTable, " +
                        "a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * Notifies all listeners about the extraction of common gold cards from the deck.
     * @param model Current game state.
     */
    public synchronized void notify_goldCardExtractedFromDeck(GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.goldCardExtractedFromDeck(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_goldCardExtractedFromDeck, " +
                        "a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * Notifies all listeners about the extraction of common gold cards from table.
     * @param model Current game state.
     */
    public synchronized void notify_goldCardExtractedFromTable(GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.goldCardExtractedFromTable(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_goldCardExtractedFromTable, " +
                        "a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }


    public synchronized void notify_objectiveCardExtractedFromEmptyDeck(GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.objectiveCardExtractedFromEmptyDeck(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_objectiveCardExtractedFromEmptyDeck, " +
                        "a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }



    public synchronized void notify_resourceCardExtractedFromEmptyDeck(GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.resourceCardExtractedFromEmptyDeck(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_resourceCardExtractedFromEmptyDeck, " +
                        "a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    public synchronized void notify_resourceCardExtractedFromEmptyTable(GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.resourceCardExtractedFromEmptyTable(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_resourceCardExtractedFromEmptyTable, " +
                        "a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }


    public synchronized void notify_goldCardExtractedFromEmptyDeck(GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.goldCardExtractedFromEmptyDeck(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_goldCardExtractedFromEmptyDeck, " +
                        "a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    public synchronized void notify_goldCardExtractedFromEmptyTable(GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.goldCardExtractedFromEmptyTable(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_goldCardExtractedFromEmptyTable, " +
                        "a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }


    public synchronized void notify_cardPlacedOnPersonalBoard(GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.cardPlacedOnPersonalBoard(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_cardPlacedOnPersonalBoard, " +
                        "a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_SentMessage method notifies that a message has been sent
     * @param gameModel is the GameModel to pass as a new GameModelImmutable
     * @param msg is the message that has been sent
     */
    public synchronized void notify_SentMessage(GameModel gameModel, Message msg) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.sentMessage(new GameModelImmutable(gameModel), msg);
            } catch (RemoteException e) {
                printAsync("During notification of notify_SentMessage, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_nextTurn method notifies that the next turn has started <br>
     * @param model is the GameModel {@link GameModel} to pass as a new GameModelImmutable {@link GameModelImmutable}
     */
    public synchronized void notify_nextTurn(GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.nextTurn(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_nextTurn, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_lastCircle method notifies that the last circle has started <br>
     * @param model is the GameModel {@link GameModel} to pass as a new GameModelImmutable {@link GameModelImmutable}
     */
    public void notify_LastTurn(GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.lastTurn(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_LastCircle, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    public void notify_secondLastTurn(GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.secondLastTurn(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_LastCircle, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_playerDisconnected method notifies that a player has disconnected <br>
     * @param gamemodel is the GameModel {@link GameModel} to pass as a new GameModelImmutable {@link GameModelImmutable} <br>
     * @param nick is the nickname of the player that has disconnected
     */
    public synchronized void notify_playerDisconnected(GameModel gamemodel, String nick) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.playerDisconnected(new GameModelImmutable(gamemodel), nick);
            } catch (RemoteException e) {
                printAsync("During notification of notify_playerDisconnected, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_playerLeft method notifies that a player has left the game <br>
     * @param gameModel is the GameModel {@link GameModel} to pass as a new GameModelImmutable {@link GameModelImmutable} <br>
     * @param nick is the nickname of the player that has left the game
     */
    public void notify_playerLeft(GameModel gameModel, String nick) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.playerLeft(new GameModelImmutable(gameModel), nick);
            } catch (RemoteException e) {
                printAsync("During notification of notify_playerLeft, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_onlyOnePlayerConnected method notifies that only one player is connected
     * @param model is the GameModel {@link GameModel} to pass as a new GameModelImmutable
     * @param secondsToWaitUntillGameEnded is the number of seconds to wait untill the game ends
     */
    public synchronized void notify_onlyOnePlayerConnected(GameModel model, int secondsToWaitUntillGameEnded) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.onlyOnePlayerConnected(new GameModelImmutable(model), secondsToWaitUntillGameEnded);
            } catch (RemoteException e) {
                printAsync("During notification of notify_onlyOnePlayerConnected, " +
                        "a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }


    public synchronized void notify_playerHasMovedOnCommonBoard(GameModel model, int secondsToWaitUntillGameEnded) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.playerHasMovedOnCommonBoard(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_playerHasMovedOnCommonBoard, " +
                        "a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }



    /**
     * The removeListener method removes a listener from the list of listeners <br>
     * @param lis is the listener to remove
     */
    public synchronized void removeListener(GameListener lis) {
        listeners.remove(lis);
    }
}
