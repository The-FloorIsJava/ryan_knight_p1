package com.revature.EmployeeTicketApplication;

import com.revature.EmployeeTicketApplication.Utils.ConnectionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * Tests for conection factory based on in class example
 * */
public class ConnectionFactoryTestSuite {


    @Test
    public void test_getConnection() {
        try (Connection connection = ConnectionFactory
                .getConnectionFactoryInstance()
                .getConnection()) {

            System.out.println(connection);
            Assertions.assertNotNull(connection);

        }catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

}
