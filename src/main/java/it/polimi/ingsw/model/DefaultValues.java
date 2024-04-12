package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * This class contains the default values for the game
 */
public class DefaultValues implements Serializable {
    public final static int MaxNumOfPlayer = 4;
    public final static int MinNumOfPlayer = 2;
    public final static int secondsToWaitReconnection = 30;
    public final static Long timeout_for_detecting_disconnection = 4000L;
    public final static int maxnum_of_last_event_tobe_showed = 6;

    public final static int timeoutConnection_millis =3000;
    public final static int secondToWaitToSend_heartbeat =500;
    public final static String Default_servername_RMI = "CodexNaturalis";
    public static String serverIp = "127.0.0.1";
    public final static String Remote_ip = "127.0.0.1";
    public final static int row_chat = 16;
    public final static int col_chat = 96;

    public final static int num_of_attempt_to_connect_toServer_before_giveup = 5;
    public final static int seconds_between_reconnection = 5;
    public final static int max_messagesShown = 5;


}
