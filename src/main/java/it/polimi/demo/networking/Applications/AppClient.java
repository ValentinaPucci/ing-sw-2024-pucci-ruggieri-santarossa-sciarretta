package it.polimi.demo.networking.Applications;

import it.polimi.demo.model.DefaultValues;
import it.polimi.demo.networking.ConnectionType;
import it.polimi.demo.networking.rmi.RmiClient;
import it.polimi.demo.networking.rmi.RmiServer;
import it.polimi.demo.view.UIType;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static it.polimi.demo.networking.ConnectionType.RMI;
import static it.polimi.demo.networking.PrintAsync.printAsync;

public class AppClient {
    private static ConnectionType connectionType;
    private static UIType uiType;
    private static String ip;
    private static int port;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //set connectionType
        askConnectionType(scanner);
        //set ip e port
        askDetails(scanner);
        //seu UIType
        askUIType(scanner);

        printAsync("Connecting to " + ip + ":" + port + " using " + connectionType + "...");

        switch (uiType) {
            case GUI:
                //Application.launch(GUIApplication.class, connectionType.toString());
                printAsync("GUI + " + connectionType + " CHOSEN");
                break;
            case TUI:
                printAsync("TUI + " + connectionType + " CHOSEN");
                //new GameFlow(connectionType);
                break;
        }

//        try {
//            switch (uiType) {
//                case GUI:
//                    //Application.launch(GUIApplication.class, connectionType.toString());
//                    printAsync("GUI + " + connectionType + "CHOSEN");
//                    break;
//                case TUI:
//                    printAsync("TUI + " + connectionType + "CHOSEN");
//                    //new GameFlow(connectionType);
//                    break;
//            }
//        } catch (RemoteException | NotBoundException e) {
//            System.err.println("Cannot connect to server. Exiting...");
//            System.exit(1);
//        }
    }


    private static void askConnectionType(Scanner in) {
        printAsync("Select connection type:");
        for (ConnectionType value : ConnectionType.values()) {
            printAsync((value.ordinal() + 1) + ". " + value);
        }

        int clientTypeInt = getNumericInput(in, "Invalid connection type. Please retry: ");
        connectionType = ConnectionType.values()[clientTypeInt - 1];
    }

    private static void askDetails(Scanner in) {
        System.out.print("Enter server IP (blank for localhost): ");
        if (connectionType == RMI)
            RmiClient.setIp(getInputWithMessage(in, "Invalid IP address. Please retry: "));
        if (ip.isBlank()) {
            ip = "localhost";
        }

        System.out.print("Enter server port (blank for default): ");
        String portString = in.nextLine();
        if (portString.isBlank()) {
            if (connectionType == RMI)
                port = DefaultValues.Default_port_RMI; // Set to default port
            else
                port = DefaultValues.Default_port_SOCKET; // Set to default port
        } else {
            port = Integer.parseInt(portString);
        }
    }


    private static void askUIType(Scanner in) {
        printAsync("Select UI type:");
        for (UIType value : UIType.values()) {
            printAsync((value.ordinal() + 1) + ". " + value);
        }

        int uiTypeInt = getNumericInput(in, "Invalid UI type. Please retry: ") - 1;
        uiType = UIType.values()[uiTypeInt];
    }


    private static int getNumericInput(Scanner scanner, String errorMessage) {
        int selection = -1;
        String input;
        do {
            input = scanner.nextLine();
            try {
                selection = Integer.parseInt(input);
                if (selection < 1 || selection > 2) {
                    printAsync(errorMessage);
                }
            } catch (NumberFormatException e) {
                printAsync("Invalid input");
            }
        } while (selection < 1 || selection > 2);
        return selection;
    }

    private static String getInputWithMessage(Scanner scanner, String message) {
        String input;
        do {
            input = scanner.nextLine();
            if (!input.isEmpty() && !isValidIPAddress(input)) {
                printAsync(message);
            }
        } while (!input.isEmpty() && !isValidIPAddress(input));
        return input;
    }

    private static boolean isValidIPAddress(String input) {
        List<String> parts = Arrays.asList(input.split("\\."));
        if (parts.size() != 4) {
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
