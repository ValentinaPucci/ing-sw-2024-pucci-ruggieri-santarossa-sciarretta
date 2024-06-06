package it.polimi.demo.networking.socket.server;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.*;
import it.polimi.demo.model.ModelView;
import it.polimi.demo.networking.socket.client.serverToClientMessages.*;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * This class is used to pass the GameListener to the client via socket
 * {@link Listener}
 * It has a private ObjectOutputStream where it writes the data
 **/
public class GameListenersHandlerSocket implements Listener, Serializable {


    private final transient ObjectOutputStream out;

    /**
     * This constructor creates a GameListenersHandlerSocket
     * @param o the ObjectOutputStream
     */
    public GameListenersHandlerSocket(ObjectOutputStream o) {
        out = o;
    }

    @Override
    public void starterCardPlaced(ModelView gamemodel, Orientation orientation, String nick) throws RemoteException {
        try {
            //Else the object is not updated!!
            out.writeObject(new msgStarterCardPlaced(gamemodel, orientation));
            finishSending();
        } catch (IOException e) {

        }

    }

    @Override
    public void cardChosen(ModelView model, int which_card) throws RemoteException {
        try {
            //Else the object is not updated!!
            out.writeObject(new msgCardChosen(model, which_card));
            finishSending();
        } catch (IOException e) {

        }

    }

    @Override
    public void cardDrawn(ModelView model, int index) throws RemoteException {
        try {
            //Else the object is not updated!!
            out.writeObject(new msgCardDrawn(model, index));
            finishSending();
        } catch (IOException e) {

        }

    }

    /**
     * This method is used to write on the ObjectOutputStream the message that a player has joined the game
     * @param gamemodel is the game model {@link ModelView}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void playerJoined(ModelView gamemodel) throws RemoteException {
        try {
            out.writeObject(new msgPlayerJoined(gamemodel));
            finishSending();
        } catch (IOException e) {

        }
    }

    /**
     * This method is used to write on the ObjectOutputStream the message that a player has left the game
     * @param gamemodel is the game model {@link ModelView}
     * @param nick is the nickname of the player
     * @throws RemoteException if the connection fails
     */
    @Override
    public void playerLeft(ModelView gamemodel, String nick) throws RemoteException {
        try {
            out.writeObject(new msgPlayerLeft(gamemodel,nick));
            finishSending();
        } catch (IOException e) {

        }
    }

    /**
     * This method is used to write on the ObjectOutputStream the message that a player is unable to join the game because it is full
     * @param p is the player that has tried to join the game {@link Player}
     * @param gamemodel is the game model {@link ModelView}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void joinUnableGameFull(Player p, ModelView gamemodel) throws RemoteException {
        try {
            out.writeObject(new msgJoinUnableGameFull(p,gamemodel));
            finishSending();
        } catch (IOException e) {

        }
    }

    /**
     * This method is used to write on the ObjectOutputStream the message that a player is unable to join the game because the nickname is already in use
     * @param wantedToJoin is the player that has tried to join the game {@link Player}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void joinUnableNicknameAlreadyIn(Player wantedToJoin) throws RemoteException {
        try {
            out.writeObject(new msgJoinUnableNicknameAlreadyIn(wantedToJoin));
            finishSending();
        } catch (IOException e) {

        }
    }

    /**
     * This method is used to write on the ObjectOutputStream the message that the gameID is not valid
     * @param gameid is the id of the game
     * @throws RemoteException if the connection fails
     */
    @Override
    public void gameIdNotExists(int gameid) throws RemoteException {
        try {
            out.writeObject(new msgGameIdNotExists(gameid));
            finishSending();
        } catch (IOException e) {

        }
    }

    /**
     * This method is used to write on the ObjectOutputStream a generic error when entering the game
     * @param why is the reason why the error happened
     * @throws RemoteException if the connection fails
     */
    @Override
    public void genericErrorWhenEnteringGame(String why) throws RemoteException {
        try {
            out.writeObject(new msgGenericErrorWhenEntryingGame(why));
            finishSending();
        } catch (IOException e) {

        }
    }

