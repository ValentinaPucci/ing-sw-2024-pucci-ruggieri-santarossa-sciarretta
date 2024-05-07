package it.polimi.demo.networking.Applications;
import java.util.logging.Level;
import java.util.logging.Logger;
import it.polimi.demo.DefaultValues;
import it.polimi.demo.networking.Server;
import it.polimi.demo.networking.ServerExecutor;
import it.polimi.demo.networking.ServerImpl;
import it.polimi.demo.networking.Socket.ClientProxy;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.net.ServerSocket;
import java.net.Socket;

public class AppServer extends UnicastRemoteObject implements AppServerInterface {

    private static final Logger logger = Logger.getLogger(AppServer.class.getName());
    private static int socketPort = DefaultValues.defaultSocketPort;
    private static String serverIP = null;
    private static AppServer instance;
    private final ExecutorService executorService = Executors.newCachedThreadPool(); // x socket

    protected AppServer() throws RemoteException {
    }

    /**
     * AppServerImpl singleton instance getter.
     *
     * @return the AppServerImpl instance
     */
    public static AppServer getInstance() throws RemoteException {
        if (instance == null) {
            instance = new AppServer();
        }
        return instance;
    }

    /**
     * Starts the server application.
     *
     * @param args [server ip] [socket port]
     */
    public static void main(String[] args) {

        logger.info("Starting server...");
        System.out.println("Starting server...");
        switch (args.length){
            case 2:
                try {
                    socketPort = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid socket port. Using default port " + DefaultValues.defaultSocketPort + "...");
                }
            case 1:
                serverIP = args[0];
        }

        Thread rmiThread = new Thread(() -> {
            try {
                startRMI();
            } catch (RemoteException e) {
                logger.log(Level.SEVERE, "No connection protocol available. Exiting...", e);
                System.err.println("Cannot start RMI protocol.");
                e.printStackTrace();
            }
        });
        rmiThread.start();

        Thread socketThread = new Thread(() -> {
            try {
                startSocket();
            } catch (RemoteException e) {
                System.err.println("Cannot start socket protocol.");
                e.printStackTrace();
            }
        });
        socketThread.start();

        try {
            rmiThread.join();
            //socketThread.join();
        } catch (InterruptedException e) {
            System.err.println("No connection protocol available. Exiting...");
        }
    }

    /**
     * This method is used to start the RMI server.
     */
    private static void startRMI() throws RemoteException {

        logger.info("RMI > Starting RMI server...");
        System.out.println("RMI > Starting RMI server...");

        if (serverIP != null) {
            System.out.println("RMI > Binding RMI server to IP " + serverIP + "...");
            System.setProperty("java.rmi.server.hostname", serverIP);
        }

        Registry registry;

        try {
            System.out.println("RMI > Creating a new RMI registry on port " + DefaultValues.defaultRMIRegistryPort + "...");
            registry = LocateRegistry.createRegistry(DefaultValues.defaultRMIRegistryPort);
        } catch (RemoteException e) {
            logger.log(Level.SEVERE, "RMI > Cannot create RMI registry.", e);
            logger.info("RMI > Trying to get already existing RMI registry...");
            System.err.println("RMI > Cannot create RMI registry.");
            System.out.println("RMI > Trying to get already existing RMI registry...");
            registry = LocateRegistry.getRegistry();
        }

        // Here we take advantage of singleton design pattern;
        // AppServer is a local variable to this method, it is fundamental
        // for the binding to the RMI registry.
        AppServer server = getInstance();
        registry.rebind(DefaultValues.defaultRMIName, server);
    }
    private static void startSocket() throws RemoteException {
        System.out.println("SOCKET > Starting socket server on port " + socketPort + "...");

        AppServer instance = getInstance();
        try (ServerSocket serverSocket = new ServerSocket(socketPort)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("SOCKET > Accepting connection from " + socket.getInetAddress() + "...");
                instance.executorService.submit(() -> {
                    try {
                        ClientProxy client_proxy = new ClientProxy(socket);
                        Server server = new ServerImpl();
                        server.register(client_proxy);
                        while (true) {
                            client_proxy.receiveFromServer(server);
                        }
                    } catch (RemoteException e) {
                        System.err.println("SOCKET > Client " + socket.getInetAddress() + " seems to have closed the connection. Closing...");
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.err.println("SOCKET > Unexpected error. Closing connection with" + socket.getInetAddress() + "...");
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            System.err.println("SOCKET > Cannot close socket");
                        }
                    }
                });
            }
        } catch (IOException e) {
            throw new RemoteException("SOCKET > Cannot start socket server", e);
        }
    }
    // TODO: A noi non serve? metto per togliere errrore..
    @Override
    public Server connect() throws RemoteException {
        return null;
    }

}
