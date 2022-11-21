package com.revature.EmployeeTicketApplication.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.EmployeeTicketApplication.DAO.ProfileDAO;
import com.revature.EmployeeTicketApplication.Models.AdministratorProfile;
import com.revature.EmployeeTicketApplication.Models.EmployeeProfile;
import com.revature.EmployeeTicketApplication.Services.ProfileService;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class ApplicationController {

    Javalin app;
    ProfileService profileService;

    public ApplicationController(Javalin javalinApp) {

        profileService = new ProfileService(new ProfileDAO());
        app = javalinApp;

    }

    public void run() {

        app.post("registerEmployee",this::registerEmployee);
        app.post("registerAdministrator",this::registerAdministrator);
        app.post("login",this::login);

    }

    private void registerEmployee(Context context) {
        ObjectMapper mapper = new ObjectMapper();
        try {

            EmployeeProfile employeeProfile = mapper.readValue(context.body(),EmployeeProfile.class);
            profileService.register(employeeProfile);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    private void registerAdministrator(Context context) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            AdministratorProfile administratorProfile = mapper.readValue(context.body(),AdministratorProfile.class);
            profileService.register(administratorProfile);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void login(Context context) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String[] credentials = mapper.readValue(context.body(),String[].class);
            profileService.login(credentials[0],credentials[1]);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }



    }

}
