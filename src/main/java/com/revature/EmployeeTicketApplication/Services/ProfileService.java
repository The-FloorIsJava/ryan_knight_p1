package com.revature.EmployeeTicketApplication.Services;

import com.revature.EmployeeTicketApplication.DAO.DAO;
import com.revature.EmployeeTicketApplication.Models.PasswordProtectedProfile;
import com.revature.EmployeeTicketApplication.Models.ProfileFactory;

import java.sql.SQLIntegrityConstraintViolationException;

public class ProfileService {
    private final DAO<PasswordProtectedProfile,String> profileStringDAO;
    private PasswordProtectedProfile authorizedAccount;

    public ProfileService(DAO<PasswordProtectedProfile,String> profileStringDAO) {
        this.profileStringDAO = profileStringDAO;
        this.authorizedAccount = null;
    }

    public boolean register(PasswordProtectedProfile passwordProtectedProfile) {
        // Check database for username.
        if (profileStringDAO.get(passwordProtectedProfile.getUsername())!=null) {
            return false;
        } else {
            try {
                profileStringDAO.save(passwordProtectedProfile);
                return true;
            } catch (SQLIntegrityConstraintViolationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean login(String username, String password) {

        PasswordProtectedProfile passwordProtectedProfile = profileStringDAO.get(username);

        if (passwordProtectedProfile ==null) {
            return false;
        } else if (passwordProtectedProfile.getPassword().equals(password)) {
            // If profile exists, confirm password and administrator status match.
            authorizedAccount = passwordProtectedProfile;
            return true;
        } else {
            return false;
        }

    }


}
