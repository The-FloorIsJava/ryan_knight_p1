package com.revature.EmployeeTicketApplication.Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfileFactory {

    public static PasswordProtectedProfile getProfileFromResultSet(ResultSet resultSet){
        try {

            if(!resultSet.next()) {
                return null;
            } else {
                return getPasswordProtectedProfileFromResultSetNoNextCheck(resultSet);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<PasswordProtectedProfile> getPasswordProtectedProfileListFromResultSet(ResultSet resultSet) {

       List<PasswordProtectedProfile> retList = new ArrayList<>();
       try {
           while (resultSet.next()) {
               retList.add(getPasswordProtectedProfileFromResultSetNoNextCheck(resultSet));
           }
       } catch (SQLException e) {
           e.printStackTrace();
           return null;
       }
        return retList;
    }


    private static PasswordProtectedProfile getPasswordProtectedProfileFromResultSetNoNextCheck(
            ResultSet resultSet) throws SQLException {

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

    }



}
