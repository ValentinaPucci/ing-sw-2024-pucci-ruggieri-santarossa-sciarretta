package it.polimi.demo.main.utils;

import it.polimi.demo.network.utils.StaticPrinter;

import java.util.Scanner;

/**
 * This class is used to validate the input from the user.
 */
public class StaticPromptValidator {

    /**
     * This method is used to prompt the user for an IP address.
     * @param message The message to be displayed to the user.
     * @return A {@link BoolAdd} object containing the result of the validation.
     */
    public static BoolAdd promptForIP(String message) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            StaticPrinter.staticPrinter(message);
            String input = scanner.nextLine();
            if (input.isEmpty())
                return new BoolAdd(false, input);
            if (isValidIP(input))
                return new BoolAdd(true, input);
            StaticPrinter.staticPrinter("Invalid IP. Try again.");
        }
    }

    /**
     * This method is used to prompt the user for a selection.
     * @param s The message to be displayed to the user.
     * @return The selection made by the user.
     */
    public static int promptForSelection(String s) {
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

    /**
     * This method is used to validate an IP address.
     * @param input The IP address to be validated.
     * @return True if the IP address is valid, false otherwise.
     */
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
