package it.polimi.demo.main;

import it.polimi.demo.Constants;
import it.polimi.demo.main.utils.BoolAdd;
import it.polimi.demo.main.utils.StaticPromptValidator;
import it.polimi.demo.network.rmi.RMIServer;
import it.polimi.demo.network.socket.server.SocketServer;

import java.io.IOException;

/**
 * MainServer is the main class for the server side of the application.
 */
public class MainServer {

    /**
     * Main method for the server side of the application.
     * @param args the arguments for the main method
     * @throws IOException if an I/O error occurs
     */
    public static void main(String... args) throws IOException {

        BoolAdd remoteIP = StaticPromptValidator.promptForIP("Insert remote IP (leave empty for localhost): ");
        if (remoteIP.isNotEmpty()) {
            Constants.serverIp = remoteIP.add();
            System.setProperty("java.rmi.server.hostname", remoteIP.add());
        }
        else
            System.setProperty("java.rmi.server.hostname", Constants.Remote_ip);

        // Here we start Socket SocketServer
        new SocketServer(Constants.Socket_port);
        // Here we bind RMI SocketServer
        new RMIServer();
    }
}




