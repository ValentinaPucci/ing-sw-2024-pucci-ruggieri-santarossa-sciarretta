package it.polimi.demo.model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Class representing a ping message
 */
public class Ping implements Serializable {

    @Serial
    private static final long serialVersionUID = 3476528596147043783L;
    private final Long ping;
    private final String nickname;

    /**
     * Constructor for the Ping class
     * @param ping the ping
     * @param nick the nickname of the player pinging the server
     */
    public Ping(Long ping, String nick) {
        this.ping = ping;
        this.nickname = nick;
    }

    /**
     * Getter for the ping
     * @return the ping
     */
    public Long getPing() {
        return ping;
    }

    /**
     * Getter for the nickname
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

}
