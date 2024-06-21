package it.polimi.demo.view.text;

import org.fusesource.jansi.Ansi;
import it.polimi.demo.Constants;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * Utility class for printing messages to the console with optional ANSI formatting.
 * This class provides methods for printing messages both with and without newline characters.
 * All print operations are performed asynchronously.
 */
public class StaticPrinterTUI {

    /**
     * Prints an ANSI formatted message to the console.
     *
     * @param msg the ANSI formatted message to print
     */
    public static void print(Ansi msg) {
        runAsync(() -> {
            System.out.println(msg);
            resetCursor();
        });
    }

    /**
     * Prints a string message to the console.
     *
     * @param msg the string message to print
     */
    public static void print(String msg) {
        runAsync(() -> {
            System.out.println(msg);
            resetCursor();
        });
    }

    /**
     * Prints a StringBuilder message to the console.
     *
     * @param msg the StringBuilder message to print
     */
    public static void print(StringBuilder msg) {
        runAsync(() -> {
            System.out.println(msg);
            resetCursor();
        });
    }

    /**
     * Prints a string message to the console without adding a newline at the end.
     *
     * @param msg the string message to print
     */
    public static void printNoNewLine(String msg) {
        runAsync(() -> System.out.print(msg));
    }

    /**
     * Prints an ANSI formatted message to the console without adding a newline at the end.
     *
     * @param msg the ANSI formatted message to print
     */
    public static void printNoNewLine(Ansi msg) {
        runAsync(() -> System.out.print(msg));
    }

    /**
     * Runs the specified task asynchronously in a new thread.
     *
     * @param task the Runnable task to run asynchronously
     */
    private static void runAsync(Runnable task) {
        new Thread(task).start();
    }

    /**
     * Resets the cursor position in the console to the predefined row.
     */
    private static void resetCursor() {
        System.out.println(ansi().cursor(Constants.row_input, 0));
    }
}


