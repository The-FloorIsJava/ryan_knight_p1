package com.revature.EmployeeTicketApplication.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.EmployeeTicketApplication.AccountExceptions.AccountDoesNotExistException;
import com.revature.EmployeeTicketApplication.AccountExceptions.BadPasswordException;
import com.revature.EmployeeTicketApplication.DAO.ProfileDAO;
import com.revature.EmployeeTicketApplication.DAO.TicketDAO;
import com.revature.EmployeeTicketApplication.Models.*;
import com.revature.EmployeeTicketApplication.Services.ProfileService;
import com.revature.EmployeeTicketApplication.Services.TicketService;
import com.revature.EmployeeTicketApplication.Token.JWTUtility;
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
    private final JWTUtility jwtUtility;

    public ApplicationController(Javalin javalin, ProfileService profileService,
                                 TicketService ticketService, JWTUtility jwtUtility) {

        this.profileService = profileService;
        this.ticketService = ticketService;
        this.app = javalin;
        this.jwtUtility = jwtUtility;

    }

    public void run() {

        // Post handlers
        app.post("register-employee",this::registerEmployee);
        app.post("register-administrator",this::registerAdministrator);
        app.post("login",this::login);
        app.post("{username}/ticket",this::submitTicket);
        app.post("{username}/update-ticket",this::updateTicketStatus);

        // Get handlers
        app.get("pending-tickets",this::getAllPending);
        app.get("{username}/tickets",this::getTicketsAssociatedWithProfile);
        app.get("get-ticket",this::getTicketByID);


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
            context.json("Profile successfully created for" + passwordProtectedProfile.getUsername());
        } else {
            context.json("Profile associated with username \"" + passwordProtectedProfile.getUsername()
            + "\" already exists.");
        }
    }

    /**
     *
     * */
    private void login(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Credentials credentials = mapper.readValue(context.body(), Credentials.class);

        try {

            // Verify credentials are valid.
            PasswordProtectedProfile profile = profileService.login(credentials.username(), credentials.password());
            String token = jwtUtility.createToken(profile);
            context.header("Authorization",token);
            context.json("Loged in as " + credentials.username());

        } catch (BadPasswordException e) {
            context.status(404);
            context.json("Invalid password");
        } catch (AccountDoesNotExistException e) {
            context.status(404);
            context.json("No account exists with username " + credentials.username());
        } catch (Exception e) {
            e.printStackTrace();
            context.status(500);
            context.json("Server side issue");
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
        /*
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
        }*/

    }

    private void getAllPending(Context context) {
        /*
        // Confirm someone is logged in.
        if (profileService.getAuthorizedAccount()==null) {
            context.json("Login in as administrator to view pending tickets");
        } else if (!profileService.getAuthorizedAccount().isAdministrator()) {
            context.json("You are not an administrator, you are not permitted to view other people's tickets!");
        } else {
            List<Ticket> ticketList = ticketService.getAllPending();

            List<TicketToJsonRecord> jsonList = fromTicketListToTicketToJsonRecordList(ticketList);

            context.json(jsonList);
        }*/
    }

    private void getTicketsAssociatedWithProfile(Context context) {
        /*
        if (profileService.getAuthorizedAccount()==null) {
            context.json("Login to view your tickets.");
        } else {
            List<Ticket> ticketList = ticketService.getProfileTickets(
                    profileService.getAuthorizedAccount().getUsername());
            List<TicketToJsonRecord> jsonList = fromTicketListToTicketToJsonRecordList(ticketList);
            context.json(jsonList);
        }
        */


    }

    private void getTicketByID (Context context) {

        context.json(ticketService.getTicketByID(1));

    }


    private void updateTicketStatus(Context context) {
        /*
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

            // Prohibit ticket from being updated if it has already been processed.
            if (ticketService.getTicketByID(updateTicket.ticket_id()).getTicketStatus()!= TicketStatus.PENDING) {
                context.json("Ticket already processed.");
            } else {
                ticketService.updateTicketStatus(updateTicket.ticket_id(),
                        TicketStatus.valueOf(updateTicket.status().toUpperCase()));
                context.json("Ticket successfully updated.");
            }
        }*/
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
