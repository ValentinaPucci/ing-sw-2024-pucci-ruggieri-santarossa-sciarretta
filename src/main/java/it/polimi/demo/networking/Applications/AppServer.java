package it.polimi.demo.networking.Applications;

import it.polimi.demo.model.DefaultValues;
import it.polimi.demo.networking.rmi.RmiServer;

import java.util.Scanner;

import static it.polimi.demo.networking.PrintAsync.printAsync;

public class AppServer {
    /**
     * Main entry point for the RMI client application.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String ip = askForRemoteIp(scanner);

        if (ip.isEmpty()) {
            System.setProperty("java.rmi.server.hostname", DefaultValues.Remote_ip);
        } else {
            DefaultValues.Server_ip = ip;
            System.setProperty("java.rmi.server.hostname", ip);
        }
        RmiServer.bind();
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
            printAsync("Insert remote IP (leave empty for localhost)");
            input = scanner.nextLine();
        } while (!input.isEmpty() && !isValidIP(input));
        return input;
    }

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
