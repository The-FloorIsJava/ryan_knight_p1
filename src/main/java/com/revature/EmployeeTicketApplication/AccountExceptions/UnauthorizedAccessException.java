package com.revature.EmployeeTicketApplication.AccountExceptions;

public class UnauthorizedAccessException extends RuntimeException{
    public UnauthorizedAccessException(String message) {
        super(message);
    }

    public UnauthorizedAccessException(){
        this("Unauthorized access.");
    }

}
