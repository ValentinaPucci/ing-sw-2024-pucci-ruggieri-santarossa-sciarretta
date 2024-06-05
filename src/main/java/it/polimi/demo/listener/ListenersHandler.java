package it.polimi.demo.listener;

import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.GameModel;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;
import it.polimi.demo.model.Player;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static it.polimi.demo.networking.PrintAsync.printAsync;

/**
 * The ListenersHandler class is responsible for managing a List of GameListener {@link GameListener} <br>
 * and for notifying the view when a change occurs in the GameModel {@link GameModel}. <br>
 * When notifying an event, we need to pass the GameModelImmutable {@link GameModelImmutable} to the view to have access to the updated GameModel.
 */
public class ListenersHandler implements Serializable {

    private List<GameListener> listeners;

    /**
     * The constructor creates a new ArrayList of GameListener {@link GameListener}
     */
    public ListenersHandler() {
        listeners = new ArrayList<>();
    }

    /**
     * The addListener method adds a new GameListener {@link GameListener} to the List of GameListener {@link GameListener} <br>
     * @param obj is the GameListener {@link GameListener} to add
     */
    public synchronized void addListener(GameListener obj) {
        listeners.add(obj);
    }

    /**
     * The getListeners method returns the List of GameListener {@link GameListener} <br>
     * @return the List of GameListener {@link GameListener}
     */
    public synchronized List<GameListener> getListeners() {
        return listeners;
    }

