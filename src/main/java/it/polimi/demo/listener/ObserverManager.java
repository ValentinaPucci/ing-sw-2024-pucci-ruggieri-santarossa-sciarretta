package it.polimi.demo.listener;

import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.Model;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.ModelView;
import it.polimi.demo.model.Player;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static it.polimi.demo.networking.PrintAsync.printAsync;

/**
 * The ListenersHandler class is responsible for managing a List of GameListener {@link Listener} <br>
 * and for notifying the view when a change occurs in the GameModel {@link Model}. <br>
 * When notifying an event, we need to pass the GameModelImmutable {@link ModelView} to the view to have access to the updated GameModel.
 */
public class ObserverManager implements Serializable {

    private List<Listener> listeners;

    /**
     * The constructor creates a new ArrayList of GameListener {@link Listener}
     */
    public ObserverManager() {
        listeners = new ArrayList<>();
    }

    /**
     * The addListener method adds a new GameListener {@link Listener} to the List of GameListener {@link Listener} <br>
     * @param obj is the GameListener {@link Listener} to add
     */
    public synchronized void addListener(Listener obj) {
        listeners.add(obj);
    }

    /**
     * The getListeners method returns the List of GameListener {@link Listener} <br>
     * @return the List of GameListener {@link Listener}
     */
    public synchronized List<Listener> getListeners() {
        return listeners;
    }

