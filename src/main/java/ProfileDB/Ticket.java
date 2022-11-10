package main.java.ProfileDB;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Ticket {

    // Fields
    private static Map<Integer,Ticket> idMap;
    private final int ticketID;
    private String username;
    private Date submisionDate;
    private double amount;

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
        this.username = username;
        this.submisionDate = submissionDate;
        this.amount = amount;

    }

    public Ticket(String username, double amount) {
        this(username, new Date(), amount);
    }

    public int getTicketID() {
        return ticketID;
    }

    public String getUsername() {
        return username;
    }

    public Date getSubmisionDate() {
        return submisionDate;
    }

    public double getAmount() {
        return amount;
    }
}