    /**
     * The notify_playerJoined method notifies the view that a player has joined the game <br>
     * @param model is the GameModel {@link GameModel} to pass as a new GameModelImmutable {@link GameModelImmutable}
     */
    public synchronized void notify_playerJoined(GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.playerJoined(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_playerJoined, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_playerReconnected method notifies the view that a player has reconnected to the game <br>
     * @param model is the GameModel {@link GameModel} to pass as a new GameModelImmutable {@link GameModelImmutable} <br>
     * @param nickPlayerReconnected is the nickname of the player that has left the game and now is reconnected
     */
    public synchronized void notify_playerReconnected(GameModel model, String nickPlayerReconnected) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.playerReconnected(new GameModelImmutable(model), nickPlayerReconnected);
            } catch (RemoteException e) {
                printAsync("During notification of notify_playerReconnected, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }


    /**
     * The notify_JoinUnableGameFull method notifies that a player cannot join the game because the game is full <br>
     * @param playerWantedToJoin is the player that wanted to join the game <br>
     * @param model is the GameModel {@link GameModel} to pass as a new GameModelImmutable {@link GameModelImmutable}
     */
    public synchronized void notify_JoinUnableGameFull(Player playerWantedToJoin, GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.joinUnableGameFull(playerWantedToJoin, new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_JoinUnableGameFull, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_JoinUnableNicknameAlreadyIn method notifies that a player cannot join the game because the nickname is already in use <br>
     * @param playerWantedToJoin is the player that wanted to join the game {@link Player} <br>
     */
    public synchronized void notify_JoinUnableNicknameAlreadyIn(Player playerWantedToJoin) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.joinUnableNicknameAlreadyIn(playerWantedToJoin);
            } catch (RemoteException e) {
                printAsync("During notification of notify_JoinUnableNicknameAlreadyIn, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_PlayerIsReadyToStart method notifies that a player is ready to start the game <br>
     * @param model is the GameModel {@link GameModel} to pass as a new GameModelImmutable {@link GameModelImmutable} <br>
     * @param nick is the nickname of the player that is ready to start the game
     */
    public synchronized void notify_PlayerIsReadyToStart(GameModel model, String nick) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.playerIsReadyToStart(new GameModelImmutable(model), nick);
            } catch (IOException e) {
                printAsync("During notification of notify_PlayerIsReadyToStart, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_GameStarted method notifies that the game has started <br>
     * @param model is the GameModel {@link GameModel} to pass as a new GameModelImmutable {@link GameModelImmutable} <br>
     */
    public synchronized void notify_GameStarted(GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.gameStarted(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_GameStarted, a disconnection has been detected before heartbeat");
                try {
                    aux_gameEndedNotifier(l, model);
                } catch (RemoteException ex) {
                }
                i.remove();
            }
        }
    }

    /**
     * The notify_GameEnded method notifies that the game has ended <br>
     * @param model is the GameModel {@link GameModel} to pass as a new GameModelImmutable {@link GameModelImmutable}
     */
    public synchronized void notify_GameEnded(GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.gameEnded(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_GameEnded, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    public synchronized void notify_starterCardPlaced(GameModel model, Orientation o, String nick) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.starterCardPlaced(new GameModelImmutable(model), o, nick);
            } catch (RemoteException e) {
                printAsync("During notification of notify_starterCardPlaced, a disconnection has been detected before heartbeat");
                try {
                    aux_gameEndedNotifier(l, model);
                } catch (RemoteException ex) {
                }
                i.remove();
            }
        }
    }

    public synchronized void notify_cardChosen(GameModel model, int which_card) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.cardChosen(new GameModelImmutable(model), which_card);
            } catch (RemoteException e) {
                printAsync("During notification of notify_cardChosen, a disconnection has been detected before heartbeat");
                try {
                    aux_gameEndedNotifier(l, model);
                } catch (RemoteException ex) {
                }
                i.remove();
            }
        }
    }

    public synchronized void notify_cardPlaced(GameModel model, int where_to_place_x, int where_to_place_y, Orientation o) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.cardPlaced(new GameModelImmutable(model), where_to_place_x, where_to_place_y, o);
            } catch (RemoteException e) {
                printAsync("During notification of notify_cardPlaced, a disconnection has been detected before heartbeat");
                try {
                    aux_gameEndedNotifier(l, model);
                } catch (RemoteException ex) {
                }
                i.remove();
            }
        }
    }

    public synchronized void notify_illegalMove(GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                printAsync("Illegal move detected");
                l.illegalMove(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_illegalMove, a disconnection has been detected before heartbeat");
                try {
                    aux_gameEndedNotifier(l, model);
                } catch (RemoteException ex) {
                }
                i.remove();
            }
        }
    }

    public synchronized void notify_cardDrawn(GameModel model, int index) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.cardDrawn(new GameModelImmutable(model), index);
            } catch (RemoteException e) {
                printAsync("During notification of notify_cardDrawn, a disconnection has been detected before heartbeat");
                try {
                    aux_gameEndedNotifier(l, model);
                } catch (RemoteException ex) {
                }
                i.remove();
            }
        }
    }

    public synchronized void notify_secondLastRound(GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.secondLastRound(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_SecondLastRound, a disconnection has been detected before heartbeat");
                try {
                    aux_gameEndedNotifier(l, model);
                } catch (RemoteException ex) {
                }
                i.remove();
            }
        }
    }

    public synchronized void notify_lastRound(GameModel model) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.lastRound(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_LastRound, a disconnection has been detected before heartbeat");
                try {
                    aux_gameEndedNotifier(l, model);
                } catch (RemoteException ex) {
                }
                i.remove();
            }
        }
    }

    /**
     * The notify_messageSent method notifies that a message has been sent <br>
     * @param nick
     * @param message
     */
    public synchronized void notify_messageSent(GameModel model, String nick, Message message) {
        Iterator<GameListener> i = listeners.iterator();
        while (i.hasNext()) {
            GameListener l = i.next();
            try {
                l.messageSent(new GameModelImmutable(model), nick, message);
            } catch (RemoteException e) {
                printAsync("During notification of notify_SentMessage, a disconnection has been detected before heartbeat");
                try {
                    aux_gameEndedNotifier(l, model);
                } catch (RemoteException ex) {
                }
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
                try {
                    aux_gameEndedNotifier(l, model);
                } catch (RemoteException ex) {
                }
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
     * The removeListener method removes a listener from the list of listeners <br>
     * @param lis is the listener to remove
     */
    public synchronized void removeListener(GameListener lis) {
        listeners.remove(lis);
    }

    public void aux_gameEndedNotifier(GameListener l, GameModel model) throws RemoteException {
        for (GameListener listener : listeners) {
            if (!listener.equals(l)) {
                listener.gameEnded(new GameModelImmutable(model));
            }
        }
    }

}