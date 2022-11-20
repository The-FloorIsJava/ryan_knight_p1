package com.revature.EmployeeTicketApplication.Models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileFactory {

    public static PasswordProtectedProfile getProfileFromResultSet(ResultSet resultSet){
        try {

            if (!resultSet.next()) {
                return null;
            }

            // If admin is false, return employee profile, otherwise return AdministratorProfile.
            if (!resultSet.getBoolean(5)) {
                return new EmployeeProfile(
                        resultSet.getString("username"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("password")
                );
            } else {
                return new AdministratorProfile(
                        resultSet.getString("username"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("password")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
