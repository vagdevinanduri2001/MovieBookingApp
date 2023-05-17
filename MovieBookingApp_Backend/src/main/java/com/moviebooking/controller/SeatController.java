package com.moviebooking.controller;

import com.moviebooking.entity.Seat;
import com.moviebooking.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class SeatController {
    @Autowired
    private SeatRepository seatRepository;

    @PostMapping("/addSeat")
    public Seat addSeat(@RequestBody Seat seat){
        return seatRepository.save(seat);
    }

}
