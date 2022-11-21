package com.revature.EmployeeTicketApplication.Services;

import com.revature.EmployeeTicketApplication.DAO.DAO;
import com.revature.EmployeeTicketApplication.Models.Ticket;

import java.sql.SQLIntegrityConstraintViolationException;

public class TicketService {

    DAO<Ticket,Integer> ticketIntegerDAO;

    public TicketService(DAO<Ticket,Integer> ticketIntegerDAO) {
        this.ticketIntegerDAO = ticketIntegerDAO;
    }

    public void enterTicket(Ticket ticket) {
        try {
            ticketIntegerDAO.save(ticket);
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new RuntimeException(e);
        }
    }

}
