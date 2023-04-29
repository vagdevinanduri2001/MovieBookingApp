package com.moviebooking.service;

import com.moviebooking.entity.Seat;
import com.moviebooking.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatService {
    @Autowired
    private SeatRepository seatRepository;
    public void addSeat(Seat seat){
        seatRepository.save(seat);
    }
}
