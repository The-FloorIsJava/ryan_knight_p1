package main.java.AuthenticationDB;

import java.util.HashMap;
import java.util.Map;

public class TemporaryPasswordMapDB implements AuthenticationDB{


    private final Map<String, String> passwordMap;

    public TemporaryPasswordMapDB() {
        passwordMap = new HashMap<>();
    }

    @Override
    public boolean verifyUsernameAndPassword(String username, String password) throws IllegalArgumentException{
        if (passwordMap.containsKey(username)) {
            return passwordMap.get(username).equals(password);
        } else {
            throw new IllegalArgumentException("No account associated with username " + username);
        }
    }

    @Override
    public boolean addNewUser(String username, String password) throws IllegalArgumentException {

        // Throw illegal argument exception if username is taken (i.e. it is a key in passwordMap).
        if (passwordMap.containsKey(username)) {
            throw new IllegalArgumentException("Bad username, already in use.");
        }

        passwordMap.put(username, password);

        return true;
    }

    @Override
    public boolean removeUser(String username, String password) throws IllegalArgumentException {
        // Return false if user is not registered.
        if (!passwordMap.containsKey(username)) {
            return false;
        }
        // Throw illegal argument exception if password does not match username.
        else if (!passwordMap.get(username).equals(password)) {
            throw new IllegalArgumentException("Invalid password for " + username);
        } else {
            passwordMap.remove(username);
            return true;
        }
    }
}
