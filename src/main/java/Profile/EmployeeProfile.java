package main.java.Profile;

import main.java.AccountExceptions.AccountDoesNotExistException;
import main.java.AccountExceptions.BadPasswordException;

import java.util.LinkedList;
import java.util.List;

public class EmployeeProfile implements PasswordProtectedProfile {

    private final List<Ticket> profileTickets;
    private final String username;
    private String password;
    private final boolean isAdministrator;
    private final int minimumPasswordLength;

    public EmployeeProfile(String username, String password) throws BadPasswordException {
        this.profileTickets = new LinkedList<>();
        this.username = username;
        this.minimumPasswordLength = 5;
        this.isAdministrator = false;

        if (password.length() <= this.minimumPasswordLength) {
            throw new BadPasswordException("Password to short, must contain "
                    + minimumPasswordLength + " or more characters");
        }
    }

    /**
     * See PasswordProtectedProfile interface for details.
     * */
    @Override
    public boolean verifyCredentials(String username, String password) throws AccountDoesNotExistException {
        if (!this.username.equals(username)) {
            throw new AccountDoesNotExistException();
        }
        return this.password.equals(password);
    }

    /**
     * Change password associated with account.
     * @param username associated with account.
     * @param oldPassword associated with account.
     * @param newPassword to be associated with account.
     * @throws BadPasswordException if oldPassword is incorrect, or if new password is less than minimumPasswordLength.
     * @throws AccountDoesNotExistException if username is not associated with an account.
     * */
    @Override
    public void changePassword(String username, String oldPassword, String newPassword)
            throws AccountDoesNotExistException, BadPasswordException {

        if (!this.username.equals(username)) {
            throw new AccountDoesNotExistException();
        } else if (!this.password.equals(oldPassword)) {
            throw new BadPasswordException();
        } else if (this.minimumPasswordLength <= newPassword.length()) {
            throw new BadPasswordException("Password must contain at least "
                    + this.minimumPasswordLength + " characters");
        }

        this.password = newPassword;

    }

    @Override
    public boolean isAdministrator() {
        return isAdministrator;
    }

    public void addTicket(double balance) {
        profileTickets.add(new Ticket(username,balance));
    }

}
