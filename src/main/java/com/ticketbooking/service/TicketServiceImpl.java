package com.ticketbooking.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticketbooking.constants.Constant;
import com.ticketbooking.dto.BookingRequestDto;
import com.ticketbooking.entity.Seat;
import com.ticketbooking.entity.Ticket;
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
	public String bookTicket(BookingRequestDto bookingRequestDto) {
		Ticket ticket = new Ticket();
		ticket.setPassengerName(bookingRequestDto.getPassengerName());
		ticket.setCancelStatus(Constant.CANCEL_STATUS);
		ticket.setPassengerPhonNumber(bookingRequestDto.getPassengerMobileNumber());
		ticket.setBusId(seatRepository.findById(bookingRequestDto.getSeatIds().get(0)).get().getBus().getBusId());
		ticketRepository.save(ticket);
		for (Integer seatId : bookingRequestDto.getSeatIds()) {
			Optional<Seat> seat = seatRepository.findById(seatId);
			if (seat.isPresent()) {
				seat.get().setStatus(Constant.SEAT_STATUS);
				seat.get().setTicket(ticket);
				seatRepository.save(seat.get());
			}
		}

		return Constant.BOOKING_SUCCESS;

	}

}
