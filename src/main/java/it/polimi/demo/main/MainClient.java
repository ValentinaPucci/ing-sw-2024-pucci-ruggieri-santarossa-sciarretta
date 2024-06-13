package it.polimi.demo.main;

import it.polimi.demo.network.StaticPrinter;
import it.polimi.demo.view.dynamic.TypeConnection;
import it.polimi.demo.view.dynamic.GameDynamic;
import it.polimi.demo.view.gui.ApplicationGUI;
import javafx.application.Application;
import java.util.Scanner;

public class MainClient {

    public static void main(String[] args) {

        promptForIP("Insert remote IP (leave empty for localhost): ");
        promptForIP("Insert your IP (leave empty for localhost): ");

        int connection_selection = promptForSelection(
                "Select option:\n1) Socket \n2) RMI \n");

        TypeConnection connection = connection_selection == 1 ? TypeConnection.SOCKET : TypeConnection.RMI;

        int ui_selection = promptForSelection(
                "\nSelect option:\n1) TUI \n2) GUI \n");

        if (ui_selection == 1) {
            new GameDynamic(connection);
        }
        else {
            Application.launch(ApplicationGUI.class, connection.toString());
        }
    }

    private static void promptForIP(String message) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            StaticPrinter.staticPrinter(message);
            String input = scanner.nextLine();
            if (input.isEmpty() || isValidIP(input)) break;
            StaticPrinter.staticPrinter("Invalid IP. Try again.");
        }
    }

    private static int promptForSelection(String s) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            StaticPrinter.staticPrinter(s);
            try {
                int selection = Integer.parseInt(scanner.nextLine().trim());
                if (selection >= 1 && selection <= 2) return selection;
            } catch (NumberFormatException e) {
                StaticPrinter.staticPrinter("Invalid selection. Enter a number between 1 and 2.");
            }
        }
    }

    private static boolean isValidIP(String input) {
        String[] parts = input.split("\\.");
        if (parts.length != 4) return false;
        for (String part : parts) {
            try {
                int value = Integer.parseInt(part);
                if (value < 0 || value > 255) return false;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }
}

















