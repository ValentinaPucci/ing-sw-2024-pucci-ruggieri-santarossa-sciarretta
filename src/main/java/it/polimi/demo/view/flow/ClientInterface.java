package it.polimi.demo.view.flow;

import it.polimi.demo.model.chat.Message;
import it.polimi.demo.model.enumerations.*;
import it.polimi.demo.model.exceptions.GameEndedException;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public interface ClientInterface extends Serializable {
    void createGame(String nickname, int num_of_players) throws IOException, InterruptedException, NotBoundException;

    void joinGame(String nick, int idGame) throws IOException, InterruptedException, NotBoundException;

    void joinFirstAvailableGame(String nick) throws IOException, InterruptedException, NotBoundException;

    void leave(String nick, int idGame) throws IOException, NotBoundException;

    void setAsReady() throws IOException, NotBoundException;

    void placeStarterCard(Orientation orientation) throws IOException, GameEndedException, NotBoundException;

    void chooseCard(int which_card) throws IOException, NotBoundException, GameEndedException;

    void placeCard(int where_to_place_x, int where_to_place_y, Orientation orientation) throws IOException, NotBoundException, GameEndedException;

    void drawCard(int index) throws IOException, GameEndedException, NotBoundException;

    void sendMessage(String receiver, Message msg) throws IOException, NotBoundException;

    void heartbeat() throws IOException, NotBoundException;

}
