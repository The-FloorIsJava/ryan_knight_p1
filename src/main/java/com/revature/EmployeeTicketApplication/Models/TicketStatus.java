package com.revature.EmployeeTicketApplication.Models;

/**
 * Enum to categorize Ticket instances as pending, approved or declined. Tickets are pending by default.
 * */
public enum TicketStatus {
    PENDING("pending"),
    APPROVED("approved"),
    DECLINED("declined");

    private String status;

    TicketStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }

}
