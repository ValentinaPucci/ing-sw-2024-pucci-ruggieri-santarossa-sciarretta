package it.polimi.demo.main;

import it.polimi.demo.Constants;
import it.polimi.demo.network.rmi.RMIServer;
import it.polimi.demo.network.socket.server.Server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class MainServer {

    public static void main(String[] args) throws RemoteException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter remote IP (empty for default):");
        String remoteIP = scanner.nextLine();

        if (remoteIP.isEmpty()) {
            remoteIP = Constants.Remote_ip;
        }
        System.setProperty("java.rmi.server.hostname", remoteIP);

        // Here we start Socket Server
        try {
            new Server().start(Constants.Socket_port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Here we bind RMI Server
        new RMIServer();
    }
}




