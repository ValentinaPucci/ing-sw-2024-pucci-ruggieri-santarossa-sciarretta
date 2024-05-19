package it.polimi.demo.networking.Socket;

import it.polimi.demo.listener.GameListener;
import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.networking.ClientImpl;
import it.polimi.demo.controller.ControllerInterfaces.GameControllerInterface;
import it.polimi.demo.controller.ControllerInterfaces.MainControllerInterface;
import it.polimi.demo.networking.Server;
import it.polimi.demo.networking.Client;
import it.polimi.demo.networking.Socket.ClientProxy;
import it.polimi.demo.view.GameDetails;
import it.polimi.demo.view.GameView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.RemoteRef;
import java.util.List;

/**
 * This class is a proxy, a stub for the server, it is used by the client to communicate with the server.
 * Actually, to communicate with the clientProxy of the sever.

 */
public class ServerProxy  implements Server {
    private final String ip;
    private final int port;
    private ObjectInputStream in_deserialized;
    private ObjectOutputStream out_serialized;
    private Socket socket;

    public enum Methods {
        REGISTER,
        ADD_PLAYER_TO_GAME,
        CREATE,
        GET_GAMES_LIST,
        PLACE_STARTER_CARD,
        CHOOSE_CARD,
        PLACE_CARD,
        DRAW_CARD,
        PONG
    }
    /**
     * Constructor of the class
     *
     * @param ip   the ip of the server
     * @param port the port of the server
     */
    public ServerProxy(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }
    /**
     * Register a client to the server.
     * @param client the client to register
     * @throws RemoteException
     */
    @Override
    public void register(Client client) throws RemoteException {
        try {
            this.socket = new Socket(ip, port);
            try{
                this.out_serialized = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                throw new RemoteException("Cannot create OUTPUT stream", e);
            }
            try{

                this.in_deserialized = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                throw new RemoteException("Cannot create INPUT stream", e);
            }
        } catch (IOException e) {
            throw new RemoteException("Cannot register client", e);
        }
    }

    /**
     * Close the socket connection with the server
     * @throws RemoteException
     */
        public void close() throws RemoteException {
            try {
                System.out.println("CLOSING CONNECTION ...");
                socket.close();
            } catch (IOException e) {
                throw new RemoteException("Cannot close connection", e);
            }
        }
        @Override
        public void addPlayerToGame ( int gameID, String username) throws RemoteException {
            try {
                out_serialized.reset();
                out_serialized.writeObject(Methods.ADD_PLAYER_TO_GAME);
                out_serialized.writeObject(gameID);
                out_serialized.writeObject(username);
                out_serialized.flush();
            } catch (IOException e) {
                throw new RemoteException("Cannot add player to game", e);
            }
        }

        @Override
        public void create (String username,int numberOfPlayers) throws RemoteException {
            try {
                out_serialized.reset();
                out_serialized.writeObject(Methods.CREATE);
                out_serialized.writeObject(username);
                out_serialized.writeObject(numberOfPlayers);
                out_serialized.flush();
            } catch (IOException e) {
                throw new RemoteException("Cannot create game", e);
            }
        }

        @Override
        public void getGamesList () throws RemoteException {
            try {
                out_serialized.reset();
                out_serialized.writeObject(Methods.GET_GAMES_LIST);
                out_serialized.flush();
            } catch (IOException e) {
                throw new RemoteException("Cannot get games list", e);
            }
        }

    @Override
    public void placeStarterCard(Orientation orientation) throws RemoteException {
            try{
                out_serialized.reset();
                out_serialized.writeObject(Methods.PLACE_STARTER_CARD);
                out_serialized.writeObject(orientation);
                out_serialized.flush();
            } catch (IOException e) {
                throw new RemoteException("Cannot place starter card", e);

            }

    }

    @Override
    public void chooseCard(int which_card) throws RemoteException {
            try {
                out_serialized.reset();
                out_serialized.writeObject(Methods.CHOOSE_CARD);
                out_serialized.writeObject(which_card);
                out_serialized.flush();
            } catch (IOException e) {
                throw new RemoteException("Cannot choose card", e);
            }
    }

    @Override
    public void placeCard(int where_to_place_x, int where_to_place_y, Orientation orientation) throws RemoteException {
        try {
            out_serialized.reset();
            out_serialized.writeObject(Methods.PLACE_CARD);
            out_serialized.writeObject(where_to_place_x);
            out_serialized.writeObject(where_to_place_y);
            out_serialized.writeObject(orientation);
            out_serialized.flush();
        } catch (IOException e) {
            throw new RemoteException("Cannot place card", e);
        }
    }

    @Override
    public void drawCard(int index) throws RemoteException {
            try {
                out_serialized.reset();
                out_serialized.writeObject(Methods.DRAW_CARD);
                out_serialized.writeObject(index);
                out_serialized.flush();
            } catch (IOException e) {
                throw new RemoteException("Cannot draw card", e);
            }
    }

    @Override
        public void pong () throws RemoteException {
            try {
                out_serialized.reset();
                out_serialized.writeObject(Methods.PONG);
                out_serialized.flush();
            } catch (IOException e) {
                throw new RemoteException("Cannot pong", e);
            }
        }

        public void ReceiveFromClient(Client client) throws RemoteException {
            try {
                ClientProxy.Methods client_methods = (ClientProxy.Methods) in_deserialized.readObject();
                switch (client_methods) {
                    case UPDATE_GAMES_LIST -> client.updateGamesList((List<GameDetails>) in_deserialized.readObject());
                    case SHOW_ERROR -> client.showError((String) in_deserialized.readObject());
                    case UPDATE_PLAYERS_LIST -> client.updatePlayersList((List<String>) in_deserialized.readObject());
                    case GAME_HAS_STARTED -> client.gameHasStarted();
                    case MODEL_CHANGED -> client.modelChanged((GameView) in_deserialized.readObject());
                    case GAME_ENDED -> client.gameEnded((GameView) in_deserialized.readObject());
                    case PING -> client.ping();
                }
            } catch (SocketException e) {
                // Entra qus
                System.exit(0);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Cannot deserialize object", e);
            } catch (IOException e) {
                throw new RemoteException("Connection error", e);
            }
        }

    }
