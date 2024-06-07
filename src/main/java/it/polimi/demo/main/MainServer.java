package it.polimi.demo.main;

import it.polimi.demo.DefaultValues;
import it.polimi.demo.networking.rmi.RMIServer;
import it.polimi.demo.networking.socket.server.Server;

import java.io.IOException;
import java.util.Scanner;
import java.util.function.Consumer;

// todo: it is checked
public class MainServer {

    public static void main(String[] args) {
        String remoteIP = retrieveRemoteIP();
        setupSystemProperty(remoteIP);
        startServer(port -> {
            try {
                new Server().start(port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, DefaultValues.Default_port_Socket);
        startServer(port -> {
            RMIServer.bind();
        }, 0);
    }

    private static String retrieveRemoteIP() {
        return new Scanner(System.in).nextLine();
    }

    private static void setupSystemProperty(String remoteIP) {
        System.setProperty("java.rmi.server.hostname", remoteIP.isEmpty() ? DefaultValues.Remote_ip : remoteIP);
    }

    private static void startServer(Consumer<Integer> serverStarter, int port) {
        serverStarter.accept(port);
    }
}



