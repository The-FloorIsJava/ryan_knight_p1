package com.revature.EmployeeTicketApplication.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.EmployeeTicketApplication.DAO.ProfileDAO;
import com.revature.EmployeeTicketApplication.Models.AdministratorProfile;
import com.revature.EmployeeTicketApplication.Models.EmployeeProfile;
import com.revature.EmployeeTicketApplication.Models.PasswordProtectedProfile;
import com.revature.EmployeeTicketApplication.Services.ProfileService;
import com.revature.EmployeeTicketApplication.Utils.Credentials;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.DriverManager;
import java.sql.ResultSet;

public class ApplicationController {

    Javalin app;
    ProfileService profileService;

    public ApplicationController(Javalin javalinApp) {

        profileService = new ProfileService(new ProfileDAO());
        app = javalinApp;

    }

    public void run() {

        // Post handlers
        app.post("registerEmployee",this::registerEmployee);
        app.post("registerAdministrator",this::registerAdministrator);
        app.post("login",this::login);

        // Get handlers


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
            context.json("Profile successfully created, now loged in as " + passwordProtectedProfile.getUsername());
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

}