    /**
     * The notify_playerJoined method notifies the view that a player has joined the game <br>
     * @param model is the GameModel {@link Model} to pass as a new GameModelImmutable {@link ModelView}
     */
    public synchronized void notify_playerJoined(Model model) {
        Iterator<Listener> i = listeners.iterator();
        while (i.hasNext()) {
            Listener l = i.next();
            try {
                l.playerJoined(new ModelView(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_playerJoined, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_playerReconnected method notifies the view that a player has reconnected to the game <br>
     * @param model is the GameModel {@link Model} to pass as a new GameModelImmutable {@link ModelView} <br>
     * @param nickPlayerReconnected is the nickname of the player that has left the game and now is reconnected
     */
    public synchronized void notify_playerReconnected(Model model, String nickPlayerReconnected) {
        Iterator<Listener> i = listeners.iterator();
        while (i.hasNext()) {
            Listener l = i.next();
            try {
                l.playerReconnected(new ModelView(model), nickPlayerReconnected);
            } catch (RemoteException e) {
                printAsync("During notification of notify_playerReconnected, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }


    /**
     * The notify_JoinUnableGameFull method notifies that a player cannot join the game because the game is full <br>
     * @param playerWantedToJoin is the player that wanted to join the game <br>
     * @param model is the GameModel {@link Model} to pass as a new GameModelImmutable {@link ModelView}
     */
    public synchronized void notify_JoinUnableGameFull(Player playerWantedToJoin, Model model) {
        Iterator<Listener> i = listeners.iterator();
        while (i.hasNext()) {
            Listener l = i.next();
            try {
                l.joinUnableGameFull(playerWantedToJoin, new ModelView(model));
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
        Iterator<Listener> i = listeners.iterator();
        while (i.hasNext()) {
            Listener l = i.next();
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
     * @param model is the GameModel {@link Model} to pass as a new GameModelImmutable {@link ModelView} <br>
     * @param nick is the nickname of the player that is ready to start the game
     */
    public synchronized void notify_PlayerIsReadyToStart(Model model, String nick) {
        Iterator<Listener> i = listeners.iterator();
        while (i.hasNext()) {
            Listener l = i.next();
            try {
                l.playerIsReadyToStart(new ModelView(model), nick);
            } catch (IOException e) {
                printAsync("During notification of notify_PlayerIsReadyToStart, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_GameStarted method notifies that the game has started <br>
     * @param model is the GameModel {@link Model} to pass as a new GameModelImmutable {@link ModelView} <br>
     */
    public synchronized void notify_GameStarted(Model model) {
        Iterator<Listener> i = listeners.iterator();
        while (i.hasNext()) {
            Listener l = i.next();
            try {
                l.gameStarted(new ModelView(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_GameStarted, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_GameEnded method notifies that the game has ended <br>
     * @param model is the GameModel {@link Model} to pass as a new GameModelImmutable {@link ModelView}
     */
    public synchronized void notify_GameEnded(Model model) {
        Iterator<Listener> i = listeners.iterator();
        while (i.hasNext()) {
            Listener l = i.next();
            try {
                l.gameEnded(new ModelView(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_GameEnded, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    public synchronized void notify_starterCardPlaced(Model model, Orientation o, String nick) {
        Iterator<Listener> i = listeners.iterator();
        while (i.hasNext()) {
            Listener l = i.next();
            try {
                l.starterCardPlaced(new ModelView(model), o, nick);
            } catch (RemoteException e) {
                printAsync("During notification of notify_starterCardPlaced, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    public synchronized void notify_cardChosen(Model model, int which_card) {
        Iterator<Listener> i = listeners.iterator();
        while (i.hasNext()) {
            Listener l = i.next();
            try {
                l.cardChosen(new ModelView(model), which_card);
            } catch (RemoteException e) {
                printAsync("During notification of notify_cardChosen, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    public synchronized void notify_cardPlaced(Model model, int where_to_place_x, int where_to_place_y, Orientation o) {
        Iterator<Listener> i = listeners.iterator();
        while (i.hasNext()) {
            Listener l = i.next();
            try {
                l.cardPlaced(new ModelView(model), where_to_place_x, where_to_place_y, o);
            } catch (RemoteException e) {
                printAsync("During notification of notify_cardPlaced, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    public synchronized void notify_illegalMove(Model model) {
        Iterator<Listener> i = listeners.iterator();
        while (i.hasNext()) {
            Listener l = i.next();
            try {
                printAsync("Illegal move detected");
                l.illegalMove(new ModelView(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_illegalMove, a disconnection has been detected before heartbeat");
                i.remove();

            }
        }
    }

    public synchronized void notify_successMove(Model model) {
        Iterator<Listener> i = listeners.iterator();
        while (i.hasNext()) {
            Listener l = i.next();
            try {
                printAsync("Successful move");
                l.successfulMove(new ModelView(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_successfulMove, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    public synchronized void notify_cardDrawn(Model model, int index) {
        Iterator<Listener> i = listeners.iterator();
        while (i.hasNext()) {
            Listener l = i.next();
            try {
                l.cardDrawn(new ModelView(model), index);
            } catch (RemoteException e) {
                printAsync("During notification of notify_cardDrawn, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    public synchronized void notify_secondLastRound(Model model) {
        Iterator<Listener> i = listeners.iterator();
        while (i.hasNext()) {
            Listener l = i.next();
            try {
                l.secondLastRound(new ModelView(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_SecondLastRound, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    public synchronized void notify_lastRound(Model model) {
        Iterator<Listener> i = listeners.iterator();
        while (i.hasNext()) {
            Listener l = i.next();
            try {
                l.lastRound(new ModelView(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_LastRound, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_messageSent method notifies that a message has been sent <br>
     * @param nick
     * @param message
     */
    public synchronized void notify_messageSent(Model model, String nick, Message message) {
        Iterator<Listener> i = listeners.iterator();
        while (i.hasNext()) {
            Listener l = i.next();
            try {
                l.messageSent(new ModelView(model), nick, message);
            } catch (RemoteException e) {
                printAsync("During notification of notify_SentMessage, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_nextTurn method notifies that the next turn has started <br>
     * @param model is the GameModel {@link Model} to pass as a new GameModelImmutable {@link ModelView}
     */
    public synchronized void notify_nextTurn(Model model) {
        Iterator<Listener> i = listeners.iterator();
        while (i.hasNext()) {
            Listener l = i.next();
            try {
                l.nextTurn(new ModelView(model));
            } catch (RemoteException e) {
                printAsync("During notification of notify_nextTurn, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_playerDisconnected method notifies that a player has disconnected <br>
     * @param gamemodel is the GameModel {@link Model} to pass as a new GameModelImmutable {@link ModelView} <br>
     * @param nick is the nickname of the player that has disconnected
     */
    public synchronized void notify_playerDisconnected(Model gamemodel, String nick) {
        Iterator<Listener> i = listeners.iterator();
        while (i.hasNext()) {
            Listener l = i.next();
            try {
                l.playerDisconnected(new ModelView(gamemodel), nick);
            } catch (RemoteException e) {
                printAsync("During notification of notify_playerDisconnected, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The notify_playerLeft method notifies that a player has left the game <br>
     * @param model is the GameModel {@link Model} to pass as a new GameModelImmutable {@link ModelView} <br>
     * @param nick is the nickname of the player that has left the game
     */
    public void notify_playerLeft(Model model, String nick) {
        Iterator<Listener> i = listeners.iterator();
        while (i.hasNext()) {
            Listener l = i.next();
            try {
                l.playerLeft(new ModelView(model), nick);
            } catch (RemoteException e) {
                printAsync("During notification of notify_playerLeft, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }


    /**
     * The notify_onlyOnePlayerConnected method notifies that only one player is connected <br>
     * @param model is the GameModel {@link Model} to pass as a new GameModelImmutable {@link ModelView} <br>
     * @param secondsToWaitUntillGameEnded is the number of seconds to wait untill the game ends
     */
    public synchronized void notify_onlyOnePlayerConnected(Model model, int secondsToWaitUntillGameEnded) {
        Iterator<Listener> i = listeners.iterator();
        while (i.hasNext()) {
            Listener l = i.next();
            try {
                l.onlyOnePlayerConnected(new ModelView(model), secondsToWaitUntillGameEnded);
            } catch (RemoteException e) {
                printAsync("During notification of notify_onlyOnePlayerConnected, a disconnection has been detected before heartbeat");
                i.remove();
            }
        }
    }

    /**
     * The removeListener method removes a listener from the list of listeners <br>
     * @param lis is the listener to remove
     */
    public synchronized void removeListener(Listener lis) {
        listeners.remove(lis);
    }

}