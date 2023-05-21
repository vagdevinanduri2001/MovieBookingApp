//package com.moviebooking.service;
//
//import com.moviebooking.entity.Movie;
//import com.moviebooking.entity.MovieId;
//import com.moviebooking.entity.Seat;
//import com.moviebooking.repository.SeatRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class KafkaListener {
//
//    @Autowired
//    private SeatRepository seatRepository;
//
//    @org.springframework.kafka.annotation.KafkaListener(topics = "my-topic", groupId = "movie-group")
//    public void listenToTopic(Movie movie){
//
//        MovieId movieId = movie.getMovieId();
//
//        List<Seat> seats = seatRepository.findByMovieMovieId(movieId);
//        for(Seat seat : seats){
//            seat.setMovie(movie);
//        }
//        seatRepository.saveAll(seats);
//
//    }
//}
