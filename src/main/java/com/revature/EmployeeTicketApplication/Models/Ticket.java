package com.revature.EmployeeTicketApplication.Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.sql.Date;

public class Ticket {

    // Fields
    private final int ticketID;
    private final String username;
    private final Date submissionDate;
    private final double amount;
    private TicketStatus ticketStatus;

    /**
     * @param username of ticket owner.
     * @param submissionDate date ticket is submitted.
     * @param amount the amount of the reimbursement.
     * */
    public Ticket(String username, Date submissionDate, double amount) {

        this.ticketID = -1;
        this.username = username;
        this.ticketStatus = TicketStatus.PENDING;
        this.submissionDate = submissionDate;

        if (amount < 0 ) {
            throw new IllegalArgumentException("amount @param must be greater than or equal to 0.");
        }

        this.amount = amount;

    }

    /**
     * Create ticket instance with null Date, where the date is to be defined by default in sql.
     * */
    public Ticket(String username, double amount) {
        this(username, null, amount);
    }

    public Ticket(ResultSet resultSet) throws SQLException {

        this.ticketID = resultSet.getInt("ticket_id");
        this.username = resultSet.getString("owner");
        this.amount = resultSet.getDouble("amount");
        this.submissionDate = resultSet.getDate("submission_date");
        this.ticketStatus = TicketStatus.valueOf(resultSet.getString("status").toUpperCase());
    }



    public int getTicketID() {
        return ticketID;
    }

    public String getUsername() {
        return username;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public double getAmount() {
        return amount;
    }


    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return ticketID == ticket.ticketID &&
                amount == ticket.amount &&
                username.equals(ticket.username) &&
                submissionDate.equals(ticket.submissionDate) &&
                ticketStatus == ticket.ticketStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketID, username, submissionDate, amount, ticketStatus);
    }
}
