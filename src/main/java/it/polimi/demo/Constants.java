package it.polimi.demo;

import java.io.Serializable;

public class Constants implements Serializable {

    public final static int MaxNumOfPlayer = 4;
    public final static int minNumOfPlayer = 2;

    public final static int RMI_port = 4333;
    public final static int Socket_port = 4334;

    public final static String RMI_server_name = "CodexNaturalis";
    public static String serverIp = "127.0.0.1";
    public final static String Remote_ip = "127.0.0.1";
    public final static int secondsToWaitReconnection = 5000;

    public final static int maxnum_of_last_event_tobe_showed = 6;

    //INPUT
    public final static int row_input = 34;

    //IMPORTANT EVENTS
    public final static int row_important_events = 2;
    public final static int col_important_events = 86;

    //CHAT
    public final static int row_chat = 16;
    public final static int col_chat = 96;

    //LEADERBOARD
    public final static int row_leaderboard = 25;
    public final static int col_leaderboard = 15;

    //GAME INFO
    public final static int row_gameID = 9;
    public final static int row_nextTurn = 10;

    public final static int num_points_for_second_last_round = 3;
    public final static int num_of_attempt_to_connect_toServer_before_giveup = 5;
    public final static int seconds_between_reconnection = 5;
}
