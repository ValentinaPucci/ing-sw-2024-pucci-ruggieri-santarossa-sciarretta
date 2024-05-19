package it.polimi.demo.networking;

import it.polimi.demo.model.cards.gameCards.GoldCard;
import it.polimi.demo.model.cards.gameCards.ResourceCard;
import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.model.exceptions.GameNotStartedException;
import it.polimi.demo.model.exceptions.InvalidChoiceException;
import org.fusesource.jansi.Ansi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
    public void placeStarterCard(Orientation orientation) throws RemoteException {
        executorService.submit(() -> {
            try {
                server.placeStarterCard(orientation);
            } catch (RemoteException | GameEndedException e) {
                try {
                    client.showError("Error while placing the starter card.");
                } catch (RemoteException ignored) {}
            }
        });
    }

    @Override
    public void chooseCard(int which_card) throws RemoteException {
        executorService.submit(() -> {
            try {
                server.chooseCard(which_card);
            } catch (RemoteException e) {
                try {
                    client.showError("Error while choosing the card.");
                } catch (RemoteException ignored) {}
            }
        });
    }

    @Override
    public void placeCard(int where_to_place_x, int where_to_place_y, Orientation orientation) throws RemoteException {
        executorService.submit(() -> {
            try {
                server.placeCard(where_to_place_x, where_to_place_y, orientation);
            } catch (RemoteException e) {
                try {
                    client.showError("Error while placing the card.");
                } catch (RemoteException ignored) {}
            }
        });
    }

    @Override
    public void drawCard(int index) throws RemoteException {
        executorService.submit(() -> {
            try {
                server.drawCard(index);
            } catch (RemoteException | GameEndedException e) {
                try {
                    client.showError("Error while drawing the card.");
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
