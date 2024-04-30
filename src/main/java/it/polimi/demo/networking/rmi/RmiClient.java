package it.polimi.demo.networking.rmi;

import it.polimi.demo.DefaultValues;
import it.polimi.demo.networking.ControllerInterfaces.MainControllerInterface;
import it.polimi.demo.networking.ConcreteClient;
import it.polimi.demo.networking.GenericServer;
import it.polimi.demo.view.TUI;
import it.polimi.demo.view.TUIUtils;
import it.polimi.demo.view.UIType;

import java.io.Serializable;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import static it.polimi.demo.networking.PrintAsync.*;
import static org.fusesource.jansi.Ansi.ansi;

// Todo: re-implement some code (originality)

/**
 * RMI client to communicate with the remote server using RMI.
 * Implements the MainControllerInterface to receive messages from the server.
 */
public class RmiClient extends ConcreteClient implements Serializable {

    /**
     * Registry of the RMI
     */
    private Registry registry;

    /**
     * Create, start and connect an RMI Client to the server
     */
    public RmiClient(UIType uiType) throws RemoteException {
        super(uiType);
        connectToRMIServer();
        // todo: heartbeats initializer
        // done: take care of connectToRegistry() when calling startRMI() in AppClient!
    }

    /**
     * Connects to the RMI server.
     */
    public void connectToRMIServer() {
        int attempt = 0;

        while (attempt < DefaultValues.max_num_connection_attempts) {
            try {
                // Get the registry for the RMI server
                Registry registry = LocateRegistry.getRegistry(DefaultValues.Server_ip, DefaultValues.Default_port_RMI);

                // Look up the MainControllerInterface from the registry
                MainControllerInterface controllerInterface = (MainControllerInterface) registry.lookup(DefaultValues.RMI_ServerName);

                // Export the GameListener object
                //events_from_model = (GameListener) UnicastRemoteObject.exportObject(gameListenersHandler, 0);

                // Print a success message
                printAsync("Connected to RMI server.");

                // Exit the loop as connection succeeded
                break;
            } catch (RemoteException | NotBoundException e) {
                // Print error message for the first attempt only
                if (attempt == 0) {
                    printAsync("[ERROR] Unable to connect to RMI server:");
                    e.printStackTrace();
                }

                // Print reconnection attempt message
                printAsyncNoLine("[Attempt #" + (attempt + 1) + "] Waiting to reconnect to RMI server...");

                // Wait for a certain duration before attempting reconnection
                waitForReconnection();

                // Increment the attempt counter
                attempt++;
            }
        }

        // If all attempts failed, exit the program
        if (attempt == DefaultValues.max_num_connection_attempts) {
            printAsyncNoLine("Max connection attempts reached. Exiting...");
            System.exit(-1);
        }
    }

    /**
     * Waits for a certain duration before attempting reconnection.
     */
    public void waitForReconnection() {
        try {
            // Wait for a certain duration before attempting reconnection
            Thread.sleep(DefaultValues.secondsToWaitReconnection * 1000);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Connects to the RMI registry.
     *
     * @throws RemoteException if a remote exception occurs
     * @throws NotBoundException if the requested object is not bound in the registry
     */
    public void connectToRegistry() throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(DefaultValues.Server_ip, DefaultValues.Default_port_RMI);
        setClientRequests((MainControllerInterface) registry.lookup(DefaultValues.RMI_ServerName));
    }

    /**
     * Starts the TUI.
     * @param Tui the TUI to start
     */
    public static void startTUI(TUI Tui) {

        Scanner s = new Scanner(System.in);
        int choice = TUIUtils.nextInt(s);

        switch (choice) {
            case 1 -> Tui.createGame();
            case 2 -> Tui.joinGame();
            default -> System.out.println("Invalid choice.");
        }
    }
}
