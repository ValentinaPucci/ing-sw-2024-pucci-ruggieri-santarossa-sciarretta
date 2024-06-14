package it.polimi.demo.main;

import it.polimi.demo.Constants;
import it.polimi.demo.main.utils.BoolAdd;
import it.polimi.demo.main.utils.StaticPromptValidator;
import it.polimi.demo.network.rmi.RMIServer;
import it.polimi.demo.network.socket.server.Server;

import java.io.IOException;

public class MainServer {

    public static void main(String... args) throws IOException {

        BoolAdd remoteIP = StaticPromptValidator.promptForIP("Insert remote IP (leave empty for localhost): ");
        if (remoteIP.isNotEmpty()) {
            Constants.serverIp = remoteIP.add();
            System.setProperty("java.rmi.server.hostname", remoteIP.add());
        }
        else
            System.setProperty("java.rmi.server.hostname", Constants.Remote_ip);

        // Here we start Socket Server
        new Server().start(Constants.Socket_port);
        // Here we bind RMI Server
        new RMIServer();
    }
}




