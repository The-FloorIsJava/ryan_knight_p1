package Authenticator;

public abstract class Authenticator {

    private boolean authorized;

    /**
     * Attempt to log user in, if successful, change status to authorized.
     * @param userName - user's name.
     * @param password - user's password.
     * @return true if login is successful, otherwise return false.
     * **/
    public abstract boolean login(String userName, String password);

    /**
     * Register user, setting a username and password.
     * **/
    public abstract boolean register(String userName, String password);

    /**
     * @return boolean indicating the authorizations status of the user.
     * **/
    public boolean isAuthorized() {
        return authorized;
    };
}
