package com.revature.EmployeeTicketApplication.Models;

import com.revature.EmployeeTicketApplication.AccountExceptions.AccountDoesNotExistException;
import com.revature.EmployeeTicketApplication.AccountExceptions.BadPasswordException;

import java.util.LinkedList;

public class AdministratorProfile extends EmployeeProfile{

    public AdministratorProfile() {
        super.isAdministrator = true;
    }
    public AdministratorProfile(String username, String firstName, String lastName, String password) {
        super(username, firstName, lastName, password, true);
    }
}
