package com.revature.EmployeeTicketApplication.Profile;

import com.revature.EmployeeTicketApplication.Models.PasswordProtectedProfile;

public interface PasswordProtectedDB {

    /**
     * Add profile to database.
     * @param passwordProtectedProfile profile being added.
     * */
    void addProfile(PasswordProtectedProfile passwordProtectedProfile);

    /**
     * Remove profile associated with username, which is the profile's primary key.
     * @param username associated with profile being removed.
     * @param password associated with profile being removed.
     * */
    void removeProfile(String username, String password);

    /**
     * Remove profile associated with username, given a profile with is an administrator.
     * @param username associated with profile being removed.
     * @param administrator removing profile, method must confirm administrator is a valid.
     * */
    void removeProfile(String username, PasswordProtectedProfile administrator);

    /**
     * Get profile
     * @param username associated with profile.
     * @param password  associated with account.
     * */
    PasswordProtectedProfile getProfile(String username, String password);

    /**
     * Get profile
     * @param username associated with profile fetched.
     * @param administrator getting profile, method must confirm administrator is a valid.
     * */
    PasswordProtectedProfile getProfile(String username, PasswordProtectedProfile administrator);
}
