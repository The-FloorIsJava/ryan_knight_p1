package com.revature.EmployeeTicketApplication.AccountExceptions;

public class DuplicatePrimaryKeyException extends Exception{
    public DuplicatePrimaryKeyException() {
        super("Duplicate primary key.");
    }

    public DuplicatePrimaryKeyException(String message) {
        super(message);
    }
}
