package it.polimi.demo;

import java.io.Serializable;

/**
 * A class containing all the default values we need in the project<br>
 */
public class DefaultValues implements Serializable {

    public final static boolean DEBUG = false;

    public final static int[] NE_StarterCard_index = {500,501};
    public final static int[] NW_StarterCard_index = {500,500};
    public final static int[] SE_StarterCard_index = {501,501};
    public final static int[] SW_StarterCard_index = {501,500};


    public final static int longest_commonCardMessage = 81;
    public final static int time_publisher_showing_seconds = 1;

    public final static int MaxNumOfPlayer = 4;
    public final static int minNumOfPlayer = 2;

    public final static int NumOfCommonCards = 2;

    public final static String gameIdData = "GameId";
    public final static String gameIdTime = "Created";
    public final static int twelveHS = 43200;
    public final static int display_finalPoint = 13;

    public final static int Default_port_RMI = 4321;
    public final static int Default_port_Socket = 4320;

    public final static String Default_servername_RMI = "CodexNaturalis";
    public static String serverIp = "127.0.0.1";
    public final static String Remote_ip = "127.0.0.1";
    public final static int secondsToWaitReconnection = 30;
    public final static int timeoutConnection_millis = 3000;
    public final static int secondToWaitToSend_heartbeat = 500;
    public final static Long timeout_for_detecting_disconnection = 30000L;

    public final static int maxnum_of_last_event_tobe_showed = 6;


    //CONSOLE OUTPUT INDEXES

    //INPUT
    public final static int row_input = 34;

    //IMPORTANT EVENTS
    public final static int row_important_events = 2;
    public final static int col_important_events = 86;

    //PLAYGROUND
    public final static int row_playground = 13;
    public final static int col_playground = 0;

    //SHELVES
    public final static int row_playerName = 24;
    public final static int row_shelves = 25;
    public final static int col_shelves = 3;

    public final static int chair_index = 21;

    //COMMON CARDS
    public final static int row_commonCards = 9;
    public final static int col_commonCards = 50;


    //POINTS
    public final static int row_points = 16;
    public final static int col_points = 78;

    //GOAL CARDS
    public final static int row_goalCards = 16;
    public final static int col_goalCards = 50;

    //CHAT
    public final static int row_chat = 16;
    public final static int col_chat = 96;

    //LEADERBOARD
    public final static int row_leaderboard = 25;
    public final static int col_leaderboard = 15;

    //GAME INFO
    public final static int row_gameID = 9;
    public final static int row_nextTurn = 10;


    public final static int num_points_for_second_last_round = 20;
    public final static int num_of_attempt_to_connect_toServer_before_giveup = 5;
    public final static int seconds_between_reconnection = 5;

    public final static int max_messagesShown = 5;

}
