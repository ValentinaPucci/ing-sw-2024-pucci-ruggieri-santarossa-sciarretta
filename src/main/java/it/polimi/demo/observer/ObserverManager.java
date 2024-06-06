package it.polimi.demo.observer;

import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.GameModel;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;
import it.polimi.demo.model.Player;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.demo.networking.PrintAsync.printAsync;

// todo: all methods are rewritten, but javadoc are to be completed

/**
 * This class is used to handle the listeners of the game
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
    public synchronized void notify_playerJoined(GameModel model) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.playerJoined(new GameModelImmutable(model));
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
    public synchronized void notify_joinUnableGameFull(Player p, GameModel model) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.joinUnableGameFull(p, new GameModelImmutable(model));
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

    public synchronized void notify_PlayerIsReadyToStart(GameModel model, String nick) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.playerIsReadyToStart(new GameModelImmutable(model), nick);
            } catch (IOException e) {
                printAsync("Disconnection detected - notify_PlayerIsReadyToStart");
                toRemove.add(listener);
            }
        });
        listeners.removeAll(toRemove);
    }

    public synchronized void notify_GameStarted(GameModel model) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.gameStarted(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("Disconnection detected - notify_GameStarted");
                toRemove.add(listener);
            }
        });

        listeners.removeAll(toRemove);
    }

    public synchronized void notify_GameEnded(GameModel model) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.gameEnded(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("Disconnection detected - notify_GameEnded");
                toRemove.add(listener);
            }
        });

        listeners.removeAll(toRemove);
    }

    public synchronized void notify_starterCardPlaced(GameModel model, Orientation o, String nick) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.starterCardPlaced(new GameModelImmutable(model), o, nick);
            } catch (RemoteException e) {
                printAsync("Disconnection detected - notify_starterCardPlaced");
                toRemove.add(listener);
            }
        });

        listeners.removeAll(toRemove);
    }

    public synchronized void notify_cardChosen(GameModel model, int which_card) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.cardChosen(new GameModelImmutable(model), which_card);
            } catch (RemoteException e) {
                printAsync("Disconnection detected - notify_cardChosen");
                toRemove.add(listener);
            }
        });

        listeners.removeAll(toRemove);
    }

    public synchronized void notify_cardPlaced(GameModel model, int where_to_place_x, int where_to_place_y, Orientation o) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.cardPlaced(new GameModelImmutable(model), where_to_place_x, where_to_place_y, o);
            } catch (RemoteException e) {
                printAsync("Disconnection detected - notify_cardPlaced");
                toRemove.add(listener);
            }
        });

        listeners.removeAll(toRemove);
    }

    public synchronized void notify_illegalMove(GameModel model) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.illegalMove(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("Disconnection detected - notify_illegalMove");
                toRemove.add(listener);
            }
        });
    }

    public synchronized void notify_illegalMoveBecauseOf(GameModel model, String reason_why) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.illegalMoveBecauseOf(new GameModelImmutable(model), reason_why);
            } catch (RemoteException e) {
                printAsync("Disconnection detected - notify_illegalMoveBecauseOf");
                toRemove.add(listener);
            }
        });

        listeners.removeAll(toRemove);
    }

    public synchronized void notify_cardDrawn(GameModel model, int index) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.cardDrawn(new GameModelImmutable(model), index);
            } catch (RemoteException e) {
                printAsync("Disconnection detected - notify_cardDrawn");
                toRemove.add(listener);
            }
        });

        listeners.removeAll(toRemove);
    }

    public synchronized void notify_secondLastRound(GameModel model) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.secondLastRound(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("Disconnection detected - notify_secondLastRound");
                toRemove.add(listener);
            }
        });

        listeners.removeAll(toRemove);
    }

    public synchronized void notify_lastRound(GameModel model) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.lastRound(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("Disconnection detected - notify_lastRound");
                toRemove.add(listener);
            }
        });

        listeners.removeAll(toRemove);
    }

    public synchronized void notify_messageSent(GameModel model, String nick, Message message) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.messageSent(new GameModelImmutable(model), nick, message);
            } catch (RemoteException e) {
                printAsync("Disconnection detected - notify_messageSent");
                toRemove.add(listener);
            }
        });

        listeners.removeAll(toRemove);
    }

    public synchronized void notify_nextTurn(GameModel model) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.nextTurn(new GameModelImmutable(model));
            } catch (RemoteException e) {
                printAsync("Disconnection detected - notify_nextTurn");
                toRemove.add(listener);
            }
        });

        listeners.removeAll(toRemove);
    }

    public synchronized void notify_playerDisconnected(GameModel gamemodel, String nick) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.playerDisconnected(new GameModelImmutable(gamemodel), nick);
            } catch (RemoteException e) {
                printAsync("Disconnection detected - notify_playerDisconnected");
                toRemove.add(listener);
            }
        });

        listeners.removeAll(toRemove);
    }

    public void notify_playerLeft(GameModel gameModel, String nick) {
        List<Listener> toRemove = new ArrayList<>();

        listeners.forEach(listener -> {
            try {
                listener.playerLeft(new GameModelImmutable(gameModel), nick);
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