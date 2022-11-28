package com.revature.EmployeeTicketApplication.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.EmployeeTicketApplication.DAO.ProfileDAO;
import com.revature.EmployeeTicketApplication.DAO.TicketDAO;
import com.revature.EmployeeTicketApplication.Models.*;
import com.revature.EmployeeTicketApplication.Services.ProfileService;
import com.revature.EmployeeTicketApplication.Services.TicketService;
import com.revature.EmployeeTicketApplication.Utils.Credentials;
import com.revature.EmployeeTicketApplication.Utils.TicketRecord;
import com.revature.EmployeeTicketApplication.Utils.TicketToJsonRecord;
import com.revature.EmployeeTicketApplication.Utils.UpdateTicket;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ApplicationController {

    private final Javalin app;
    private final ProfileService profileService;
    private final TicketService ticketService;

    public ApplicationController(Javalin javalin, ProfileService profileService,
                                 TicketService ticketService) {

        this.profileService = profileService;
        this.ticketService = ticketService;
        app = javalin;

    }

    public void run() {

        // Post handlers
        app.post("registerEmployee",this::registerEmployee);
        app.post("registerAdministrator",this::registerAdministrator);
        app.post("login",this::login);
        app.post("submitTicket",this::submitTicket);
        app.post("updateTicket",this::updateTicketStatus);

        // Get handlers
        app.get("pendingTickets",this::getAllPending);
        app.get("getMyTickets",this::getTicketsAssociatedWithProfile);
        app.get("getTicket",this::getTicketByID);

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
        String description;
        try {
            amount = mapper.readValue(context.body(), TicketRecord.class).amount();
            description = mapper.readValue(context.body(),TicketRecord.class).description();
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
        // Ensure valid description is used.
        else if (description == null || description.length() == 0) {
            context.json("Ticket submission must include valid description.");
        }
        // Submit ticket for authorized account.
        else {
            Ticket ticket = new Ticket(profileService.getAuthorizedAccount().getUsername(),description,amount);
            ticketService.enterTicket(ticket);
            context.json("Ticket submitted for " + amount);
        }

    }

    private void getAllPending(Context context) {

        // Confirm someone is logged in.
        if (profileService.getAuthorizedAccount()==null) {
            context.json("Login in as administrator to view pending tickets");
        } else if (!profileService.getAuthorizedAccount().isAdministrator()) {
            context.json("You are not an administrator, you are not permitted to view other people's tickets!");
        } else {
            List<Ticket> ticketList = ticketService.getAllPending();

            List<TicketToJsonRecord> jsonList = fromTicketListToTicketToJsonRecordList(ticketList);

            context.json(jsonList);
        }
    }

    private void getTicketsAssociatedWithProfile(Context context) {

        if (profileService.getAuthorizedAccount()==null) {
            context.json("Login to view your tickets.");
        } else {
            List<Ticket> ticketList = ticketService.getProfileTickets(
                    profileService.getAuthorizedAccount().getUsername());
            context.json(ticketList);
        }

    }

    private void getTicketByID (Context context) {

        context.json(ticketService.getTicketByID(1));

    }


    private void updateTicketStatus(Context context) {

        if (profileService.getAuthorizedAccount()==null) {
            context.json("Login as administrator to update ticket.");
        } else if (!profileService.getAuthorizedAccount().isAdministrator()) {
            context.json("Must be administrator to update tickets.");
        } else {
            ObjectMapper mapper = new ObjectMapper();
            UpdateTicket updateTicket;

            try {
                updateTicket = mapper.readValue(context.body(),UpdateTicket.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }


            ticketService.updateTicketStatus(updateTicket.ticket_id(),
                    TicketStatus.valueOf(updateTicket.status().toUpperCase()));
            context.json("Ticket successfully updated.");
        }
    }

    /**
     * Get list of TicketToJsonRecords from list of tickets, so that the dates are legible in Postman
     * */
    private List<TicketToJsonRecord> fromTicketListToTicketToJsonRecordList(List<Ticket> ticketList) {
        List<TicketToJsonRecord> jsonList = new ArrayList<>();

        for (Ticket ticket : ticketList) {
            jsonList.add(new TicketToJsonRecord(
                    ticket.getTicketID(),
                    ticket.getUsername(),
                    ticket.getSubmissionDate().toString(),
                    Double.toString(ticket.getAmount()),
                    ticket.getTicketStatus().toString()
            ));
        }
        return jsonList;
    }

}
