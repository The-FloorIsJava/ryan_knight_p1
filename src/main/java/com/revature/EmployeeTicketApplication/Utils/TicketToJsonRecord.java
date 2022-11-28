package com.revature.EmployeeTicketApplication.Utils;

import com.revature.EmployeeTicketApplication.Models.Ticket;

/***
 * Used to make ticket dates legible in Postman.
 */

public record TicketToJsonRecord(int id, String username, String date, String amount, String status) {

}
