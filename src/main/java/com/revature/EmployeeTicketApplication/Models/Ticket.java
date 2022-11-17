package com.revature.EmployeeTicketApplication.Models;

import java.util.*;

public class Ticket {

    // Fields
    private static Map<Integer,Ticket> idMap;
    private final int ticketID;
    private final String username;
    private final Date submisionDate;
    private final double amount;
    private TicketStatus ticketStatus;

    public Ticket(String username, Date submissionDate, double amount) {
        // Initialize idMap if not done already.
        if (Ticket.idMap == null) {
            Ticket.idMap = new HashMap<>();
        }

        Random rand = new Random();

        // Create unique ticket id, ensure it is not already in use.
        int proposedID = rand.nextInt(15);
        while (Ticket.idMap.containsKey(proposedID)) {
            proposedID = rand.nextInt(15);
        }

        this.ticketID = proposedID;
        this.ticketStatus = TicketStatus.PENDING;
        this.username = username;
        this.submisionDate = submissionDate;
        this.amount = amount;

    }

    public Ticket(String username, double amount) {
        this(username, new Date(), amount);
    }

    public Ticket(Ticket toBeCopied) {
        this(toBeCopied.username,new Date(toBeCopied.submisionDate.getDate()),toBeCopied.amount);
    }

    public int getTicketID() {
        return ticketID;
    }

    public String getUsername() {
        return username;
    }

    public String getSubmisionDate() {
        return submisionDate.toString();
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return ticketID == ticket.ticketID &&
                amount == ticket.amount &&
                username.equals(ticket.username) &&
                submisionDate.equals(ticket.submisionDate) &&
                ticketStatus == ticket.ticketStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketID, username, submisionDate, amount, ticketStatus);
    }
}
