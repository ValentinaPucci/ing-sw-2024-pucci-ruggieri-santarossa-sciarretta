package it.polimi.demo.main;

import javafx.application.Application;
import it.polimi.demo.DefaultValues;
import it.polimi.demo.view.flow.ConnectionSelection;
import it.polimi.demo.view.flow.GameFlow;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;
import static it.polimi.demo.networking.PrintAsync.printAsync;

public class MainClient {

    public static void main(String[] args) {

        clearCMD();
        int selection;

        // Disable javaFX logger
        // killLoggers();

        if (!DefaultValues.DEBUG) {
            String input;
            do {
                printAsync(ansi().cursor(1, 0).a("""
                        Insert remote IP (leave empty for localhost)
                        """));
                input = new Scanner(System.in).nextLine();
                if(!input.equals("") && !isValidIP(input)){
                    clearCMD();
                    printAsync("Not valid");
                }
            } while (!input.equals("") && !isValidIP(input));
            if (!input.equals(""))
                DefaultValues.serverIp = input;

            clearCMD();

            do {
                printAsync(ansi().cursor(1, 0).a("""
                        Insert your IP (leave empty for localhost)
                        """));
                input = new Scanner(System.in).nextLine();
                if (!input.equals("") && !isValidIP(input)) {
                    clearCMD();
                    printAsync("Not valid");
                }
            } while (!input.equals("") && !isValidIP(input));
            if (!input.equals(""))
                System.setProperty("java.rmi.server.hostname", input);


            clearCMD();
            do {
                printAsync(ansi().cursor(1, 0).a("""
                        Select option:
                        \t (1) TUI + Socket
                        \t (2) TUI + RMI
                        \t
                        \t (3) GUI + Socket
                        \t (4) GUI + RMI
                        """));
                input = new Scanner(System.in).nextLine();
                try {
                    selection = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    selection = -1;
                    printAsync("Nan");
                }
            } while (selection != 1 && selection != 2 && selection != 3 && selection != 4);
        } else {
            selection = 2; //Default run configuration
        }


        //Get the Communication Protocol wanted
        ConnectionSelection con_sel;
        if (selection == 1 || selection == 3) {
            con_sel = ConnectionSelection.SOCKET;
        } else {
            con_sel = ConnectionSelection.RMI;
        }

        printAsync("Starting the game!");

        // Starts the UI wanted
        if (selection == 1 || selection == 2) {
            // Starts the game with TUI
            // I can start directly here the GameFlow
            new GameFlow(con_sel);
        } else {
            // Starts the game with GUI
            // For doing so, I need to start the Main of GUI (GameFlow needs to be started inside the thread of GUI)
            //Application.launch(GUIApplication.class, con_sel.toString());
        }

    }

    private static void clearCMD() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            printAsync("\033\143");   //for Mac
        }
    }

    private static boolean isValidIP(String input) {
        List<String> parsed;
        parsed = Arrays.stream(input.split("\\.")).toList();
        if (parsed.size() != 4) {
            return false;
        }
        for (String part : parsed) {
            try {
                Integer.parseInt(part);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

//    private static void killLoggers(){
//        com.sun.javafx.util.Logging.getJavaFXLogger().disableLogging();
//        com.sun.javafx.util.Logging.getCSSLogger().disableLogging();
//        com.sun.javafx.util.Logging.getAccessibilityLogger().disableLogging();
//        com.sun.javafx.util.Logging.getFocusLogger().disableLogging();
//        com.sun.javafx.util.Logging.getInputLogger().disableLogging();
//        com.sun.javafx.util.Logging.getLayoutLogger().disableLogging();
//    }

}
