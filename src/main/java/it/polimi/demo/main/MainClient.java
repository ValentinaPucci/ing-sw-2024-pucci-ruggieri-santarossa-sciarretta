package it.polimi.demo.main;

import it.polimi.demo.DefaultValues;
import it.polimi.demo.view.flow.ConnectionSelection;
import it.polimi.demo.view.flow.GameDynamics;
import it.polimi.demo.view.gui.ApplicationGUI;
import javafx.application.Application;
import org.fusesource.jansi.Ansi;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

import static it.polimi.demo.networking.PrintAsync.printAsync;

// todo: checked class
public class MainClient {

    public static void main(String[] args) {
        boolean debug = DefaultValues.DEBUG;

        if (!debug) {
            getValidInput("Insert remote IP (leave empty for localhost)", MainClient::isValidIP);
            getValidInput("Insert your IP (leave empty for localhost)", MainClient::isValidIP);
        }

        int selection = debug ? 2 : getValidSelection(
                "Select option:\n\t(1) TUI + Socket\n\t(2) TUI + RMI\n\t\n\t(3) GUI + Socket\n\t(4) GUI + RMI",
                MainClient::isValidSelection);

        ConnectionSelection conSel = (selection == 1 || selection == 3) ? ConnectionSelection.SOCKET : ConnectionSelection.RMI;
        printAsync("Starting the game!");

        if (selection == 1 || selection == 2) {
            new GameDynamics(conSel); // Start game with TUI
        } else {
            Application.launch(ApplicationGUI.class, conSel.toString()); // Start game with GUI
        }
    }

    private static String getValidInput(String message, Predicate<String> validator) {
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            printAsync(Ansi.ansi().cursor(1, 0).a(message));
            input = scanner.nextLine();
            if (!input.isEmpty() && !validator.test(input)) {
                printAsync("Not valid");
            }
        } while (!input.isEmpty() && !validator.test(input));
        return input;
    }

    private static int getValidSelection(String message, Predicate<Integer> validator) {
        Scanner scanner = new Scanner(System.in);
        int selection;
        do {
            printAsync(Ansi.ansi().cursor(1, 0).a(message));
            selection = Arrays.stream(scanner.nextLine().split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .findFirst()
                    .orElse(-1);
            if (!validator.test(selection)) {
                printAsync("Not valid");
            }
        } while (!validator.test(selection));
        return selection;
    }

    private static boolean isValidIP(String input) {
        List<String> parsed = Arrays.asList(input.split("\\."));
        return parsed.size() == 4 && parsed.stream().allMatch(part -> {
            try {
                int value = Integer.parseInt(part);
                return value >= 0 && value <= 255;
            } catch (NumberFormatException e) {
                return false;
            }
        });
    }

    private static boolean isValidSelection(int selection) {
        return selection >= 1 && selection <= 4;
    }
}














