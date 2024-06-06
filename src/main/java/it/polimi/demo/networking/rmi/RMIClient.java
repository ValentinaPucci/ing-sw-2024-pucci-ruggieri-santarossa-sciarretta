package it.polimi.demo.networking.rmi;

import it.polimi.demo.observer.Listener;
import it.polimi.demo.DefaultValues;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.networking.ObserverManagerClient;
import it.polimi.demo.networking.PingSender;
import it.polimi.demo.view.flow.ClientInterface;
import it.polimi.demo.view.flow.Dynamics;
import it.polimi.demo.networking.remoteInterfaces.GameControllerInterface;
import it.polimi.demo.networking.remoteInterfaces.MainControllerInterface;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.function.Consumer;

import static it.polimi.demo.networking.PrintAsync.printAsync;
import static it.polimi.demo.networking.PrintAsync.printAsyncNoLine;

public class RMIClient implements ClientInterface {

    private static MainControllerInterface asks;
    private GameControllerInterface controller = null;
    private static Listener lis;
    private String nickname;
    private int game_id;
    private boolean initialized = false;
    private final ObserverManagerClient gameListenersHandler;
    private Registry registry;
    private final Dynamics dynamics;
    private final PingSender rmiHeartbeat;

    public RMIClient(Dynamics dynamics) {
        this.dynamics = dynamics;
        this.gameListenersHandler = new ObserverManagerClient(dynamics);
        this.rmiHeartbeat = new PingSender(dynamics, this);
        this.rmiHeartbeat.start();
        connect();
    }

    private void connect() {
        int attempt = 1;
        while (attempt <= DefaultValues.num_of_attempt_to_connect_toServer_before_giveup) {
            try {
                registry = LocateRegistry.getRegistry(DefaultValues.serverIp, DefaultValues.Default_port_RMI);
                asks = (MainControllerInterface) registry.lookup(DefaultValues.Default_servername_RMI);
                lis = (Listener) UnicastRemoteObject.exportObject(gameListenersHandler, 0);
                printAsync("Client RMI ready");
                return;
            } catch (Exception e) {
                handleConnectionError(e, attempt++);
            }
        }
        printAsyncNoLine("Give up!");
        try { System.in.read(); } catch (IOException ex) { throw new RuntimeException(ex); }
        System.exit(-1);
    }

    private void handleConnectionError(Exception e, int attempt) {
        if (attempt == 1) printAsync("[ERROR] CONNECTING TO RMI SERVER: \n\tClient RMI exception: " + e + "\n");
        printRetryMessage(attempt);
        waitAndPrintDots(DefaultValues.seconds_between_reconnection);
    }

    private void printRetryMessage(int attempt) {
        printAsyncNoLine("[#" + attempt + "]Waiting to reconnect to RMI Server on port: '" + DefaultValues.Default_port_RMI + "' with name: '" + DefaultValues.Default_servername_RMI + "'");
    }

    private void waitAndPrintDots(int seconds) {
        try {
            for (int i = 0; i < seconds; i++) {
                Thread.sleep(1000);
                printAsyncNoLine(".");
            }
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        printAsyncNoLine("\n");
    }

    private void withRegistry(Consumer<Registry> action) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultValues.serverIp, DefaultValues.Default_port_RMI);
        asks = (MainControllerInterface) registry.lookup(DefaultValues.Default_servername_RMI);
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
    public void joinFirstAvailableGame(String nick) throws RemoteException, NotBoundException {
        withRegistry(reg -> {
            try {
                controller = asks.joinFirstAvailableGame(lis, nick);
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
    public void chooseCard(int which_card) throws RemoteException, NotBoundException, GameEndedException {
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

