package it.polimi.demo.network.rmi;

import it.polimi.demo.network.StaticPrinter;
import it.polimi.demo.observer.Listener;
import it.polimi.demo.Constants;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.*;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.network.ObserverManagerClient;
import it.polimi.demo.network.PingSender;
import it.polimi.demo.view.flow.ClientInterface;
import it.polimi.demo.view.flow.Dynamics;
import it.polimi.demo.network.interfaces.GameControllerInterface;
import it.polimi.demo.network.interfaces.MainControllerInterface;

import java.io.IOException;
import java.io.Serial;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.function.Consumer;

public class RMIClient implements ClientInterface {

    @Serial
    private static final long serialVersionUID = 5576020039171499792L;

    private static MainControllerInterface asks;
    private GameControllerInterface controller = null;

    private int game_id;
    private String nickname;

    private Registry registry;

    private final ObserverManagerClient gameListenersHandler;
    private static Listener lis;

    private boolean initialized = false;
    private final PingSender ping;

    public RMIClient(Dynamics dynamics) {
        this.gameListenersHandler = new ObserverManagerClient(dynamics);
        this.ping = new PingSender(dynamics, this);
        this.ping.start();
        initialize();
    }

    private void initialize() {
        try {
            registry = LocateRegistry.getRegistry(Constants.serverIp, Constants.RMI_port);
            asks = (MainControllerInterface) registry.lookup(Constants.RMI_server_name);
            lis = (Listener) UnicastRemoteObject.exportObject(gameListenersHandler, 0);
            StaticPrinter.staticPrinter("Client RMI ready");
        } catch (Exception e) {
            StaticPrinter.staticPrinter("Connection failed");
        }
    }

    private void withRegistry(Consumer<Registry> action) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(Constants.serverIp, Constants.RMI_port);
        asks = (MainControllerInterface) registry.lookup(Constants.RMI_server_name);
        action.accept(registry);
    }

    @Override
    public void createGame(String nick, int num_of_players) throws RemoteException, NotBoundException {
        withRegistry(reg -> {
            try {
                controller = asks.createGame(lis, nick, num_of_players);
                nickname = nick;
                game_id = controller.getGameId();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void joinGame(String nick, int idGame) throws RemoteException, NotBoundException {
        withRegistry(reg -> {
            try {
                controller = asks.joinGame(lis, nick, idGame);
                if (controller != null) {
                    nickname = nick;
                    game_id = controller.getGameId();
                    initialized = controller.getNumConnectedPlayers() == controller.getNumPlayersToPlay();
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void joinRandomly(String nick) throws RemoteException, NotBoundException {
        withRegistry(reg -> {
            try {
                controller = asks.joinRandomly(lis, nick);
                if (controller != null) {
                    nickname = nick;
                    game_id = controller.getGameId();
                    initialized = controller.getNumConnectedPlayers() == controller.getNumPlayersToPlay();
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void setAsReady() throws RemoteException, NotBoundException {
        withRegistry(reg -> {
            try {
                asks.setAsReady(lis, nickname, game_id);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void placeStarterCard(Orientation orientation) throws RemoteException, GameEndedException, NotBoundException {
        withRegistry(reg -> {
            try {
                asks.placeStarterCard(lis, nickname, orientation, game_id);
            } catch (RemoteException | GameEndedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void chooseCard(int which_card) throws RemoteException, NotBoundException {
        withRegistry(reg -> {
            try {
                asks.chooseCard(lis, nickname, which_card, game_id);
            } catch (RemoteException | GameEndedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void placeCard(int x, int y, Orientation orientation) throws RemoteException, NotBoundException, GameEndedException {
        withRegistry(reg -> {
            try {
                asks.placeCard(lis, nickname, x, y, orientation, game_id);
            } catch (RemoteException | GameEndedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void drawCard(int index) throws RemoteException, GameEndedException, NotBoundException {
        withRegistry(reg -> {
            try {
                asks.drawCard(lis, nickname, index, game_id);
            } catch (RemoteException | GameEndedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void sendMessage(String nick, Message msg) throws RemoteException, NotBoundException {
        withRegistry(reg -> {
            try {
                asks.sendMessage(lis, nick, msg, game_id);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void heartbeat() throws RemoteException, NotBoundException {
        if (initialized) {
            withRegistry(reg -> {
                try {
                    asks.addPing(lis, nickname, game_id);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @Override
    public void leave(String nick, int idGame) throws IOException, NotBoundException {
        withRegistry(reg -> {
            try {
                asks.leaveGame(lis, nick, idGame);
                controller = null;
                nickname = null;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
