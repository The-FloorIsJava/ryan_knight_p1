package main.java.ProfileDB;

import java.util.ArrayList;

public class Profile implements ProfileInterface {
    private final String username;
    private final String email;
    /**
     * ArrayList containing all user's tickets.
     * */
    private final ArrayList<Ticket> tickets;

    /**
     * Username and email required to initialize class
     * */
    public Profile(final String username, final String email) {
        this.username = username;
        this.email = email;
        this.tickets = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Ticket[] getTickets() {
        Ticket[] buffer = new Ticket[tickets.size()];
        return tickets.toArray(buffer);
    }
}
