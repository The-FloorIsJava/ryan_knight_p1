package com.revature.EmployeeTicketApplication.DAO;

import com.revature.EmployeeTicketApplication.Models.PasswordProtectedProfile;
import com.revature.EmployeeTicketApplication.Models.ProfileFactory;
import com.revature.EmployeeTicketApplication.Utils.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfileDAO implements DAO<PasswordProtectedProfile,String> {
    @Override
    public PasswordProtectedProfile save(PasswordProtectedProfile record)
            throws SQLIntegrityConstraintViolationException {

        try(Connection connection = ConnectionFactory.getConnectionFactoryInstance().getConnection()) {

            // Define sql commands.
            String sql = "INSERT INTO profiles(username,first_name,last_name,password,admin) " +
                    "VALUES(?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, record.getUsername());
            preparedStatement.setString(2,record.getFirstName());
            preparedStatement.setString(3,record.getLastName());
            preparedStatement.setString(4,record.getPassword());
            preparedStatement.setBoolean(5, record.isAdministrator());

            int checkInsert = preparedStatement.executeUpdate();

            if (checkInsert == 0) {
                throw new RuntimeException("Failed to add profile to database");
            }

            return record;

        } catch (SQLIntegrityConstraintViolationException e){
            throw new SQLIntegrityConstraintViolationException();
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(PasswordProtectedProfile record) {
        String sql = "DELETE FROM profiles WHERE username=?";

        try(Connection connection = ConnectionFactory.getConnectionFactoryInstance().getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, record.getUsername());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PasswordProtectedProfile get(String primaryKey) {
        try (Connection connection = ConnectionFactory.getConnectionFactoryInstance().getConnection()) {

            String query = "Select * from profiles where username=?;";


            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,primaryKey);

            ResultSet rs = preparedStatement.executeQuery();
            return ProfileFactory.getProfileFromResultSet(rs);

        } catch (SQLException e) {

            e.printStackTrace();
            return null;

        }
    }

    @Override
    public PasswordProtectedProfile update(PasswordProtectedProfile record) {
        String sql = "UPDATE profiles SET first_name=?, last_name=?, password=?, admin=? WHERE username=?;";

        try (Connection connection = ConnectionFactory.getConnectionFactoryInstance().getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(sql);


            preparedStatement.setString(1,record.getFirstName());
            preparedStatement.setString(2,record.getLastName());
            preparedStatement.setString(3,record.getPassword());
            preparedStatement.setBoolean(4, record.isAdministrator());
            preparedStatement.setString(5,record.getUsername());


            int checkInsert = preparedStatement.executeUpdate();

            if (checkInsert == 0) {
                throw new RuntimeException("Failed to add profile to database");
            }

            return get(record.getUsername());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<PasswordProtectedProfile> getAll() {
        try (Connection connection = ConnectionFactory.getConnectionFactoryInstance().getConnection()) {

            String query = "Select * from profiles;";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            return ProfileFactory.getPasswordProtectedProfileListFromResultSet(rs);


        } catch (SQLException e) {

            e.printStackTrace();
            return null;

        }
    }

    @Override
    public List<PasswordProtectedProfile> getAllWhere(String filed, String constraint) {
        return null;
    }

}
