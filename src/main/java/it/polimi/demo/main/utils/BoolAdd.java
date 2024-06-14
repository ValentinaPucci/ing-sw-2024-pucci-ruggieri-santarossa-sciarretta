package it.polimi.demo.main.utils;

/**
 * BoolAdd record is used to store a boolean and a string.
 * @param isNotEmpty if the address is not empty (not localhost)
 * @param add the address
 */
public record BoolAdd(boolean isNotEmpty, String add) {}