    /**
     * This method is used to write on the ObjectOutputStream the message that player is ready to start
     * @param model is the game model {@link ModelView}
     * @param nick is the nickname of the player
     * @throws RemoteException if the connection fails
     */
    @Override
    public void playerIsReadyToStart(ModelView model, String nick) throws RemoteException {
        //System.out.println(nick +" ready to start by socket");
        try {
            out.writeObject(new msgPlayerIsReadyToStart(model, nick));
            finishSending();
        } catch (IOException e) {

        }
    }

    /**
     * This method is used to write on the ObjectOutputStream the game started
     * @param gamemodel is the game model {@link ModelView}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void gameStarted(ModelView gamemodel) throws RemoteException {
        //System.out.println(gamemodel.getGameId() +" game started by socket");
        try {
            out.writeObject(new msgGameStarted(gamemodel));
            finishSending();
        } catch (IOException e) {

        }
    }

    /**
     * This method is used to write on the ObjectOutputStream that the game ended
     * @param gamemodel is the game model {@link ModelView}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void gameEnded(ModelView gamemodel) throws RemoteException {
        try {
            out.writeObject(new msgGameEnded(gamemodel));
            finishSending();
        } catch (IOException e) {

        }
    }

    @Override
    public void secondLastRound(ModelView gamemodel) throws RemoteException {
        try {
            out.writeObject(new msgSecondLastRound(gamemodel));
            finishSending();
        } catch (IOException e) {

        }
    }

    /**
     * This method is used to write on the ObjectOutputStream that the player has finished his turn
     * @throws RemoteException if the connection fails
     */
    @Override
    public void cardPlaced(ModelView gamemodel, int x, int y, Orientation orientation) throws RemoteException {
        try {
            //Else the object is not updated!!
            out.writeObject(new msgCardPlaced(gamemodel, x, y, orientation));
            finishSending();
        } catch (IOException e) {

        }
    }

    @Override
    public void illegalMove(ModelView model) throws RemoteException {
        try {
            //Else the object is not updated!!
            out.writeObject(new msgIllegalMove(model));
            finishSending();
        } catch (IOException e) {

        }
    }

    @Override
    public void illegalMoveBecauseOf(ModelView model, String reason_why) throws RemoteException {
        try {
            //Else the object is not updated!!
            out.writeObject(new msgIllegalMoveBecauseOf(model, reason_why));
            finishSending();
        } catch (IOException e) {

        }
    }

    /**
     * This method is used to write on the ObjectOutputStream that the next turn is started
     * @param gamemodel is the game model {@link ModelView}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void nextTurn(ModelView gamemodel) throws RemoteException {
        try {
            out.writeObject(new msgNextTurn(gamemodel));
            finishSending();
        } catch (IOException e) {

        }
    }

    /**
     * This method is used to write on the ObjectOutputStream that a player has disconnected
     * @param gameModel is the game model {@link ModelView}
     * @param nick is the nickname of the player that has disconnected
     * @throws RemoteException if the connection fails
     */
    @Override
    public void playerDisconnected(ModelView gameModel, String nick) throws RemoteException {
        try {
            out.writeObject(new msgPlayerDisconnected(gameModel,nick));
            finishSending();
        } catch (IOException e) {

        }
    }

    /**
     * This method is used to write on the ObjectOutputStream that the last circle is started
     * @param gamemodel is the game model {@link ModelView}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void lastRound(ModelView gamemodel) throws RemoteException {
        try {
            out.writeObject(new msgLastRound(gamemodel));
            finishSending();
        } catch (IOException e) {

        }
    }

    @Override
    public void messageSent(ModelView gameModel, String nickname, Message message) throws RemoteException {
        try {
            out.writeObject(new msgMessageSent(gameModel, nickname, message));
            finishSending();
        } catch (IOException e) {

        }
    }

    /**
     * Makes sure the message has been sent
     * @throws IOException
     */
    private void finishSending() throws IOException {
        out.flush();
        out.reset();
    }


}
