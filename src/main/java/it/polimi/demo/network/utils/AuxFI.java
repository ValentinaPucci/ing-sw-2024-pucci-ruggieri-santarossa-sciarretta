package it.polimi.demo.network.utils;

import java.rmi.RemoteException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * auxiliary functional interfaces for lightweight exception handling
 */
public class AuxFI {

    /**
     * Handles an event with a single parameter
     * @param consumer the event to handle
     * @param param the parameter of the event
     * @param <T> the type of the parameter
     */
    public static <T> void handleEvent(Consumer<T> consumer, T param) {
        try {
            consumer.accept(param);
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Handles an event with two parameters
     * @param biConsumer the event to handle
     * @param param1 the first parameter of the event
     * @param param2 the second parameter of the event
     * @param <T> the type of the first parameter
     * @param <U> the type of the second parameter
     */
    public static <T, U> void handleEvent(BiConsumer<T, U> biConsumer, T param1, U param2) {
        try {
            biConsumer.accept(param1, param2);
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Handles an event with three parameters
     * @param triConsumer the event to handle
     * @param param1 the first parameter of the event
     * @param param2 the second parameter of the event
     * @param param3 the third parameter of the event
     * @param <T> the type of the first parameter
     * @param <U> the type of the second parameter
     * @param <V> the type of the third parameter
     */
    public static <T, U, V> void handleEvent(TriConsumer<T, U, V> triConsumer, T param1, U param2, V param3) {
        try {
            triConsumer.accept(param1, param2, param3);
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Handles an event with four parameters
     * @param quadConsumer the event to handle
     * @param param1 the first parameter of the event
     * @param param2 the second parameter of the event
     * @param param3 the third parameter of the event
     * @param param4 the fourth parameter of the event
     * @param <T> the type of the first parameter
     * @param <U> the type of the second parameter
     * @param <V> the type of the third parameter
     * @param <W> the type of the fourth parameter
     */
    public static <T, U, V, W> void handleEvent(QuadConsumer<T, U, V, W> quadConsumer, T param1, U param2, V param3, W param4) {
        try {
            quadConsumer.accept(param1, param2, param3, param4);
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Handles an exception
     * @param e the exception to handle
     */
    private static void handleException(Exception e) {
        if (e instanceof RemoteException) {
            throw new RuntimeException("RemoteException occurred", e);
        } else {
            throw new RuntimeException("Exception occurred", e);
        }
    }

    /**
     * Functional interface for a consumer with three parameters
     * @param <T> the type of the first parameter
     * @param <U> the type of the second parameter
     * @param <V> the type of the third parameter
     */
    @FunctionalInterface
    public interface TriConsumer<T, U, V> {
        void accept(T t, U u, V v) throws Exception;
    }

    /**
     * Functional interface for a consumer with four parameters
     * @param <T> the type of the first parameter
     * @param <U> the type of the second parameter
     * @param <V> the type of the third parameter
     * @param <W> the type of the fourth parameter
     */
    @FunctionalInterface
    public interface QuadConsumer<T, U, V, W> {
        void accept(T t, U u, V v, W w) throws Exception;
    }
}
