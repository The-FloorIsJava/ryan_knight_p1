package com.revature.EmployeeTicketApplication.DAO;

import com.revature.EmployeeTicketApplication.Models.EmployeeProfile;
import com.revature.EmployeeTicketApplication.Models.PasswordProtectedProfile;
import com.revature.EmployeeTicketApplication.Models.Ticket;
import com.revature.EmployeeTicketApplication.Models.TicketStatus;
import com.revature.EmployeeTicketApplication.Utils.ConnectionFactory;
import org.junit.BeforeClass;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TicketDAOTestSuite {

    static DAO<Ticket,Integer> ticketIntegerDAO = new TicketDAO();
    static Ticket ticket;
    static PasswordProtectedProfile ticketOwner = new EmployeeProfile("CromwellOliv",
            "Oliver","Cromwell","password");;
   static Connection connectionForDAO;

   /**
    @BeforeClass
    static void setupForAllTests() {
        ticketIntegerDAO = new TicketDAO();
        ticketOwner = new EmployeeProfile("CromOliv","Oliver","Cromwell","password");
        ticket = new Ticket(ticketOwner.getUsername(), 10.0);
    }*/

    @BeforeEach
    void setup(){
        connectionForDAO = ConnectionFactory.
                getConnectionFactoryInstance().
                getConnection();

        ticket = new Ticket(ticketOwner.getUsername(), 10.0);
    }

    @AfterEach
    void teardown(){
        try {
            connectionForDAO.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(1)
    void test_update_returnNonNullTicket_givenValidTicket() {
        try {
            Assertions.assertNotNull(ticketIntegerDAO.save(ticket));
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(2)
    void test_get_equalToLocalInstance_givenIdenticalProfileSuccessfullyFetched() {


        Ticket fetchedFromDB = ticketIntegerDAO.get(
                /*Found by manually looking through table in dbeaver.*/
                3
        );

        Assertions.assertNotNull(fetchedFromDB);
    }



}
