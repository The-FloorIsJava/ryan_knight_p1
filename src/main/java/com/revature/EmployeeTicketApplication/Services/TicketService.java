package com.revature.EmployeeTicketApplication.Services;

import com.revature.EmployeeTicketApplication.DAO.TicketDAO;
import com.revature.EmployeeTicketApplication.Models.Ticket;
import com.revature.EmployeeTicketApplication.Models.TicketStatus;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class TicketService {

    TicketDAO ticketIntegerDAO;

    public TicketService(TicketDAO ticketIntegerDAO) {
        this.ticketIntegerDAO = ticketIntegerDAO;
    }

    public void enterTicket(Ticket ticket) {
        try {
            ticketIntegerDAO.save(ticket);
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Ticket> getAllPending() {
        return ticketIntegerDAO.getAllConstraintStatus(TicketStatus.PENDING.toString());
    }

    public List<Ticket> getProfileTickets(String username) {
        return ticketIntegerDAO.getAllConstraintUsername(username);
    }


    public Ticket getTicketByID(int id) {
        return ticketIntegerDAO.get(id);
    }

    public boolean updateTicketStatus(int ticket_id, TicketStatus ticketStatus) {

        Ticket ticketToUpdate = ticketIntegerDAO.get(ticket_id);

        if (ticketToUpdate!=null) {
            ticketToUpdate.setTicketStatus(ticketStatus);
            ticketIntegerDAO.update(ticketToUpdate);
            return true;
        } else {
            return false;
        }

    }

}
