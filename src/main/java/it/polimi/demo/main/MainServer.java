package it.polimi.demo.main;

import it.polimi.demo.DefaultValues;
import it.polimi.demo.networking.rmi.RMIServer;
import it.polimi.demo.networking.socket.server.Server;

import java.io.IOException;
import java.util.Scanner;

public class MainServer {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter remote IP (empty for default):");
        String remoteIP = scanner.nextLine();

        if (remoteIP.isEmpty()) {
            remoteIP = DefaultValues.Remote_ip;
        }
        System.setProperty("java.rmi.server.hostname", remoteIP);

        // Here we start Socket Server
        try {
            new Server().start(DefaultValues.Default_port_Socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Here we start RMI Server
        RMIServer.bind();
    }
}




