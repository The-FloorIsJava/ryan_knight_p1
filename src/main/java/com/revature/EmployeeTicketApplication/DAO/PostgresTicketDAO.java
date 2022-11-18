package com.revature.EmployeeTicketApplication.DAO;

import com.revature.EmployeeTicketApplication.Models.PasswordProtectedProfile;
import com.revature.EmployeeTicketApplication.Models.ProfileFactory;
import com.revature.EmployeeTicketApplication.Utils.ConnectionFactory;

import java.sql.*;

public class PostgresTicketDAO implements PasswordProtectedDB{


    @Override
    public PasswordProtectedProfile addProfile(PasswordProtectedProfile passwordProtectedProfile) {

        try(Connection connection = ConnectionFactory.getConnectionFactoryInstance().getConnection()) {

            // Define sql commands.
            String sql = "insert into profiles(username,first_name,last_name,password,admin) " +
                    "values(?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, passwordProtectedProfile.getUsername());
            preparedStatement.setString(2,passwordProtectedProfile.getFirstName());
            preparedStatement.setString(3,passwordProtectedProfile.getLastName());
            preparedStatement.setString(4,passwordProtectedProfile.getPassword());
            preparedStatement.setBoolean(5, passwordProtectedProfile.isAdministrator());

            int checkInsert = preparedStatement.executeUpdate();

            if (checkInsert == 0) {
                throw new RuntimeException("Failed to add profile to database");
            }

            return passwordProtectedProfile;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void removeProfile(String username, String password) {

    }

    @Override
    public void removeProfile(String username, PasswordProtectedProfile administrator) {

    }

    @Override
    public PasswordProtectedProfile getProfile(String username, String password) {

        try (Connection connection = ConnectionFactory.getConnectionFactoryInstance().getConnection()) {

            String query = "Select * from profiles";

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            return ProfileFactory.getProfileFromResultSet(rs);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PasswordProtectedProfile getProfile(String username, PasswordProtectedProfile administrator) {
        return null;
    }
}
