package com.revature.EmployeeTicketApplication;

import com.revature.EmployeeTicketApplication.Controllers.ApplicationController;
import io.javalin.Javalin;

import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        ApplicationController controller = new ApplicationController(Javalin.create().start(8080));
        controller.run();
    }
}

