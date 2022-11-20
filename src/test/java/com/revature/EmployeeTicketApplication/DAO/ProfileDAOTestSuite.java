package com.revature.EmployeeTicketApplication.DAO;

import com.revature.EmployeeTicketApplication.Models.EmployeeProfile;
import com.revature.EmployeeTicketApplication.Models.PasswordProtectedProfile;
import com.revature.EmployeeTicketApplication.Utils.ConnectionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class ProfileDAOTestSuite {

    // Fields
    private PasswordProtectedProfile passwordProtectedProfile;
    private DAO<PasswordProtectedProfile,String> profileDAO;
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

        profileDAO = new ProfileDAO();
    }

    void tearDown(){
        try {
            connectionForDAO.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void test_save_returnNonNullProfile_givenValidProfile(){
        setup();
        try {
            Assertions.assertNotNull(profileDAO.save(passwordProtectedProfile));
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException(e);
        }
        tearDown();
    }

    @Test
    void test_get_equalToLocalInstance_givenIdenticalProfileSuccessfullyFetched() {
        setup();
        // passwordProtectedProfile field will be used as local instance.

        PasswordProtectedProfile fetchedFromDB = profileDAO.get(
                passwordProtectedProfile.getUsername()
        );


        Assertions.assertEquals(passwordProtectedProfile,fetchedFromDB);

        tearDown();
    }

    @Test
    void test_delete_getReturnsNull_givenRecordSuccessfullyDeleted() {
        setup();

        profileDAO.delete(passwordProtectedProfile);
        Assertions.assertNull(profileDAO.get(passwordProtectedProfile.getUsername()));

        tearDown();
    }

}
