package it.polimi.demo.model;

/**
 * We use this class as a structure to have the ping connected to the nick of a player.
 * A ping is a 'signal' the client sends to the server regularly to check
 * if the connection is still alive. It is common practice to use it in multiplayer games.
 */
public class Heartbeat {
    private final Long ping;
    private final Player p;

    /**
     * Constructor
     *
     * @param ping
     * @param p
     */
    public Heartbeat(Long ping, Player p) {
        this.ping = ping;
        this.p = p;
    }

    /**
     * @return the heartbeat
     */
    public Long getBeat() {
        return ping;
    }

    /**
     * @return the nickname of the player pinging the server
     */
    public Player getPlayer() {
        return p;
    }
}
