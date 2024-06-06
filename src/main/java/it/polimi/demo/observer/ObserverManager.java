package it.polimi.demo.observer;

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
     * The constructor of the class ObserverManager
     */
    public ObserverManager() {
        listeners = new ArrayList<>();
    }

    /**
     * The addListener method adds a observer to the list of listeners
     * @param obj is the observer to add
     */
    public synchronized void addListener(Listener obj) {
        listeners.add(obj);
    }

    /**
     * The notify_playerJoined method is used to notify the listeners that a player has joined the game
     * @param model is the game model
     */
    public synchronized void notify_playerJoined(Model model) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.playerJoined(new ModelView(model));
            } catch (RemoteException e) {
                printAsync("Disconnection detected - notify_playerJoined");
                toRemove.add(listener);
            }
        });

        listeners.removeAll(toRemove);
    }

    /**
     * The notify_joinUnableGameFull method is used to notify the listeners that a player has tried to join a full game
     * @param p is the player that tried to join
     * @param model is the game model
     */
    public synchronized void notify_joinUnableGameFull(Player p, Model model) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.joinUnableGameFull(p, new ModelView(model));
            } catch (RemoteException e) {
                printAsync("Disconnection detected - notify_joinUnableGameFull");
                toRemove.add(listener);
            }
        });

        listeners.removeAll(toRemove);
    }

    public synchronized void notify_joinUnableNicknameAlreadyIn(Player p) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.joinUnableNicknameAlreadyIn(p);
            } catch (RemoteException e) {
                printAsync("Disconnection detected - notify_joinUnableNicknameAlreadyIn");
                toRemove.add(listener);
            }
        });

        listeners.removeAll(toRemove);
    }

    public synchronized void notify_PlayerIsReadyToStart(Model model, String nick) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.playerIsReadyToStart(new ModelView(model), nick);
            } catch (IOException e) {
                printAsync("Disconnection detected - notify_PlayerIsReadyToStart");
                toRemove.add(listener);
            }
        });
        listeners.removeAll(toRemove);
    }

    public synchronized void notify_GameStarted(Model model) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.gameStarted(new ModelView(model));
            } catch (RemoteException e) {
                printAsync("Disconnection detected - notify_GameStarted");
                toRemove.add(listener);
            }
        });

        listeners.removeAll(toRemove);
    }

    public synchronized void notify_GameEnded(Model model) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.gameEnded(new ModelView(model));
            } catch (RemoteException e) {
                printAsync("Disconnection detected - notify_GameEnded");
                toRemove.add(listener);
            }
        });

        listeners.removeAll(toRemove);
    }

    public synchronized void notify_starterCardPlaced(Model model, Orientation o, String nick) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.starterCardPlaced(new ModelView(model), o, nick);
            } catch (RemoteException e) {
                printAsync("Disconnection detected - notify_starterCardPlaced");
                toRemove.add(listener);
            }
        });

        listeners.removeAll(toRemove);
    }

    public synchronized void notify_cardChosen(Model model, int which_card) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.cardChosen(new ModelView(model), which_card);
            } catch (RemoteException e) {
                printAsync("Disconnection detected - notify_cardChosen");
                toRemove.add(listener);
            }
        });

        listeners.removeAll(toRemove);
    }

    public synchronized void notify_cardPlaced(Model model, int where_to_place_x, int where_to_place_y, Orientation o) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.cardPlaced(new ModelView(model), where_to_place_x, where_to_place_y, o);
            } catch (RemoteException e) {
                printAsync("Disconnection detected - notify_cardPlaced");
                toRemove.add(listener);
            }
        });

        listeners.removeAll(toRemove);
    }

    public synchronized void notify_illegalMove(Model model) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.illegalMove(new ModelView(model));
            } catch (RemoteException e) {
                printAsync("Disconnection detected - notify_illegalMove");
                toRemove.add(listener);
            }
        });
    }

    public synchronized void notify_illegalMoveBecauseOf(Model model, String reason_why) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.illegalMoveBecauseOf(new ModelView(model), reason_why);
            } catch (RemoteException e) {
                printAsync("Disconnection detected - notify_illegalMoveBecauseOf");
                toRemove.add(listener);
            }
        });

        listeners.removeAll(toRemove);
    }

    public synchronized void notify_cardDrawn(Model model, int index) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.cardDrawn(new ModelView(model), index);
            } catch (RemoteException e) {
                printAsync("Disconnection detected - notify_cardDrawn");
                toRemove.add(listener);
            }
        });

        listeners.removeAll(toRemove);
    }

    public synchronized void notify_secondLastRound(Model model) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.secondLastRound(new ModelView(model));
            } catch (RemoteException e) {
                printAsync("Disconnection detected - notify_secondLastRound");
                toRemove.add(listener);
            }
        });

        listeners.removeAll(toRemove);
    }

    public synchronized void notify_lastRound(Model model) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.lastRound(new ModelView(model));
            } catch (RemoteException e) {
                printAsync("Disconnection detected - notify_lastRound");
                toRemove.add(listener);
            }
        });

        listeners.removeAll(toRemove);
    }

    public synchronized void notify_messageSent(Model model, String nick, Message message) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.messageSent(new ModelView(model), nick, message);
            } catch (RemoteException e) {
                printAsync("Disconnection detected - notify_messageSent");
                toRemove.add(listener);
            }
        });

        listeners.removeAll(toRemove);
    }

    public synchronized void notify_nextTurn(Model model) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.nextTurn(new ModelView(model));
            } catch (RemoteException e) {
                printAsync("Disconnection detected - notify_nextTurn");
                toRemove.add(listener);
            }
        });

        listeners.removeAll(toRemove);
    }

    public synchronized void notify_playerDisconnected(Model gamemodel, String nick) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.playerDisconnected(new ModelView(gamemodel), nick);
            } catch (RemoteException e) {
                printAsync("Disconnection detected - notify_playerDisconnected");
                toRemove.add(listener);
            }
        });

        listeners.removeAll(toRemove);
    }

    public void notify_playerLeft(Model model, String nick) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.playerLeft(new ModelView(model), nick);
            } catch (RemoteException e) {
                printAsync("Disconnection detected - notify_playerLeft");
                toRemove.add(listener);
            }
        });

        listeners.removeAll(toRemove);
    }

    /**
     * The removeListener method removes a observer from the list of listeners <br>
     * @param lis is the observer to remove
     */
    public synchronized void removeListener(Listener lis) {
        listeners.remove(lis);
    }

}