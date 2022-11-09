package main.java.AuthenticationDB;

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
     * @param username, primary key.
     * @param password user's password.
     * @throws IllegalArgumentException if username is taken.
     * @return true if registration succeeds, otherwise return false.
     * **/
    boolean addNewUser(String username, String password) throws IllegalArgumentException;

    /**
     * Remove user from database
     * @param username, primary key, method should handle instance where username is not found.
     * @param password, user's password.
     * @throws IllegalArgumentException if password does not match username.
     * @return true if user is removed from database, otherwise returns false.
     * */
    boolean removeUser(String username, String password) throws IllegalArgumentException;
}
