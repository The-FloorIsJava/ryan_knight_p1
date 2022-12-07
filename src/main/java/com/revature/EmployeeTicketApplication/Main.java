package com.revature.EmployeeTicketApplication;

import com.revature.EmployeeTicketApplication.Controllers.ApplicationController;
import com.revature.EmployeeTicketApplication.DAO.ProfileDAO;
import com.revature.EmployeeTicketApplication.DAO.TicketDAO;
import com.revature.EmployeeTicketApplication.Services.ProfileService;
import com.revature.EmployeeTicketApplication.Services.TicketService;
import com.revature.EmployeeTicketApplication.Token.JWTUtility;
import io.javalin.Javalin;

import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException {

        // Setup dependencies
        ProfileService profileService = new ProfileService(new ProfileDAO());
        TicketService ticketService = new TicketService(new TicketDAO());
        Javalin javalin = Javalin.create().start(8080);
        JWTUtility jwtUtility = new JWTUtility();

        // Build controller with above dependencies and run application.
        ApplicationController controller = new ApplicationController(javalin, profileService, ticketService, jwtUtility);
        controller.run();
    }
}

