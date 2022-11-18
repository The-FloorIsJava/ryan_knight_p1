package com.revature.EmployeeTicketApplication.Models;

import com.revature.EmployeeTicketApplication.AccountExceptions.AccountDoesNotExistException;
import com.revature.EmployeeTicketApplication.AccountExceptions.BadPasswordException;

public class AdministratorProfile implements PasswordProtectedProfile{
    @Override
    public void changePassword(String username, String oldPassword, String newPassword) throws BadPasswordException, AccountDoesNotExistException {

    }

    @Override
    public boolean isAdministrator() {
        return false;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getFirstName() {
        return null;
    }

    @Override
    public String getLastName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void setFirstName(String firstName) {

    }

    @Override
    public void setLastName(String lastName) {

    }
}
