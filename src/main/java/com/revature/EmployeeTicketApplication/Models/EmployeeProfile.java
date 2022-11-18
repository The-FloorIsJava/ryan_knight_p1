package com.revature.EmployeeTicketApplication.Models;

import com.revature.EmployeeTicketApplication.AccountExceptions.AccountDoesNotExistException;
import com.revature.EmployeeTicketApplication.AccountExceptions.BadPasswordException;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class EmployeeProfile implements PasswordProtectedProfile {

    private final List<Ticket> profileTickets;
    private final String username;

    private String firstName;
    private String lastName;
    private String password;
    private final boolean isAdministrator;


    public EmployeeProfile(String username, String firstName,String lastName,
                           String password, boolean isAdministrator) {

        this.profileTickets = new LinkedList<>();
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.isAdministrator = isAdministrator;

    }

    public EmployeeProfile(String username, String firstName,String lastName, String password){
        this(username,firstName,lastName,password,false);
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
        }
        this.password = newPassword;

    }

    @Override
    public boolean isAdministrator() {
        return isAdministrator;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void addTicket(double balance) {
        profileTickets.add(new Ticket(username,balance));
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword(){
        return password;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeProfile that = (EmployeeProfile) o;



        return isAdministrator == that.isAdministrator && Objects.equals(profileTickets, that.profileTickets)
                && username.equals(that.username) && firstName.equals(that.firstName)
                && lastName.equals(that.lastName) && password.equals(that.password)
                && profileTickets.equals(that.profileTickets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileTickets, username, firstName, lastName, password, isAdministrator);
    }
}
