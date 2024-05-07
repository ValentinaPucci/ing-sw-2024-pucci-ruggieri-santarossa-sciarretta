package it.polimi.demo;

/**
 * This class contains the default values for the game
 */
public class DefaultValues {

    public final static int[] NE_StarterCard_index = {500,501};
    public final static int[] NW_StarterCard_index = {500,500};
    public final static int[] SE_StarterCard_index = {501,501};
    public final static int[] SW_StarterCard_index = {501,501};

    public final static int MaxNumOfPlayer = 4;
    public final static int MinNumOfPlayer = 2;
    public final static int secondsToWaitReconnection = 30;
    public final static Long timeout = 4000L;
    public final static int max_num_of_last_event_tobe_showed = 6;

    public final static int num_points_for_second_last_round = 20;

    public final static int timeoutConnection_millis = 3000;
    public final static int secondToWaitToSend_heartbeat = 500;
    public final static String defaultRMIName = "CodexNaturalis";
    public static final int defaultSocketPort = 12345;
    public static final int defaultRMIRegistryPort = java.rmi.registry.Registry.REGISTRY_PORT;
    public final static int Default_port_RMI = 4321;
    public final static int Default_port_SOCKET = 4320;
    public final static String Server_ip = "127.0.0.1";
    public final static String Remote_ip = "127.0.0.1";
    public final static int row_chat = 16;
    public final static int col_chat = 96;

    public final static int max_messagesShown = 5;
    public static int max_num_connection_attempts = 5;

    /**
     * Timeout (in milliseconds) used in the ping-pong mechanism.
     */
    public static final long pingpongTimeout = 3000;
    /**
     * Timeout (in milliseconds) used by the client to determine if the connection to the server is lost.
     * This value should be greater than the ping-pong timeout.
     */
    public static final long connectionLostTimeout = pingpongTimeout * 3;

    /**
     * Time (in milliseconds) after which the only player left in the game wins.
     */
    public static final long walkoverTimeout = 600 * 1000;
}