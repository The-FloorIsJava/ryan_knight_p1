package com.revature.EmployeeTicketApplication.Models;

import com.revature.EmployeeTicketApplication.AccountExceptions.AccountDoesNotExistException;
import com.revature.EmployeeTicketApplication.AccountExceptions.BadPasswordException;

public interface PasswordProtectedProfile {


    /**
     * Change password associated with account.
     * @param username associated with account.
     * @param oldPassword associated with account.
     * @param newPassword to be associated with account.
     * @throws BadPasswordException if account does not exist.
     * @throws AccountDoesNotExistException if username is not associated with an account.
     * */
    void changePassword(String username, String oldPassword, String newPassword) throws BadPasswordException,
            AccountDoesNotExistException;

    /**
     * Return true if profile is for an administrator.
     * @return true if the profile owner is an administrator, otherwise returns false.
     * */
    boolean isAdministrator();

}
