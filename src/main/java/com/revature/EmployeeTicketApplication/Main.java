package com.revature.EmployeeTicketApplication;

import com.revature.EmployeeTicketApplication.Controllers.ApplicationController;
import com.revature.EmployeeTicketApplication.DAO.ProfileDAO;
import com.revature.EmployeeTicketApplication.DAO.TicketDAO;
import com.revature.EmployeeTicketApplication.Services.ProfileService;
import com.revature.EmployeeTicketApplication.Services.TicketService;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {

        // Setup dependencies
        ProfileService profileService = new ProfileService(new ProfileDAO());
        TicketService ticketService = new TicketService(new TicketDAO());
        Javalin javalin = Javalin.create().start(8080);

        // Build controller with above dependencies and run application.
        ApplicationController controller = new ApplicationController(javalin, profileService, ticketService);
        controller.run();
    }
}

