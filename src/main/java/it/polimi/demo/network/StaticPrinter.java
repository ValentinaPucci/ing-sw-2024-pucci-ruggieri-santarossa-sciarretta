package it.polimi.demo.network;

import org.fusesource.jansi.Ansi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is used to print messages asynchronously.
 */
public class StaticPrinter {

    private static final Logger LOGGER = Logger.getLogger(StaticPrinter.class.getName());
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
        }));
    }

    /**
     * Prints an Ansi message asynchronously.
     *
     * @param msg the Ansi message to print
     */
    public static void staticPrinter(Ansi msg) {
        executor.submit(() -> LOGGER.log(Level.INFO, msg.toString()));
    }

    /**
     * Prints a String message asynchronously.
     *
     * @param msg the String message to print
     */
    public static void staticPrinter(String msg) {
        executor.submit(() -> LOGGER.log(Level.INFO, msg));
    }

    /**
     * Prints a StringBuilder message asynchronously.
     *
     * @param msg the StringBuilder message to print
     */
    public static void staticPrinter(StringBuilder msg) {
        executor.submit(() -> LOGGER.log(Level.INFO, msg.toString()));
    }

    /**
     * Prints a String message without a newline asynchronously.
     *
     * @param msg the String message to print
     */
    public static void staticPrinterNoNewLine(String msg) {
        executor.submit(() -> LOGGER.log(Level.INFO, "{0}", msg));
    }

}
