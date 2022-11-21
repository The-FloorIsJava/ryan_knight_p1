package com.revature.EmployeeTicketApplication.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.EmployeeTicketApplication.DAO.ProfileDAO;
import com.revature.EmployeeTicketApplication.DAO.TicketDAO;
import com.revature.EmployeeTicketApplication.Models.AdministratorProfile;
import com.revature.EmployeeTicketApplication.Models.EmployeeProfile;
import com.revature.EmployeeTicketApplication.Models.PasswordProtectedProfile;
import com.revature.EmployeeTicketApplication.Models.Ticket;
import com.revature.EmployeeTicketApplication.Services.ProfileService;
import com.revature.EmployeeTicketApplication.Services.TicketService;
import com.revature.EmployeeTicketApplication.Utils.Credentials;
import com.revature.EmployeeTicketApplication.Utils.TicketRecord;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.DriverManager;
import java.sql.ResultSet;

public class ApplicationController {

    private final Javalin app;
    private final ProfileService profileService;
    private final TicketService ticketService;

    public ApplicationController(Javalin javalinApp) {

        profileService = new ProfileService(new ProfileDAO());
        ticketService = new TicketService(new TicketDAO());
        app = javalinApp;

    }

    public void run() {

        // Post handlers
        app.post("registerEmployee",this::registerEmployee);
        app.post("registerAdministrator",this::registerAdministrator);
        app.post("login",this::login);
        app.post("submitTicket",this::submitTicket);

        // Get handlers

        // delete handlers
        app.delete("logout",this::logout);


    }

    /**
     * Registers employee by adding their profile to the database.
     * Once employee has been registered automatically logs them in.
     * @param context object.
     * */
    private void registerEmployee(Context context) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            register(context,mapper.readValue(context.body(),EmployeeProfile.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Registers administrator by adding their profile to the database.
     * Once administrator has been registered automatically logs them in.
     * @param context object.
     * */
    private void registerAdministrator(Context context) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            register(context,mapper.readValue(context.body(),AdministratorProfile.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Register account.
     * */
    private void register(Context context, PasswordProtectedProfile passwordProtectedProfile) {

        if (profileService.register(passwordProtectedProfile)) {
            profileService.login(passwordProtectedProfile.getUsername(), passwordProtectedProfile.getPassword());
            context.json("Profile successfully created, now logged in as " + passwordProtectedProfile.getUsername());
        } else {
            context.json("Profile associated with username \"" + passwordProtectedProfile.getUsername()
            + "\" already exists.");
        }
    }

    /**
     *
     * */
    private void login(Context context) {
        ObjectMapper mapper = new ObjectMapper();
        try {

            Credentials credentials = mapper.readValue(context.body(), Credentials.class);
            profileService.login(credentials.username(),credentials.password());

            if (profileService.getAuthorizedAccount()!=null) {
                context.json("Logged in as " + profileService.getAuthorizedAccount().getUsername());
            } else {
                context.json("Bad credentials, no account exists with given username and password.");
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    private void logout(Context context) {

        if (profileService.getAuthorizedAccount() != null) {
            context.json("Logging out " + profileService.getAuthorizedAccount().getUsername());
            profileService.logout();
        } else {
            context.json("There is no one to logout.");
        }

    }

    private void submitTicket(Context context) {

        ObjectMapper mapper = new ObjectMapper();

        // Get amount from posted json content.
        double amount;
        try {
            amount = mapper.readValue(context.body(), TicketRecord.class).amount();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // Ensure user is logged in.
        if (profileService.getAuthorizedAccount()==null) {
            context.json("Login to submit ticket.");
        }
        //  Ensure amount is not less than or equal to 0.
        else if (amount <= 0) {
            context.json("Invalid ticket amount, must be greater than 0.");
        }
        // Submit ticket for authorized account.
        else {
            Ticket ticket = new Ticket(profileService.getAuthorizedAccount().getUsername(), amount);
            ticketService.enterTicket(ticket);
            context.json("Ticket submitted for " + amount);
        }

    }

}
