package main.java.AccountExceptions;

/**
 * Exception thrown if incorrect password is given.
 * */
public class BadPasswordException extends Exception {
    public BadPasswordException() {
        super("Incorrect password");
    }

    public BadPasswordException(String message) {
        super(message);
    }
}
