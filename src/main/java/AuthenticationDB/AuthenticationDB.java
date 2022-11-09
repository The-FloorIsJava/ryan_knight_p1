package AuthenticationDB;

public interface AuthenticationDB {
    /**
     * Search database for corresponding username and password, if found return true.
     * @param username username, primary key.
     * @param  password user's password.
     * @return true if credentials are verified, false otherwise.
     * **/
    boolean verifyUsernameAndPassword(String username, String password);

    /**
     * Add new user to database.
     * @param username, primary key, registration fails if key is taken.
     * @param password user's password.
     * @return true if registration succeeds, otherwise return false.
     * **/
    boolean addNewUser(String username, String password);
}
