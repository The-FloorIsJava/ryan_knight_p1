package com.revature.EmployeeTicketApplication.DAO;

import com.revature.EmployeeTicketApplication.Models.PasswordProtectedProfile;
import com.revature.EmployeeTicketApplication.Models.Ticket;
import com.revature.EmployeeTicketApplication.Utils.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO implements DAOChildTable<Ticket,Integer, PasswordProtectedProfile> {
    @Override
    public Ticket save(Ticket record) throws SQLIntegrityConstraintViolationException {


        try(Connection connection = ConnectionFactory.getConnectionFactoryInstance().getConnection()) {

            // Define sql commands.
            String sql = "INSERT INTO tickets(owner, amount) " +
                    "VALUES(?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);


            preparedStatement.setString(1, record.getUsername());
            preparedStatement.setDouble(2,record.getAmount());


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

        String sql = "DELETE FROM tickets WHERE ticket_id=?;";

        try(Connection connection = ConnectionFactory.getConnectionFactoryInstance().getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,record.getTicketID());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Ticket get(Integer primaryKey) {

        try (Connection connection = ConnectionFactory.getConnectionFactoryInstance().getConnection()){

            String sql = "SELECT * FROM tickets WHERE ticket_id=?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,primaryKey);

            ResultSet resultSet = preparedStatement.executeQuery();


            if (!resultSet.next()) {
                return null;
            } else {
                return new Ticket(resultSet);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Ticket update(Ticket record) {

        String sql = "UPDATE tickets SET owner=?, amount=?, submission_date=?, " +
                "status=? WHERE ticket_id=?";

        try (Connection connection = ConnectionFactory.getConnectionFactoryInstance().getConnection()) {

            PreparedStatement preparedStatement =  connection.prepareStatement(sql);

            preparedStatement.setString(1,record.getUsername());
            preparedStatement.setDouble(2,record.getAmount());
            preparedStatement.setDate(2,record.getSubmissionDate());
            preparedStatement.setString(3,record.getTicketStatus().toString().toLowerCase());
            preparedStatement.setInt(4,record.getTicketID());

            preparedStatement.executeUpdate();

            return get(record.getTicketID());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Ticket> getAll() {

        String sql = "SELECT * FROM tickets;";

        try (Connection connection = ConnectionFactory.getConnectionFactoryInstance().getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Ticket> retArr = new ArrayList<>();

            while (resultSet.next()) {
                retArr.add(new Ticket(resultSet));
            }

            return retArr;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Ticket[] getAll(PasswordProtectedProfile foreignKey) {
        return new Ticket[0];
    }
}
