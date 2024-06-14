package it.polimi.demo.observer;

import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.Model;
import it.polimi.demo.model.enumerations.Coordinate;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.ModelView;
import it.polimi.demo.model.Player;
import it.polimi.demo.network.StaticPrinter;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ObserverManager implements Serializable {

    @Serial
    private static final long serialVersionUID = 6359099610166201305L;

    private List<Listener> listeners;

    public ObserverManager() {
        listeners = new ArrayList<>();
    }

    public synchronized void addListener(Listener obj) {
        listeners.add(obj);
    }

    public synchronized void removeListener(Listener lis) {
        listeners.remove(lis);
    }

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

    @FunctionalInterface
    private interface ListenerAction {
        void perform(Listener listener) throws IOException;
    }

    public synchronized void notify_playerJoined(Model model) {
        notifyListeners(listener -> listener.playerJoined(new ModelView(model)), "notify_playerJoined");
    }

    public synchronized void notify_joinUnableGameFull(Player p, Model model) {
        notifyListeners(listener -> listener.joinUnableGameFull(p, new ModelView(model)), "notify_joinUnableGameFull");
    }

    public synchronized void notify_joinUnableNicknameAlreadyIn(Player p) {
        notifyListeners(listener -> listener.joinUnableNicknameAlreadyIn(p), "notify_joinUnableNicknameAlreadyIn");
    }

    public synchronized void notify_PlayerIsReadyToStart(Model model, String nick) {
        notifyListeners(listener -> listener.playerIsReadyToStart(new ModelView(model), nick), "notify_PlayerIsReadyToStart");
    }

    public synchronized void notify_GameStarted(Model model) {
        notifyListeners(listener -> listener.gameStarted(new ModelView(model)), "notify_GameStarted");
    }

    public synchronized void notify_GameEnded(Model model) {
        notifyListeners(listener -> listener.gameEnded(new ModelView(model)), "notify_GameEnded");
    }

    public synchronized void notify_starterCardPlaced(Model model, Orientation o, String nick) {
        notifyListeners(listener -> listener.starterCardPlaced(new ModelView(model), o, nick), "notify_starterCardPlaced");
    }

    public synchronized void notify_cardChosen(Model model, int which_card) {
        notifyListeners(listener -> listener.cardChosen(new ModelView(model), which_card), "notify_cardChosen");
    }

    public synchronized void notify_cardPlaced(Model model, int where_to_place_x, int where_to_place_y, Orientation o) {
        notifyListeners(listener -> listener.cardPlaced(new ModelView(model), where_to_place_x, where_to_place_y, o), "notify_cardPlaced");
    }

    public void notify_showOthersPersonalBoard(Model model, int player_index, String playerNickname) {
        notifyListeners(listener -> listener.showOthersPersonalBoard(new ModelView(model), playerNickname, player_index), "notify_showOthersPersonalBoard");
    }

    public synchronized void notify_illegalMove(Model model) {
        notifyListeners(listener -> listener.illegalMove(new ModelView(model)), "notify_illegalMove");
    }

    public synchronized void notify_illegalMoveBecauseOf(Model model, String reason_why) {
        notifyListeners(listener -> listener.illegalMoveBecauseOf(new ModelView(model), reason_why), "notify_illegalMoveBecauseOf");
    }

    public synchronized void notify_cardDrawn(Model model, int index) {
        notifyListeners(listener -> listener.cardDrawn(new ModelView(model), index), "notify_cardDrawn");
    }

    public synchronized void notify_secondLastRound(Model model) {
        notifyListeners(listener -> listener.secondLastRound(new ModelView(model)), "notify_secondLastRound");
    }

    public synchronized void notify_lastRound(Model model) {
        notifyListeners(listener -> listener.lastRound(new ModelView(model)), "notify_lastRound");
    }

    public synchronized void notify_messageSent(Model model, String nick, Message message) {
        notifyListeners(listener -> listener.messageSent(new ModelView(model), nick, message), "notify_messageSent");
    }

    public synchronized void notify_nextTurn(Model model) {
        notifyListeners(listener -> listener.nextTurn(new ModelView(model)), "notify_nextTurn");
    }

    public synchronized void notify_playerDisconnected(Model gamemodel, String nick) {
        notifyListeners(listener -> listener.playerDisconnected(new ModelView(gamemodel), nick), "notify_playerDisconnected");
    }

    public void notify_playerLeft(Model model, String nick) {
        notifyListeners(listener -> listener.playerLeft(new ModelView(model), nick), "notify_playerLeft");
    }

    public void notify_successMove(Model model, Coordinate coordinate) {
        notifyListeners(listener -> listener.successfulMove(new ModelView(model), coordinate), "notify_successMove");
    }
}
