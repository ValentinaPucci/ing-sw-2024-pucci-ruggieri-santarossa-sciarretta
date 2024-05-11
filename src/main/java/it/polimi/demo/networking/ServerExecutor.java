package it.polimi.demo.networking;

import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.model.exceptions.GameNotStartedException;
import it.polimi.demo.model.exceptions.InvalidChoiceException;
import org.fusesource.jansi.Ansi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static it.polimi.demo.networking.PrintAsync.printAsync;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * This class wraps the server methods in order to execute them in a separate thread.
 * This is done in order to avoid blocking RMI calls, which could cause delays.
 */
public class ServerExecutor extends UnicastRemoteObject implements Server {

    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    private final Server server;
    private Client client;

    public ServerExecutor(Server server) throws RemoteException {
        super();
        this.server = server;
    }

    @Override
    public void register(Client client) throws RemoteException {
        this.client = client;
        executorService.submit(() -> {
            try {
                server.register(client);
            } catch (RemoteException e) {
                try {
                    client.showError("Error while registering to the server.");
                } catch (RemoteException ignored) {}
            }
        });
    }

    @Override
    public void addPlayerToGame(int gameID, String username) throws RemoteException {
        executorService.submit(() -> {
            try {
                server.addPlayerToGame(gameID, username);
            } catch (RemoteException | GameNotStartedException e) {
                try {
                    client.showError(e.getMessage());
                } catch (RemoteException ignored) {}
            } catch (InvalidChoiceException | GameEndedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void create(String username, int numberOfPlayers) throws RemoteException {
        executorService.submit(() -> {
            try {
                server.create(username, numberOfPlayers);
            } catch (RemoteException | GameNotStartedException e) {
                try {
                    client.showError(e.getMessage());
                } catch (RemoteException ignored) {}
            }
        });
    }

    @Override
    public void getGamesList() throws RemoteException {
        executorService.submit(() -> {
            try {
                server.getGamesList();
            } catch (RemoteException e) {
                try {
                    client.showError("Error while getting the games list.");
                } catch (RemoteException ignored) {}
            }
        });
    }

    @Override
    public void pong() throws RemoteException {
        executorService.submit(() -> {
            try {
                server.pong();
            } catch (RemoteException e) {
                try {
                    client.showError("Error while responding to the ping.");
                } catch (RemoteException ignored) {}
            }
        });
    }
}
