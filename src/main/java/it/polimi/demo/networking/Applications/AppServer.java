package it.polimi.demo.networking.Applications;

import it.polimi.demo.DefaultValues;
import it.polimi.demo.networking.rmi.RmiServer;

import java.rmi.RemoteException;
import java.util.Scanner;

import static it.polimi.demo.networking.PrintAsync.printAsync;

public class AppServer {

    /**
     * Main entry point for the RMI client application.
     */
    public static void main(String[] args) throws RemoteException {

        Scanner scanner = new Scanner(System.in);
        String ip = askForRemoteIp(scanner);

        if (ip.isEmpty()) {
            System.setProperty("java.rmi.server.hostname", DefaultValues.Remote_ip);
        } else {
            DefaultValues.Server_ip = ip;
            System.setProperty("java.rmi.server.hostname", ip);
        }

        /**
         * Start the RMI and Socket servers in separate threads.
         * We do this in order to maintain generality before
         * deciding which server implementation to use
         * (RMI or Socket).
         * todo: originality.
         */

        Thread rmi_thread = new Thread(() -> {
            try {
                RmiServer.startServer();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
        rmi_thread.start();

        Thread socket_thread = new Thread(() -> {
            // todo: SocketServer.startServer();
        });
        socket_thread.start();

        try {
            rmi_thread.join();
            socket_thread.join();
        } catch (InterruptedException e) {
            System.err.println("Error while waiting for threads to finish.");
        }
    }

    /**
     * Prompts the user to enter the remote IP address.
     *
     * @param scanner The scanner object for user input
     * @return The entered remote IP address
     */
    private static String askForRemoteIp(Scanner scanner) {
        String input;
        do {
            printAsync("Insert remote IP (leave empty for localhost): ");
            input = scanner.nextLine();
        } while (!input.isEmpty() && !isValidIP(input));
        return input;
    }

    /**
     * Checks if the given input is a valid IP address.
     *
     * @param input The input to check
     * @return True if the input is a valid IP address, false otherwise
     *
     */
    private static boolean isValidIP(String input) {
        String[] parts = input.split("\\.");
        if (parts.length != 4) {
            return false;
        }
        for (String part : parts) {
            try {
                int value = Integer.parseInt(part);
                if (value < 0 || value > 255) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }
}
