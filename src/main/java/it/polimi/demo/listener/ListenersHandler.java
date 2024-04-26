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
     * This method adds a listener to the set of listeners
     * @param lis is the listener to add
     */
    public void addListener(GameListener lis) {
        listeners.add(lis);
    }

    /**
     * This method returns the set of listeners
     * @return the set of listeners
     */
    public Set<GameListener> getListeners() {
        return listeners;
    }

    /**
     * This method informs the listeners that a player has joined the game
     * @param model the GameModel representing the current game state.
     */
    public synchronized void notify_playerParticipating(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.playerParticipating(new GameModelImmutable(model));
                return false;
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_playerParticipating");
                return true;
            }
        });
    }

    /**
     * This method informs the listeners that a player has reconnected to the game.
     *
     * @param model the GameModel representing the current game state.
     * @param nick the nickname of the player who has reconnected.
     */
    public synchronized void notify_playerReconnected(GameModel model, String nick) {
        listeners.removeIf(listener -> {
            try {
                listener.playerReconnected(new GameModelImmutable(model), nick);
                return false;
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_playerReconnected");
                return true;
            }
        });
    }

    /**
     * This method informs the listeners that a player's attempt to join the game has been unsuccessful due to the game being at full capacity.
     * @param p the player who wanted to join the game.
     * @param model the GameModel representing the current game state.
     */
    public synchronized void notify_failedJoinFullGame(Player p, GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.failedJoinFullGame(p, new GameModelImmutable(model));
                return false;
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_failedJoinFullGame");
                return true;
            }
        });
    }


    /**
     * This method informs that a player cannot join the game because
     * the nickname is already in use
     * @param p is the player that wanted to join the game
     */

    public synchronized void notify_failedJoinInvalidNickname(Player p) {
        listeners.removeIf(listener -> {
            try {
                listener.failedJoinInvalidNickname(p);
                return false;
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_failedJoinInvalidNickname");
                return true;
            }
        });
    }

    /**
     * This method informs that a player is ready to start the game
     * @param model the GameModel representing the current game state
     * @param nick is the alias of the player who is prepared to initiate the game.
     */

    public synchronized void notify_PlayerReadyForStarting(GameModel model, String nick) {
        listeners.removeIf(listener -> {
            try {
                listener.playerReadyForStarting(new GameModelImmutable(model), nick);
                return false;
            } catch (IOException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_PlayerReadyForStarting");
                return true;
            }
        });
    }



    /**
     * This method informs that the game has started
     * @param model the GameModel representing the current game state
     */

    public synchronized void notify_GameStarted(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.gameStarted(new GameModelImmutable(model));
                return false;
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_GameStarted");
                return true;
            }
        });
    }



    /**
     * This method informs that the game is finished
     * @param model the GameModel representing the current game state
     */

    public synchronized void notify_GameEnded(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.gameEnded(new GameModelImmutable(model));
                return false;
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_GameEnded");
                return true;
            }
        });
    }



    /**
     * This method informs all listeners about the extraction of common objective cards.
     * @param model Current game state.
     */
    public synchronized void notify_commonObjectiveCardExtracted(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.commonObjectiveCardsExtracted(new GameModelImmutable(model));
                return false;
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_commonObjectiveCardExtracted");
                return true;
            }
        });
    }



    /**
     *  This method informs all listeners about the extraction of common resource cards from the deck.
     * @param model Current game state.
     */
    public synchronized void notify_resourceCardExtractedFromDeck(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.resourceCardExtractedFromDeck(new GameModelImmutable(model));
                return false;
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_resourceCardExtractedFromDeck");
                return true;
            }
        });
    }



    /**
     * this method informs all listeners about the extraction of common resource cards from table.
     * @param model Current game state.
     */

    public synchronized void notify_resourceCardExtractedFromTable(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.resourceCardExtractedFromTable(new GameModelImmutable(model));
                return false;
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_resourceCardExtractedFromTable");
                return true;
            }
        });
    }



    /**
     * This method informs all listeners about the extraction of common gold cards from the deck.
     * @param model Current game state.
     */
    public synchronized void notify_goldCardExtractedFromDeck(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.goldCardExtractedFromDeck(new GameModelImmutable(model));
                return false;
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_goldCardExtractedFromDeck");
                return true;
            }
        });
    }



    /**
     * This method notifies all listeners about the extraction of common gold cards from table.
     * @param model Current game state.
     */

    public synchronized void notify_goldCardExtractedFromTable(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.goldCardExtractedFromTable(new GameModelImmutable(model));
                return false;
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_goldCardExtractedFromTable");
                return true;
            }
        });
    }

    /**
     * This method informs all listeners about the extraction of objective cards from empty deck.
     * @param model Current game state.
     */

    public synchronized void notify_objectiveCardExtractedFromEmptyDeck(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.objectiveCardExtractedFromEmptyDeck(new GameModelImmutable(model));
                return false;
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_objectiveCardExtractedFromEmptyDeck");
                return true;
            }
        });
    }


    /**
     * This method informs all listeners about the extraction of common resource cards from empty deck.
     * @param model Current game state.
     */

    public synchronized void notify_resourceCardExtractedFromEmptyDeck(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.resourceCardExtractedFromEmptyDeck(new GameModelImmutable(model));
                return false;
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_resourceCardExtractedFromEmptyDeck");
                return true;
            }
        });
    }

    /**
     * This method informs all listeners about the extraction of common resource cards from empty table.
     * @param model Current game state.
     */

    public synchronized void notify_resourceCardExtractedFromEmptyTable(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.resourceCardExtractedFromEmptyTable(new GameModelImmutable(model));
                return false;
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_resourceCardExtractedFromEmptyTable");
                return true;
            }
        });
    }

    /**
     * This method informs all listeners about the extraction of common gold cards from empty deck.
     * @param model Current game state.
     */

    public synchronized void notify_goldCardExtractedFromEmptyDeck(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.goldCardExtractedFromEmptyDeck(new GameModelImmutable(model));
                return false;
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_goldCardExtractedFromEmptyDeck");
                return true;
            }
        });
    }

    /**
     * This method informs all listeners about the extraction of common gold cards from empty table.
     * @param model Current game state.
     */

    public synchronized void notify_goldCardExtractedFromEmptyTable(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.goldCardExtractedFromEmptyTable(new GameModelImmutable(model));
                return false;
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_goldCardExtractedFromEmptyTable");
                return true;
            }
        });
    }

    /**
     * This method informs all listeners about the placement of a card on PersonalBoard.
     * @param model Current game state.
     */

    public synchronized void notify_cardPlacedOnPersonalBoard(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.cardPlacedOnPersonalBoard(new GameModelImmutable(model));
                return false;
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_cardPlacedOnPersonalBoard");
                return true;
            }
        });
    }

    /**
     * This method informs that a message has been sent
     * @param gameModel current game state
     * @param msg is the message that has been sent
     */

    public synchronized void notify_SentMessage(GameModel gameModel, Message msg) {
        listeners.removeIf(listener -> {
            try {
                listener.sentMessage(new GameModelImmutable(gameModel), msg);
                return false;
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_SentMessage");
                return true;
            }
        });
    }


    /**
     * This method informs that the next turn has started
     * @param model is the current game state
     */

    public synchronized void notify_nextTurn(GameModel model) {
        listeners.removeIf(listener -> {
            try {
                listener.nextTurn(new GameModelImmutable(model));
                return false;
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_nextTurn");
                return true;
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
                return false;
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_LastRound");
                return true;
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
                return false;
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_SecondLastRound");
                return true;
            }
        });
    }


    /**
     * This method informs that a player has disconnected
     * @param model is the current game state
     * @param nick is the alias of the player who  has disconnected
     */
    public synchronized void notify_playerDisconnected(GameModel model, String nick) {
        listeners.removeIf(listener -> {
            try {
                listener.playerDisconnected(new GameModelImmutable(model), nick);
                return false;
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_playerDisconnected");
                return true;
            }
        });
    }


    /**
     * This method informs that a player has left the game
     * @param model is the current game state
     * @param nick is the nickname of the player that has left the game
     */
    public synchronized void notify_playerAbandoningGame(GameModel model, String nick) {
        listeners.removeIf(listener -> {
            try {
                listener.playerAbandoningGame(new GameModelImmutable(model), nick);
                return false;
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_playerLeft");
                return true;
            }
        });
    }


    /**
     * This method informs that only one player is connected
     * @param model is the current game state
     * @param secondsToWaitUntilGameEnded is the number of seconds to wait until the game ends
     */
    public synchronized void notify_onlyOnePlayerConnected(GameModel model, int secondsToWaitUntilGameEnded) {
        listeners.removeIf(listener -> {
            try {
                listener.onlyOnePlayerConnected(new GameModelImmutable(model), secondsToWaitUntilGameEnded);
                return false;
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_onlyOnePlayerConnected");
                return true;
            }
        });
    }


    /**
     * This method informs that a player has moved on CommonBoard
     * @param model is the current game state
     * @param secondsToWaitUntilGameEnded is the number of seconds to wait until the game ends
     */
    public synchronized void notify_playerHasMovedOnCommonBoard(GameModel model, int secondsToWaitUntilGameEnded) {
        listeners.removeIf(listener -> {
            try {
                listener.playerHasMovedOnCommonBoard(new GameModelImmutable(model));
                return false;
            } catch (RemoteException e) {
                printAsync("ListenerHandler: Disconnection detected during notify_playerHasMovedOnCommonBoard");
                return true;
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


    public synchronized boolean contains(GameListener gameListener){
        return listeners.contains(gameListener);
    }

    public synchronized int size(){
        return listeners.size();
    }


}
