package it.polimi.demo.networking.Socket;


import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.model.exceptions.InvalidChoiceException;
import it.polimi.demo.networking.Client;
import it.polimi.demo.networking.Server;
import it.polimi.demo.view.GameDetails;
import it.polimi.demo.view.GameView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * This class is a proxy, a stub for the server, it is used by the server to communicate with the client and with the
 * 'server implementation'.
 * Actually, to communicate with the clientProxy of the sever.
 *
 * ALL THE METHODS THAT CAN BE CALLED ON THE CLIENT:
 *

 */
public class ClientProxy implements Client {

    private final ObjectOutputStream out_serialized;
    private final ObjectInputStream in_deserialized;

    /**
     * This enum is used to define the methods that can be called on the client.
     */
    public enum Methods{
        UPDATE_GAMES_LIST,
        SHOW_ERROR,
        UPDATE_PLAYERS_LIST,
        GAME_HAS_STARTED,
        MODEL_CHANGED,
        GAME_ENDED,
        PING
    }

    /**
     * Constructor of the class
     * @param socket
     * @throws RemoteException
     */
    public ClientProxy(Socket socket) throws RemoteException {
        try {
            this.out_serialized = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RemoteException("Cannot create output stream", e);
        }
        try {
            this.in_deserialized = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RemoteException("Cannot create input stream", e);
        }
    }

    @Override
    public void updateGamesList(List<GameDetails> o) throws RemoteException{
        try {
            out_serialized.reset();
            out_serialized.writeObject(Methods.UPDATE_GAMES_LIST);
            out_serialized.writeObject(o);
            out_serialized.flush();
            // Just because it is not a void method
        } catch (IOException e) {
            throw new RemoteException("error, cannot serialize the object", e);
        }
    }

    @Override
    public void  showError(String err) throws RemoteException{
        try {
            out_serialized.reset();
            out_serialized.writeObject(Methods.SHOW_ERROR);
            out_serialized.writeObject(err);
            out_serialized.flush();
            // Just because it is not a void method
        } catch (IOException e) {
            throw new RemoteException("error, cannot serialize the object", e);
        }
    }

    @Override
    public void updatePlayersList(List<String> o) throws RemoteException{
        try {
            out_serialized.reset();
            out_serialized.writeObject(Methods.UPDATE_PLAYERS_LIST); // Corrected enum value
            out_serialized.writeObject(o);
            out_serialized.flush();
        } catch (IOException e) {
            throw new RemoteException("error, cannot serialize the object", e);
        }
    }

    // ****************************** Status of the game ******************************

    @Override
    public void showStatus(GameStatus status) throws RemoteException {

    }

    // *******************************************************************************

    @Override
    public void gameUnavailable() throws RemoteException {

    }

    @Override
    public void gameHasStarted() throws RemoteException{
        try {
            out_serialized.reset();
            out_serialized.writeObject(Methods.GAME_HAS_STARTED);
            out_serialized.flush();
            // Just because it is not a void method
        } catch (IOException e) {
            throw new RemoteException("error, cannot serialize the object", e);
        }
    }

    @Override
    public void modelChanged(GameView gameView) throws RemoteException{
        try {
            out_serialized.reset();
            out_serialized.writeObject(Methods.MODEL_CHANGED);
            out_serialized.writeObject(gameView);
            out_serialized.flush();
            // Just because it is not a void method
        } catch (IOException e) {
            throw new RemoteException("error, cannot serialize the object", e);
        }
    }

    @Override
    public void nextTurn() throws RemoteException {

    }

    @Override
    public void gameEnded(GameView gameView) throws RemoteException{
        try {
            out_serialized.reset();
            out_serialized.writeObject(Methods.GAME_ENDED);
            out_serialized.writeObject(gameView);
            out_serialized.flush();
            // Just because it is not a void method
        } catch (IOException e) {
            throw new RemoteException("error, cannot serialize the object", e);
        }
    }

    @Override
    public void ping() throws RemoteException {
        try {
            out_serialized.reset();
            out_serialized.writeObject(Methods.PING);
            out_serialized.flush();
        } catch (IOException e) {
            throw new RemoteException("error, cannot serialize the object", e);
        }
    }

    // Receive methods:
    public void receiveFromServer(Server server) throws RemoteException{
        try{
            ServerProxy.Methods server_methods = (ServerProxy.Methods) in_deserialized.readObject();
            switch(server_methods){
                case REGISTER:
                    server.register((Client) in_deserialized.readObject());
                    break;
                case ADD_PLAYER_TO_GAME:
                    try {
                        server.addPlayerToGame((int) in_deserialized.readObject(), (String) in_deserialized.readObject());
                    } catch (InvalidChoiceException | GameEndedException e) {
                        // Exception thrown by the server
                        System.err.println("Invalid choice: " + e.getMessage());
                    }
                    break;
                case CREATE:
                    //System.out.println(" " + (String) in_deserialized.readObject() + " " + (int) in_deserialized.readObject());
                    server.create((String) in_deserialized.readObject(), (int) in_deserialized.readObject());
                    break;
                case GET_GAMES_LIST:
                    server.getGamesList();
                    break;
                case PONG:
                    server.pong();
                    break;
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RemoteException("error, cannot deserialize the object or cannot connect", e);
        }
    }

}
