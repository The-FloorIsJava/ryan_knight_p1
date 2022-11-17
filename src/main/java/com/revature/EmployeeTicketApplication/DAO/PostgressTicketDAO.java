package com.revature.EmployeeTicketApplication.DAO;

import com.revature.EmployeeTicketApplication.Models.PasswordProtectedProfile;
import com.revature.EmployeeTicketApplication.Utils.ConnectionFactory;

import java.sql.Connection;

public class PostgressTicketDAO implements PasswordProtectedDB{
    @Override
    public PasswordProtectedProfile addProfile(PasswordProtectedProfile passwordProtectedProfile) {



    }

    @Override
    public void removeProfile(String username, String password) {

    }

    @Override
    public void removeProfile(String username, PasswordProtectedProfile administrator) {

    }

    @Override
    public PasswordProtectedProfile getProfile(String username, String password) {
        return null;
    }

    @Override
    public PasswordProtectedProfile getProfile(String username, PasswordProtectedProfile administrator) {
        return null;
    }
}
