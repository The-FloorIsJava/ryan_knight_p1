package com.revature.EmployeeTicketApplication.Utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Connection factory
 * */
public class ConnectionFactory {

    // Static fields.
    private static final ConnectionFactory connectionFactoryInstance;

    // Initialize static fields.
    static {
        connectionFactoryInstance = new ConnectionFactory();

        try {
            // Search for and if found, load org.postgresql.Driver.
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Non-static fields.
    private final Properties properties;

    /**
     * Private constructor
     * */
    private ConnectionFactory() {
        properties = new Properties();
        try {
            properties.load(new FileReader("src/main/resources/config.properties"));
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    /**
     * Get single existing instance of ConnectionFactory
     * @return connectionFactoryInstance
     * */
    public static ConnectionFactory getConnectionFactoryInstance() {
        return connectionFactoryInstance;
    }


    /**
     * Get connection.
     * @return connection
     * */
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(properties.getProperty("url"),
                    properties.getProperty("user"),
                    properties.getProperty("password"));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
