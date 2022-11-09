package Authenticator;

public class AuthenticationService extends Authenticator{

    @Override
    public boolean login(String userName, String password) {
        return false;
    }

    @Override
    public boolean register(String userName, String password) {
        return false;
    }
}
