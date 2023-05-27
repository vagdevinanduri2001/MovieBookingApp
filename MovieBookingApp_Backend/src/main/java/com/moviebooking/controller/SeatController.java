package com.moviebooking.controller;

import com.moviebooking.entity.MovieId;
import com.moviebooking.entity.Seat;
import com.moviebooking.repository.SeatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class SeatController {
    @Autowired
    private SeatRepository seatRepository;

    @PostMapping("/addSeat")
    public Seat addSeat(@RequestBody Seat seat){
        return seatRepository.save(seat);
    }

    Logger logger = LoggerFactory.getLogger(SeatController.class);

    @GetMapping("/getAllSeats/{movieName}/{theatreName}")
    public List<Seat> getSeatsByMovie(@PathVariable String movieName,@PathVariable String theatreName){
        MovieId movieId = new MovieId(movieName,theatreName);
        List<Seat> seats = seatRepository.findByMovieMovieId(movieId);
        logger.info("----------------"+seats.size()+"------------------");
        return seatRepository.findByMovieMovieId(movieId);
    }

}
