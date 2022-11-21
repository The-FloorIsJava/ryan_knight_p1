package com.revature.EmployeeTicketApplication.Services;

import com.revature.EmployeeTicketApplication.DAO.ProfileDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProfileServiceTestSuite {

    @Test
    void test_login_returnTrue_givenValidCredentials() {
        Assertions.assertTrue((new ProfileService(new ProfileDAO())).login("CromwellOliv","goodPassword"));
    }

    @Test
    void test_login_returnFalse_givenWrongPassword() {
        Assertions.assertFalse((new ProfileService(new ProfileDAO())).login("CromwellOliv","wrongPassword"));
    }

}
