package it.polimi.demo.networking.Applications;

import it.polimi.demo.DefaultValues;
import it.polimi.demo.networking.ConnectionType;
import it.polimi.demo.networking.ConcreteClient;
import it.polimi.demo.networking.GenericServer;
import it.polimi.demo.networking.rmi.RmiClient;
import it.polimi.demo.networking.rmi.RmiServer;
import it.polimi.demo.view.UIType;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static it.polimi.demo.networking.ConnectionType.RMI;
import static it.polimi.demo.networking.PrintAsync.printAsync;
import static org.fusesource.jansi.Ansi.ansi;

public class AppClient {

    private static ConnectionType connectionType;
    private static UIType uiType;
    private static String ip;
    private static int port;
    private static String nickname;

    public static void main(String[] args) throws RemoteException, NotBoundException {

        Scanner scanner = new Scanner(System.in);

        askConnectionType(scanner);
        askDetails(scanner);
        askUIType(scanner);
        AnsiConsole.systemInstall();
        askNickname();
        System.out.print(ansi().eraseScreen(Ansi.Erase.BACKWARD).cursor(1, 1).reset());
        showMenu();

        System.out.println(ansi().bold().fg(Ansi.Color.BLUE).a("Connecting to " + ip + ":" + port + " using " + connectionType + "...").reset());

        switch (connectionType) {
            case RMI:
                startRMI();
                break;
            case SOCKET:
                // todo: startSocket();
                break;
        }

//        switch (uiType) {
//            case GUI:
//                //Application.launch(GUIApplication.class, connectionType.toString());
//                printAsync("GUI + " + connectionType + " CHOSEN");
//                GUI Gui = new GUI();
//                break;
//            case TUI:
//                printAsync("TUI + " + connectionType + " CHOSEN");
//                TUI Tui = new TUI();
//                startTUI(Tui);
//                break;
//        }
    }

//    /**
//     * Starts an RMI client.
//     */
//    private static void startRMI() throws RemoteException, NotBoundException {
//        Registry registry = LocateRegistry.getRegistry(ip, port);
//        AppServer appServer = (AppServer) registry.lookup(Constants.defaultRMIName);
//
//        ClientImpl client = new ClientImpl(appServer.connect(), uiType);
//        client.run();
//    }

    /**
     * Starts an RMI client.
     */
    // todo: connect with the ui properly, i.e. implement run() method in TUI and GUI
    public static void startRMI() throws RemoteException, NotBoundException {

        ConcreteClient client = new RmiClient(uiType);
        ((RmiClient) client).connectToRegistry();
        client.run();
    }

    /**
     * Asks the user to insert his username.
     */
    private static void askNickname() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(ansi().bold().fg(Ansi.Color.GREEN).a("Insert your username: ").reset());
        nickname = scanner.next();
    }

    /**
     * Shows the start menu and asks the user to select an option.
     * If the user selects an invalid option, the menu is shown again.
     */
    private static void showMenu(){
        System.out.println(ansi().bold().fg(Ansi.Color.GREEN).a("Select an option:").reset());
        System.out.println(ansi().bold().fg(Ansi.Color.GREEN).a(" 1. Create a new game").reset());
        System.out.println(ansi().bold().fg(Ansi.Color.GREEN).a(" 2. Join an existing game").reset());
    }

    private static void askConnectionType(Scanner in) {
        System.out.println(ansi().bold().fg(Ansi.Color.GREEN).a("Select connection type:").reset());
        for (ConnectionType value : ConnectionType.values()) {
            printAsync((value.ordinal() + 1) + ". " + value);
        }

        int clientTypeInt = getNumericInput(in, "Invalid connection type. Please retry: ");
        connectionType = ConnectionType.values()[clientTypeInt - 1];
    }

    private static void askDetails(Scanner in) {
        System.out.println(ansi().bold().fg(Ansi.Color.GREEN).a("Enter server IP (blank for localhost): ").reset());
        if (connectionType == RMI)
            ip = (getInputWithMessage(in, "Invalid IP address. Please retry: "));
        if (ip.isBlank()) {
            ip = "localhost";
        }

        System.out.println(ansi().bold().fg(Ansi.Color.GREEN).a("Enter server port (blank for localhost): ").reset());
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
        System.out.println(ansi().bold().fg(Ansi.Color.GREEN).a("Select UI type: ").reset());
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
                    System.out.println(ansi().bold().fg(Ansi.Color.RED).a(errorMessage).reset());
                }
            } catch (NumberFormatException e) {
                System.out.println(ansi().bold().fg(Ansi.Color.RED).a("Invalid input").reset());
            }
        } while (selection < 1 || selection > 2);
        return selection;
    }

    private static String getInputWithMessage(Scanner scanner, String message) {
        String input;
        do {
            input = scanner.nextLine();
            if (!input.isEmpty() && !isValidIPAddress(input)) {
                System.out.println(ansi().bold().fg(Ansi.Color.RED).a(message).reset());
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
