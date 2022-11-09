package main.java.Authenticator;

public interface Authenticator {

    /**
     * Attempt to log user in, if successful, change status to authorized.
     * @param userName - user's name.
     * @param password - user's password.
     * @return true if login is successful, otherwise return false.
     * **/
    boolean login(String userName, String password);

    /**
     * Register user, setting a username and password.
     * **/
    boolean register(String userName, String password);

    /**
     * @return boolean indicating the authorizations status of the user.
     * **/
    boolean isAuthorized();
}
