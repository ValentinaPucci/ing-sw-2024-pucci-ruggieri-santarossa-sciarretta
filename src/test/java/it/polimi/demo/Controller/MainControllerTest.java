package it.polimi.demo.Controller;

import it.polimi.demo.controller.MainController;
import it.polimi.demo.model.Player;
import it.polimi.demo.model.chat.Chat;
import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.GameStatus;
import it.polimi.demo.model.enumerations.Orientation;
import it.polimi.demo.model.exceptions.GameEndedException;
import it.polimi.demo.view.dynamic.GameDynamic;
import it.polimi.demo.view.dynamic.utilities.TypeConnection;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainControllerTest {

    MainController mainController = MainController.getControllerInstance();

    @Test
    public void createGameTest() throws RemoteException, GameEndedException {

        String current_player;

        // Listener associated to a player (the creator)
        GameDynamic gameDynamic1 = new GameDynamic(TypeConnection.RMI);
        String nickname1 = "Player1";

        // Listener associated to another player (the opponent)
        GameDynamic gameDynamic2 = new GameDynamic(TypeConnection.RMI);
        String nickname2 = "Player2";

        // Listener associated to another player (the opponent)
        GameDynamic gameDynamic3 = new GameDynamic(TypeConnection.RMI);
        String nickname3 = "Player3";

        // Create a new game
        mainController.createGame(gameDynamic1, nickname1, 3);
        // Join randomly
        mainController.joinRandomly(gameDynamic2, nickname2);
        // Join a specific game
        mainController.joinGame(gameDynamic3, nickname3, 0);

        // try to enter a full game
        GameDynamic gameDynamic4 = new GameDynamic(TypeConnection.RMI);
        String nickname4 = "Player4";

        try {
            mainController.joinGame(gameDynamic4, nickname4, 0);
        } catch (RemoteException e) {
            assert e.getMessage().equals("Game is full");
        }

        try {
            mainController.joinGame(gameDynamic4, nickname4, 1);
        } catch (RemoteException e) {
            assert e.getMessage().equals("Game not found");
        }

        assert mainController.getGameController(0).getConnectedPlayers().size() == 3;

        // Set as ready
        mainController.setAsReady(gameDynamic1, nickname1, 0);
        mainController.setAsReady(gameDynamic2, nickname2, 0);
        mainController.setAsReady(gameDynamic3, nickname3, 0);

        // the game is start
        assert mainController.getGameController(0).getStatus().equals(GameStatus.FIRST_ROUND);

        try {
            mainController.joinGame(gameDynamic4, nickname4, 0);
        } catch (RemoteException e) {
            assert e.getMessage().equals("Game currently not available: already started");
        }

        current_player = mainController.getGameController(0).getCurrentPlayer().getNickname();

        if (current_player.equals(nickname1)) {
            mainController.chooseCard(gameDynamic1, nickname1, 1, 0);
            mainController.placeStarterCard(gameDynamic1, nickname1, Orientation.FRONT, 0);
        } else if (current_player.equals(nickname2)) {
            mainController.chooseCard(gameDynamic2, nickname2, 1, 0);
            mainController.placeStarterCard(gameDynamic2, nickname2, Orientation.FRONT, 0);
        } else if (current_player.equals(nickname3)) {
            mainController.chooseCard(gameDynamic3, nickname3, 1, 0);
            mainController.placeStarterCard(gameDynamic3, nickname3, Orientation.FRONT, 0);
        }

        // the current player changes (it is the next player)

        current_player = mainController.getGameController(0).getCurrentPlayer().getNickname();

        if (current_player.equals(nickname1)) {
            mainController.chooseCard(gameDynamic1, nickname1, 1, 0);
            mainController.placeStarterCard(gameDynamic1, nickname1, Orientation.BACK, 0);
        } else if (current_player.equals(nickname2)) {
            mainController.chooseCard(gameDynamic2, nickname2, 1, 0);
            mainController.placeStarterCard(gameDynamic2, nickname2, Orientation.BACK, 0);
        } else if (current_player.equals(nickname3)) {
            mainController.chooseCard(gameDynamic3, nickname3, 1, 0);
            mainController.placeStarterCard(gameDynamic3, nickname3, Orientation.BACK, 0);
        }

        // the current player changes (it is the next player)

        current_player = mainController.getGameController(0).getCurrentPlayer().getNickname();

        if (current_player.equals(nickname1)) {
            mainController.chooseCard(gameDynamic1, nickname1, 1, 0);
            mainController.placeStarterCard(gameDynamic1, nickname1, Orientation.FRONT, 0);
        } else if (current_player.equals(nickname2)) {
            mainController.chooseCard(gameDynamic2, nickname2, 1, 0);
            mainController.placeStarterCard(gameDynamic2, nickname2, Orientation.FRONT, 0);
        } else if (current_player.equals(nickname3)) {
            mainController.chooseCard(gameDynamic3, nickname3, 1, 0);
            mainController.placeStarterCard(gameDynamic3, nickname3, Orientation.FRONT, 0);
        }

        // play the game card

        assert mainController.getGameController(0).getStatus().equals(GameStatus.RUNNING);

        Player p = mainController.getGameController(0).getCurrentPlayer();

        if (p.getNickname().equals(nickname1)) {
            mainController.sendMessage(gameDynamic1, nickname1, new Message("Hello", p), 0);
        } else if (p.getNickname().equals(nickname2)) {
            mainController.sendMessage(gameDynamic1, nickname1, new Message("Hello", p), 0);
        } else if (p.getNickname().equals(nickname3)) {
            mainController.sendMessage(gameDynamic1, nickname1, new Message("Hello", p), 0);
        }

        current_player = mainController.getGameController(0).getCurrentPlayer().getNickname();

        if (current_player.equals(nickname1)) {
            mainController.chooseCard(gameDynamic1, nickname1, 2, 0);
            mainController.placeCard(gameDynamic1, nickname1, 0, 0, Orientation.BACK, 0);
            mainController.drawCard(gameDynamic1, nickname1, 4, 0);
        } else if (current_player.equals(nickname2)) {
            mainController.chooseCard(gameDynamic2, nickname2, 1, 0);
            mainController.placeCard(gameDynamic2, nickname2, 0, 0, Orientation.BACK, 0);
            mainController.drawCard(gameDynamic2, nickname2, 6, 0);
        } else if (current_player.equals(nickname3)) {
            mainController.chooseCard(gameDynamic3, nickname3, 2, 0);
            mainController.placeCard(gameDynamic3, nickname3, 0, 0, Orientation.BACK, 0);
            mainController.drawCard(gameDynamic3, nickname3, 5, 0);
        }

        if (!current_player.equals(nickname1)) {
            try {
                mainController.chooseCard(gameDynamic1, nickname1, 2, 0);
                mainController.placeCard(gameDynamic1, nickname1, 0, 0, Orientation.BACK, 0);
            } catch (RemoteException e) {
                assert e.getMessage().equals("It's not your turn");
            }
        } else if (!current_player.equals(nickname2)) {
            try {
                mainController.chooseCard(gameDynamic2, nickname2, 1, 0);
                mainController.placeCard(gameDynamic2, nickname2, 0, 0, Orientation.BACK, 0);
            } catch (RemoteException e) {
                assert e.getMessage().equals("It's not your turn");
            }
        } else if (!current_player.equals(nickname3)) {
            try {
                mainController.chooseCard(gameDynamic3, nickname3, 2, 0);
                mainController.placeCard(gameDynamic3, nickname3, 0, 0, Orientation.BACK, 0);
            } catch (RemoteException e) {
                assert e.getMessage().equals("It's not your turn");
            }
        }

        current_player = mainController.getGameController(0).getCurrentPlayer().getNickname();

        try {
            mainController.placeCard(gameDynamic4, nickname4, 0, 0, Orientation.BACK, 0);
        } catch (RemoteException e) {
            assert e.getMessage().equals("Player not connected");
        }

        if (current_player.equals(nickname1)) {
            mainController.chooseCard(gameDynamic1, nickname1, 0, 0);
            mainController.placeCard(gameDynamic1, nickname1, 1, 1, Orientation.BACK, 0);
            mainController.drawCard(gameDynamic1, nickname1, 2, 0);
        } else if (current_player.equals(nickname2)) {
            mainController.chooseCard(gameDynamic2, nickname2, 2, 0);
            mainController.placeCard(gameDynamic2, nickname2, 0, 1, Orientation.BACK, 0);
            mainController.drawCard(gameDynamic2, nickname2, 3, 0);
        } else if (current_player.equals(nickname3)) {
            mainController.chooseCard(gameDynamic3, nickname3, 2, 0);
            mainController.placeCard(gameDynamic3, nickname3, 1, 0, Orientation.BACK, 0);
            mainController.drawCard(gameDynamic3, nickname3, 1, 0);
        }

        current_player = mainController.getGameController(0).getCurrentPlayer().getNickname();

        if (current_player.equals(nickname1)) {
            mainController.addPing(gameDynamic1, nickname1, 0);
        } else if (current_player.equals(nickname2)) {
            mainController.addPing(gameDynamic2, nickname2, 0);
        } else if (current_player.equals(nickname3)) {
            mainController.addPing(gameDynamic3, nickname3, 0);
        }

        mainController.leaveGame(gameDynamic1, nickname1, 0);
        mainController.leaveGame(gameDynamic2, nickname2, 0);
        mainController.leaveGame(gameDynamic3, nickname3, 0);

    }

    @Test
    public void hitDisconnectionTest() throws RemoteException, GameEndedException {

        String current_player;

        // Listener associated to a player (the creator)
        GameDynamic gameDynamic1 = new GameDynamic(TypeConnection.RMI);
        String nickname1 = "Player1";

        // Listener associated to another player (the opponent)
        GameDynamic gameDynamic2 = new GameDynamic(TypeConnection.RMI);
        String nickname2 = "Player2";

        // Listener associated to another player (the opponent)
        GameDynamic gameDynamic3 = new GameDynamic(TypeConnection.RMI);
        String nickname3 = "Player3";

        // Create a new game
        mainController.createGame(gameDynamic1, nickname1, 3);
        // Join randomly
        mainController.joinRandomly(gameDynamic2, nickname2);
        // Join a specific game
        mainController.joinGame(gameDynamic3, nickname3, 0);

        // try to enter a full game
        GameDynamic gameDynamic4 = new GameDynamic(TypeConnection.RMI);
        String nickname4 = "Player4";
        mainController.joinGame(gameDynamic4, nickname4, 0);

        assert mainController.getGameController(0).getConnectedPlayers().size() == 3;

        // Set as ready
        mainController.setAsReady(gameDynamic1, nickname1, 0);
        mainController.setAsReady(gameDynamic2, nickname2, 0);
        mainController.setAsReady(gameDynamic3, nickname3, 0);

        // the game is start
        assert mainController.getGameController(0).getStatus().equals(GameStatus.FIRST_ROUND);

        current_player = mainController.getGameController(0).getCurrentPlayer().getNickname();

        if (current_player.equals(nickname1)) {
            mainController.chooseCard(gameDynamic1, nickname1, 1, 0);
            mainController.placeStarterCard(gameDynamic1, nickname1, Orientation.FRONT, 0);
        } else if (current_player.equals(nickname2)) {
            mainController.chooseCard(gameDynamic2, nickname2, 1, 0);
            mainController.placeStarterCard(gameDynamic2, nickname2, Orientation.FRONT, 0);
        } else if (current_player.equals(nickname3)) {
            mainController.chooseCard(gameDynamic3, nickname3, 1, 0);
            mainController.placeStarterCard(gameDynamic3, nickname3, Orientation.FRONT, 0);
        }

        mainController.addPing(gameDynamic1, nickname1, 0);
        mainController.addPing(gameDynamic2, nickname2, 0);
        mainController.addPing(gameDynamic3, nickname3, 0);


        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testGetLastMessage() {
        Chat chat = new Chat();
        Player p1 = new Player("p1");
        Player p2 = new Player("p2");
        Message message1 = new Message("Hi", p1);
        Message message2 = new Message("Hello", p2);
        chat.addMessage(message1);
        chat.addMessage(message2);

        assertEquals(message2, chat.getLastMessage());
    }
}
