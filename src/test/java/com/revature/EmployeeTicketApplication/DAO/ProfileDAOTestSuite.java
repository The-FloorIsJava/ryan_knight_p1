package com.revature.EmployeeTicketApplication.DAO;

import com.revature.EmployeeTicketApplication.Models.EmployeeProfile;
import com.revature.EmployeeTicketApplication.Models.PasswordProtectedProfile;
import com.revature.EmployeeTicketApplication.Utils.ConnectionFactory;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProfileDAOTestSuite {

    // Fields
    private PasswordProtectedProfile passwordProtectedProfile;
    private DAO<PasswordProtectedProfile,String> profileDAO;
    private Connection connectionForDAO;


    @BeforeEach
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

    @AfterEach
    void tearDown(){
        try {
            connectionForDAO.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(1)
    void test_save_returnNonNullProfile_givenValidProfile(){

        try {
            Assertions.assertNotNull(profileDAO.save(passwordProtectedProfile));
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(2)
    void test_get_equalToLocalInstance_givenIdenticalProfileSuccessfullyFetched() {

        // passwordProtectedProfile field will be used as local instance.

        PasswordProtectedProfile fetchedFromDB = profileDAO.get(
                passwordProtectedProfile.getUsername()
        );

        Assertions.assertEquals(passwordProtectedProfile,fetchedFromDB);
    }

    @Test
    @Order(3)
    void test_update_firstNameIsNewFirstName_givenUpdateSuccessful() {

        String newName = "Richard";
        passwordProtectedProfile.setFirstName(newName);

        profileDAO.update(passwordProtectedProfile);

        Assertions.assertEquals(
                profileDAO.get(passwordProtectedProfile.getUsername()).getFirstName(),
                newName
        );

    }

    @Test
    @Order(4)
    void test_delete_getReturnsNull_givenRecordSuccessfullyDeleted() {


        profileDAO.delete(passwordProtectedProfile);
        Assertions.assertNull(profileDAO.get(passwordProtectedProfile.getUsername()));


    }

}
