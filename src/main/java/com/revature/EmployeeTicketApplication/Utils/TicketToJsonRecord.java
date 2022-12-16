package com.revature.EmployeeTicketApplication.Utils;

/***
 * Used to make ticket dates legible in Postman.
 */

public record TicketToJsonRecord(int id, String username, String date,String description, String amount, String status) {

}
