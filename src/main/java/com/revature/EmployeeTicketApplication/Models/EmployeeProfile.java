package com.revature.EmployeeTicketApplication.Models;

import com.revature.EmployeeTicketApplication.AccountExceptions.AccountDoesNotExistException;
import com.revature.EmployeeTicketApplication.AccountExceptions.BadPasswordException;

import java.util.LinkedList;
import java.util.List;

public class EmployeeProfile implements PasswordProtectedProfile {

    private final String username;

    private String firstName;
    private String lastName;
    private String password;
    private final boolean isAdministrator;
    private final int minimumPasswordLength;

    public EmployeeProfile(String username, String firstName,String lastName, String password, boolean isAdministrator)
            throws BadPasswordException {

        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.minimumPasswordLength = 5;
        this.isAdministrator = isAdministrator;

        if (password.length() <= this.minimumPasswordLength) {
            throw new BadPasswordException("Password to short, must contain "
                    + minimumPasswordLength + " or more characters");
        }
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
