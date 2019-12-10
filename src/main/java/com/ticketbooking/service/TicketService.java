package com.ticketbooking.service;

import com.ticketbooking.exceptions.TicketNotFoundException;

public interface TicketService {
	
	String cancelTicket(Long ticketId) throws TicketNotFoundException;

}
