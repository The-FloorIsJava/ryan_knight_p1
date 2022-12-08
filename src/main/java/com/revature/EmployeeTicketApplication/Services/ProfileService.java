package com.revature.EmployeeTicketApplication.Services;

import com.revature.EmployeeTicketApplication.AccountExceptions.AccountDoesNotExistException;
import com.revature.EmployeeTicketApplication.AccountExceptions.BadPasswordException;
import com.revature.EmployeeTicketApplication.DAO.DAO;
import com.revature.EmployeeTicketApplication.Models.PasswordProtectedProfile;
import com.revature.EmployeeTicketApplication.Models.ProfileFactory;

import java.sql.SQLIntegrityConstraintViolationException;


public class ProfileService {

    /**
     * DAO for service to manipulate.
     * */
    private final DAO<PasswordProtectedProfile,String> profileStringDAO;

    /**
     * Constructor
     * @param profileStringDAO data access object to be manipulated.
     * */
    public ProfileService(DAO<PasswordProtectedProfile,String> profileStringDAO) {
        this.profileStringDAO = profileStringDAO;
    }

    /**
     * Registers profile by making a record of it in the profiles table.
     * @param passwordProtectedProfile profile being registered
     * @return true if registration is successful, otherwise returns false.
     * */
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

    /**
     * Attempts to login user. If credentials are valid, sets passwordProtectedProfile field to an instance of
     * PasswordProtectedProfile which is associated with the given credentials.
     * @param username - primary key.
     * @param password of account being loged into.
     * @return true if the account exists and the password is valid, otherwise returns false.
     * */
    public PasswordProtectedProfile login(String username, String password) throws BadPasswordException, AccountDoesNotExistException {

       PasswordProtectedProfile passwordProtectedProfile = profileStringDAO.get(username);

        if (passwordProtectedProfile ==null) {
            throw new AccountDoesNotExistException();
        } else if (!passwordProtectedProfile.getPassword().equals(password)) {
            throw new BadPasswordException();
        } else {
            return passwordProtectedProfile;
        }
    }

}
