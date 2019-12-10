package com.ticketbooking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ticketbooking.constants.Constant;
import com.ticketbooking.entity.Seat;
import com.ticketbooking.entity.Ticket;
import com.ticketbooking.exceptions.TicketNotFoundException;
import com.ticketbooking.repository.SeatRepository;
import com.ticketbooking.repository.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	TicketRepository ticketRepository;

	@Autowired
	SeatRepository seatRepository;

	@Override
	@Transactional
	public String cancelTicket(Long ticketId) throws TicketNotFoundException {
		Optional<Ticket> ticket = ticketRepository.findById(ticketId);
		if (ticket.isPresent()) {
			ticket.get().setCancelStatus(Constant.SEAT_AVAILABLE_STATUS_YES);
			ticketRepository.save(ticket.get());
			List<Seat> seats = seatRepository.findAllByTicket(ticket);
			seats.forEach(seat -> {
				seat.setStatus(Constant.AVAILABLE);
				seat.setTicket(null);
				seatRepository.save(seat);
			});
			return Constant.SUCCESS;
		} else {
				throw new TicketNotFoundException(Constant.TICKET_NOT_FOUND);
		}
	}

}
