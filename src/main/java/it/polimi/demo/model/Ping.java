package it.polimi.demo.model;

import java.io.Serializable;

/**
 * Ping's class<br>
 * This class implements the method we used to ping the server<br>
 * to let him know that we are still connected (needed for rmi connection)<br>
 */
public class Ping implements Serializable {

    private final Long ping;
    private final String nick;

    /**
     * Constructor
     *
     * @param ping
     * @param nick
     */
    public Ping(Long ping, String nick) {
        this.ping = ping;
        this.nick = nick;
    }

    /**
     * @return the heartbeat
     */
    public Long getPing() {
        return ping;
    }

    /**
     * @return the nickname of the player pinging the server
     */
    public String getNick() {
        return nick;
    }

}
