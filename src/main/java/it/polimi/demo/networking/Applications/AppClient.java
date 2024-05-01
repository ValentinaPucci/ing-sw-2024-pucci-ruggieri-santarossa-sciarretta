package it.polimi.demo.networking.Applications;
import java.util.logging.Logger;
import it.polimi.demo.DefaultValues;
import it.polimi.demo.networking.ClientImpl;
import it.polimi.demo.networking.ConnectionType;
import it.polimi.demo.view.TextualUtils;
import it.polimi.demo.view.UIType;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class AppClient {
    private static final Logger logger = Logger.getLogger(AppServer.class.getName());
    private static UIType uiType;
    private static ConnectionType connectionType;
    private static String ip;
    private static int port;

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
                //case SOCKET -> startSocket();
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
            ip = DefaultValues.Server_ip;
        }

        System.out.print("Enter server port (blank for default): ");
        String portString = in.nextLine();
        if (portString.isBlank()){
            switch (connectionType) {
                case RMI -> port = DefaultValues.defaultRMIRegistryPort;
                case SOCKET -> port = DefaultValues.defaultSocketPort;
            }
        } else {
            port = Integer.parseInt(portString);
        }
    }

    /**
     * Starts an RMI client.
     */
    private static void startRMI() throws RemoteException, NotBoundException {
        try {
            Registry registry = LocateRegistry.getRegistry(ip, port);
            AppServerInterface appServer = (AppServerInterface) registry.lookup(DefaultValues.defaultRMIName);

            ClientImpl client = new ClientImpl(appServer.connect(), uiType);
            client.run();
        } catch (RemoteException e) {
            throw new RemoteException("Cannot connect to server due to a remote exception!!", e);
        } catch (NotBoundException e) {
            throw new NotBoundException();
        }
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
}