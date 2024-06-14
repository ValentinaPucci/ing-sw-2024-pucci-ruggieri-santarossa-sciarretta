package it.polimi.demo.model;

import java.io.Serializable;

/**
 * Ping record is used to store a ping and a nickname.
 * @param ping the ping
 * @param nickname the nickname
 */
public record Ping(Long ping, String nickname) implements Serializable {}

