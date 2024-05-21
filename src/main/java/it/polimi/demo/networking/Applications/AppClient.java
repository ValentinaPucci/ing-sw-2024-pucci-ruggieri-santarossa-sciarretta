package it.polimi.demo.networking.Applications;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AccessException;
import java.util.logging.Logger;
import it.polimi.demo.DefaultValues;
import it.polimi.demo.networking.ClientImpl;
import it.polimi.demo.networking.ConnectionType;
import it.polimi.demo.networking.Server;
import it.polimi.demo.networking.ServerImpl;
import it.polimi.demo.networking.Socket.ClientProxy;
import it.polimi.demo.view.UI.TUI.TextualUtils;
import it.polimi.demo.view.UI.UIType;
import it.polimi.demo.networking.Socket.ServerProxy;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;


public class AppClient {
    private static UIType uiType;
    private static ConnectionType connectionType;
    private static String ip;
    private static int port;
    private static int socketPort = DefaultValues.Default_port_SOCKET;
    private static AppServer instance;

    /**
     * Starts a client application.
     * @param args client type (RMI or SOCKET), server IP and server port
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        askArgs(in);
        System.out.println("Select UI type:");
        for (UIType value : UIType.values()) {
            System.out.println((value.ordinal() + 1) + ". " + value);
        }
        int uiTypeInt = TextualUtils.nextInt(in, 1, UIType.values().length, "Invalid UI type. Please retry: ") - 1;
        uiType = UIType.values()[uiTypeInt];

        System.out.println("Connecting to " + ip + ":" + port + " using " + connectionType + "...");

        try {
            switch (connectionType) {
                case RMI -> startRMI();
                case SOCKET -> startSocket();
            }
        } catch (RemoteException e) {
            System.err.println("Cannot connect to server due to a remote exception. Exiting...");
            System.exit(1);
        } catch (NotBoundException e) {
            System.err.println("Cannot connect to server because the object is not bound. Exiting...");
            System.exit(1);
        }
    }

    private static void askArgs(Scanner in) {
        System.out.println("Select connection type:");
        for (ConnectionType value : ConnectionType.values()) {
            System.out.println((value.ordinal() + 1) + ". " + value);
        }

        int clientTypeInt = TextualUtils.nextInt(in, 1, ConnectionType.values().length, "Invalid client type. Please retry: ") - 1;
        connectionType = ConnectionType.values()[clientTypeInt];

        // Consumes the \n left by nextInt
        in.nextLine();

        System.out.print("Enter server IP (blank for localhost): ");
        ip = in.nextLine();
        if (ip.isBlank()){
            ip = "localhost";
        }

        System.out.print("Enter server port (blank for default): ");
        String portString = in.nextLine();
        if (portString.isBlank()){
            switch (connectionType) {
                case RMI -> port = DefaultValues.Default_port_RMI;
                case SOCKET -> port = DefaultValues.Default_port_SOCKET;
            }
        } else {
            port = Integer.parseInt(portString);
        }
    }

    /**
     * Starts an RMI client.
     */
    private static void startRMI() throws RemoteException, NotBoundException {
            Registry registry = LocateRegistry.getRegistry(ip, port);
            AppServerInterface appServer = (AppServerInterface) registry.lookup(DefaultValues.defaultRMIName);
            ClientImpl client = new ClientImpl(appServer.connect(), uiType);
            client.run();
    }

    public static String getIP(){
        return ip;
    }

    public static int getPort(){
        return port;
    }

    public static ConnectionType getConnectionType(){
        return connectionType;
    }

    // Starts the socket client:

    private  static void startSocket() throws RemoteException {
        ServerProxy serverProxy = new ServerProxy(ip, port);
        ClientImpl client = new ClientImpl(serverProxy, uiType);
        new Thread(() -> {
            while(true) {
                try {
                    serverProxy.ReceiveFromClient(client);
                } catch (RemoteException e) {
                    System.err.println("Cannot receive from server. Stopping...");
                    try {
                        serverProxy.close();
                    } catch (RemoteException ex) {
                        System.err.println("Cannot close connection with server. Halting...");
                    }
                    System.exit(1);
                }
            }
        }).start();
        client.run();
    }
}