package com.revature.EmployeeTicketApplication.Models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileFactory {

    public static PasswordProtectedProfile getProfileFromResultSet(ResultSet resultSet){
        try {
            // If admin is false, return employee profile, otherwise return AdministratorProfile.
            if (resultSet.next() && !resultSet.getBoolean(5)) {
                return new EmployeeProfile(
                        resultSet.getString("username"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("password")
                );
            } else {
                return new AdministratorProfile();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
