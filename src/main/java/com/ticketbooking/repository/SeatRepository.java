package com.ticketbooking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticketbooking.entity.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer>{

	Seat findSeatById(Integer id);


	
}
