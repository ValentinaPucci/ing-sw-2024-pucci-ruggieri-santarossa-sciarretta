package it.polimi.demo;

import java.io.Serializable;

/**
 * Constants class contains all the constants used in the game
 */
public class Constants implements Serializable {

    public final static int MaxNumOfPlayer = 4;
    public final static int minNumOfPlayer = 2;

    public final static int RMI_port = 4333;
    public final static int Socket_port = 4335;

    public final static String RMI_server_name = "CodexNaturalis";
    public static String serverIp = "127.0.0.1";
    public final static String Remote_ip = "127.0.0.1";
    public final static int secondsToWaitReconnection = 10000;

    //INPUT
    public final static int row_input = 36;
    public final static int row_gameID = 15;

    //GAME INFO
    public final static int num_points_for_second_last_round = 20;

}
