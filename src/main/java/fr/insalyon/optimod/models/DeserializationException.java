package fr.insalyon.optimod.models;

/**
 * Throwed when illegal XML arises
 */
public class DeserializationException extends Exception {
    public DeserializationException(String message) {
        super(message);
    }
}
