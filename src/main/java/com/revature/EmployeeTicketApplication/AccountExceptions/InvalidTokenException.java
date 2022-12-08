package com.revature.EmployeeTicketApplication.AccountExceptions;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(){
        super("Invalid token.");
    }
}
