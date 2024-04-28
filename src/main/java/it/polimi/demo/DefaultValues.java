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
    public final static String RMI_ServerName = "CodexNaturalis";

    public static final int defaultRMIRegistryPort = java.rmi.registry.Registry.REGISTRY_PORT;
    public final static int Default_port_RMI = 4321;
    public final static int Default_port_SOCKET = 4320;
    public static String Server_ip = "127.0.0.1";
    public final static String Remote_ip = "127.0.0.1";
    public final static int row_chat = 16;
    public final static int col_chat = 96;

    public final static int max_messagesShown = 5;
    public static int max_num_connection_attempts = 5;
}