package com.revature.EmployeeTicketApplication.DAO;

import com.revature.EmployeeTicketApplication.Models.Ticket;
import com.revature.EmployeeTicketApplication.Utils.ConnectionFactory;

import java.sql.*;
import java.util.List;

public class TicketDAO implements DAO<Ticket,Integer> {
    @Override
    public Ticket save(Ticket record) throws SQLIntegrityConstraintViolationException {


        try(Connection connection = ConnectionFactory.getConnectionFactoryInstance().getConnection()) {

            // Define sql commands.
            String sql = "INSERT INTO profiles(username,first_name,last_name,password,admin) " +
                    "VALUES(?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);


            preparedStatement.setString(2, record.getUsername());
            preparedStatement.setDouble(3,record.getAmount());


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
    public void delete(Ticket record) {

    }

    @Override
    public Ticket get(Integer primaryKey) {
        return null;
    }

    @Override
    public Ticket update(Ticket record) {
        return null;
    }

    @Override
    public List<Ticket> getAll() {
        return null;
    }
}
