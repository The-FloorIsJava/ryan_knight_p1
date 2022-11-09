package main.java.Authenticator;

import main.java.Authenticator.Authenticator;

public class AuthenticationService implements Authenticator {

    private boolean authorized;

    @Override
    public boolean login(String userName, String password) {
        return false;
    }

    @Override
    public boolean register(String userName, String password) {
        return false;
    }

    @Override
    public boolean isAuthorized() {
        return false;
    }
}
