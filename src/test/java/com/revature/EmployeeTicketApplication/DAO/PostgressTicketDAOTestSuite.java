package com.revature.EmployeeTicketApplication.DAO;

import com.revature.EmployeeTicketApplication.Models.EmployeeProfile;
import com.revature.EmployeeTicketApplication.Models.PasswordProtectedProfile;
import com.revature.EmployeeTicketApplication.Utils.ConnectionFactory;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class PostgressTicketDAOTestSuite {

    // Fields
    private PasswordProtectedProfile passwordProtectedProfile;
    private PostgressTicketDAO postgressTicketDAO;
    private Connection connectionForDAO;



    void addProfileSetup(){
        passwordProtectedProfile = new EmployeeProfile(
                "CromwellOliv",
                "Oliver",
                "Cromwel",
                "goodPassword"
        );

        connectionForDAO = ConnectionFactory.
                getConnectionFactoryInstance().
                getConnection();

        postgressTicketDAO = new PostgressTicketDAO();
    }

    void addProfileTearDown(){
        try {
            connectionForDAO.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void test_addProfile_notThrowRuntimeException_givenValidProfile() {

    }

    @Test
    void test_addProfile_returnNonNullProfile_givenValidProfile(){
        addProfileSetup();
        Assertions.assertNotNull(postgressTicketDAO.addProfile(passwordProtectedProfile));
        addProfileTearDown();

    }

}
