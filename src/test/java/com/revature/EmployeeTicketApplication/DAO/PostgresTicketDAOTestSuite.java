package com.revature.EmployeeTicketApplication.DAO;

import com.revature.EmployeeTicketApplication.Models.EmployeeProfile;
import com.revature.EmployeeTicketApplication.Models.PasswordProtectedProfile;
import com.revature.EmployeeTicketApplication.Utils.ConnectionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class PostgresTicketDAOTestSuite {

    // Fields
    private PasswordProtectedProfile passwordProtectedProfile;
    private PostgresTicketDAO postgresTicketDAO;
    private Connection connectionForDAO;



    void setup(){
        passwordProtectedProfile = new EmployeeProfile(
                "CromwellOliv",
                "Oliver",
                "Cromwell",
                "goodPassword"
        );

        connectionForDAO = ConnectionFactory.
                getConnectionFactoryInstance().
                getConnection();

        postgresTicketDAO = new PostgresTicketDAO();
    }

    void tearDown(){
        try {
            connectionForDAO.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void test_addProfile_returnNonNullProfile_givenValidProfile(){
        setup();
        Assertions.assertNotNull(postgresTicketDAO.addProfile(passwordProtectedProfile));
        tearDown();
    }

    @Test
    void test_getProfile_equalToLocalInstance_givenIdenticalProfileSuccessfullyFetched() {
        setup();
        // passwordProtectedProfile field will be used as local instance.

        PasswordProtectedProfile fetchedFromDB = postgresTicketDAO.getProfile(
                passwordProtectedProfile.getUsername(),
                passwordProtectedProfile.getPassword()
        );


        Assertions.assertEquals(passwordProtectedProfile,fetchedFromDB);

        tearDown();
    }

}
