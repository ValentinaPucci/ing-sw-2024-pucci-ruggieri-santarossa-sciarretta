package it.polimi.demo.networking.socket.server;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.*;
import it.polimi.demo.model.gameModelImmutable.GameModelImmutable;
import it.polimi.demo.networking.socket.client.serverToClientMessages.*;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * This class is used to pass the GameListener to the client via socket
 * {@link GameListener}
 * It has a private ObjectOutputStream where it writes the data
 **/
public class GameListenersHandlerSocket implements GameListener, Serializable {

    private final ObjectOutputStream out;

    /**
     * This constructor creates a GameListenersHandlerSocket
     * @param o the ObjectOutputStream
     */
    public GameListenersHandlerSocket(ObjectOutputStream o) {
        out = o;
    }

    @Override
    public void starterCardPlaced(GameModelImmutable gamemodel, Orientation orientation, String nick) throws RemoteException {
        try {
            //Else the object is not updated!!
            out.writeObject(new msgStarterCardPlaced(gamemodel, orientation));
            finishSending();
        } catch (IOException e) {

        }

    }

    @Override
    public void cardChosen(GameModelImmutable model, int which_card) throws RemoteException {
        try {
            //Else the object is not updated!!
            out.writeObject(new msgCardChosen(model, which_card));
            finishSending();
        } catch (IOException e) {

        }

    }

    @Override
    public void cardDrawn(GameModelImmutable model, int index) throws RemoteException {
        try {
            //Else the object is not updated!!
            out.writeObject(new msgCardDrawn(model, index));
            finishSending();
        } catch (IOException e) {

        }

    }

    /**
     * This method is used to write on the ObjectOutputStream the message that a player has joined the game
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void playerJoined(GameModelImmutable gamemodel) throws RemoteException {
        try {
            out.writeObject(new msgPlayerJoined(gamemodel));
            finishSending();
        } catch (IOException e) {

        }
    }

    /**
     * This method is used to write on the ObjectOutputStream the message that a player has left the game
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @param nick is the nickname of the player
     * @throws RemoteException if the connection fails
     */
    @Override
    public void playerLeft(GameModelImmutable gamemodel,String nick) throws RemoteException {
        try {
            out.writeObject(new msgPlayerLeft(gamemodel,nick));
            finishSending();
        } catch (IOException e) {

        }
    }

    /**
     * This method is used to write on the ObjectOutputStream the message that a player has reconnected to the game
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @param nickPlayerReconnected is the nickname of the player
     * @throws RemoteException if the connection fails
     */
    @Override
    public void playerReconnected(GameModelImmutable gamemodel, String nickPlayerReconnected) throws RemoteException {
        //System.out.println(nickNewPlayer +" by socket");
        try {
            out.writeObject(new msgPlayerReconnected(gamemodel, nickPlayerReconnected));
            finishSending();
        } catch (IOException e) {

        }
    }

    /**
     * This method is used to write on the ObjectOutputStream the message that a player is unable to join the game because it is full
     * @param p is the player that has tried to join the game {@link Player}
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void joinUnableGameFull(Player p, GameModelImmutable gamemodel) throws RemoteException {
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
     * @param model is the game model {@link GameModelImmutable}
     * @param nick is the nickname of the player
     * @throws RemoteException if the connection fails
     */
    @Override
    public void playerIsReadyToStart(GameModelImmutable model, String nick) throws RemoteException {
        //System.out.println(nick +" ready to start by socket");
        try {
            out.writeObject(new msgPlayerIsReadyToStart(model, nick));
            finishSending();
        } catch (IOException e) {

        }
    }

    /**
     * This method is used to write on the ObjectOutputStream the game started
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void gameStarted(GameModelImmutable gamemodel) throws RemoteException {
        //System.out.println(gamemodel.getGameId() +" game started by socket");
        try {
            out.writeObject(new msgGameStarted(gamemodel));
            finishSending();
        } catch (IOException e) {

        }
    }

    /**
     * This method is used to write on the ObjectOutputStream that the game ended
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void gameEnded(GameModelImmutable gamemodel) throws RemoteException {
        try {
            out.writeObject(new msgGameEnded(gamemodel));
            finishSending();
        } catch (IOException e) {

        }
    }

    @Override
    public void secondLastRound(GameModelImmutable gamemodel) throws RemoteException {

    }

    /**
     * This method is used to write on the ObjectOutputStream that a message has been sent
     * @param gameModel is the game model {@link GameModelImmutable}
     * @param msg is the message sent {@link Message}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void messageSent(GameModelImmutable gameModel, Message msg) throws RemoteException {
        try {
            out.writeObject(new msgSentMessage(gameModel, msg));
            finishSending();
        } catch (IOException e) {

        }
    }


    @Override
    public void cardPlaced(GameModelImmutable gamemodel, int x, int y, Orientation orientation) throws RemoteException {
        try {
            //Else the object is not updated!!
            out.writeObject(new msgCardPlaced(gamemodel, x, y, orientation));
            finishSending();
        } catch (IOException e) {

        }
    }

    @Override
    public void illegalMove(GameModelImmutable model) throws RemoteException {

    }

    //TODO: Maybe add message for placed card in wrong position.


    /**
     * This method is used to write on the ObjectOutputStream that the next turn is started
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void nextTurn(GameModelImmutable gamemodel) throws RemoteException {
        try {
            out.writeObject(new msgNextTurn(gamemodel));
            finishSending();
        } catch (IOException e) {

        }
    }



    /**
     * This method is used to write on the ObjectOutputStream that a player has disconnected
     * @param gameModel is the game model {@link GameModelImmutable}
     * @param nick is the nickname of the player that has disconnected
     * @throws RemoteException if the connection fails
     */
    @Override
    public void playerDisconnected(GameModelImmutable gameModel,String nick) throws RemoteException {
        try {
            out.writeObject(new msgPlayerDisconnected(gameModel,nick));
            finishSending();
        } catch (IOException e) {

        }
    }


    /**
     * This method is used to write on the ObjectOutputStream that only one player is connected
     * @param gameModel is the game model {@link GameModelImmutable}
     * @param secondsToWaitUntilGameEnded is the number of seconds to wait until the game ends
     * @throws RemoteException if the connection fails
     */
    @Override
    public void onlyOnePlayerConnected(GameModelImmutable gameModel, int secondsToWaitUntilGameEnded) throws RemoteException {
        try {
            out.writeObject(new msgOnlyOnePlayerConnected(gameModel,secondsToWaitUntilGameEnded));
            finishSending();
        } catch (IOException e) {

        }
    }

    /**
     * This method is used to write on the ObjectOutputStream that the last circle is started
     * @param gamemodel is the game model {@link GameModelImmutable}
     * @throws RemoteException if the connection fails
     */
    @Override
    public void lastRound(GameModelImmutable gamemodel) throws RemoteException {
        try {
            out.writeObject(new msgLastRound(gamemodel));
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
