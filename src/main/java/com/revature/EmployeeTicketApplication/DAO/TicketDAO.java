package com.revature.EmployeeTicketApplication.DAO;

import com.revature.EmployeeTicketApplication.Models.Ticket;
import com.revature.EmployeeTicketApplication.Utils.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO implements DAO<Ticket,Integer> {
    @Override
    public Ticket save(Ticket record) throws SQLIntegrityConstraintViolationException {


        try(Connection connection = ConnectionFactory.getConnectionFactoryInstance().getConnection()) {

            // Define sql commands.
            String sql = "INSERT INTO tickets(username, description, amount) " +
                    "VALUES(?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);


            preparedStatement.setString(1, record.getUsername());
            preparedStatement.setString(2,record.getDescription());
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

        String sql = "UPDATE tickets SET username=?, amount=?, submission_date=?, " +
                "status=?::\"ticket_status\" WHERE ticket_id=?";

        try (Connection connection = ConnectionFactory.getConnectionFactoryInstance().getConnection()) {

            PreparedStatement preparedStatement =  connection.prepareStatement(sql);

            preparedStatement.setString(1,record.getUsername());
            preparedStatement.setDouble(2,record.getAmount());
            preparedStatement.setDate(3,record.getSubmissionDate());
            preparedStatement.setString(4,record.getTicketStatus().toString().toLowerCase());
            preparedStatement.setInt(5,record.getTicketID());

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



    public List<Ticket> getAllConstraintStatus(String constraint) {
        String sql = "SELECT * FROM tickets WHERE status=?::\"ticket_status\";";
        List<Ticket> retList = new ArrayList<>();

        try(Connection connection = ConnectionFactory.getConnectionFactoryInstance().getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,constraint);



            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                retList.add(new Ticket(resultSet));
            }

            return retList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public List<Ticket> getAllConstraintUsername(String constraint) {
        String sql = "SELECT * FROM tickets WHERE username=?;";
        List<Ticket> retList = new ArrayList<>();

        try(Connection connection = ConnectionFactory.getConnectionFactoryInstance().getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,constraint);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                retList.add(new Ticket(resultSet));
            }

            return retList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
