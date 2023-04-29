package com.moviebooking.repository;

import com.moviebooking.entity.MovieId;
import com.moviebooking.entity.Seat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends MongoRepository<Seat,Integer> {

    @Query(value = "{$and:[{'seatNumber':?0},{'movie.id':?1}] }")
    Seat findBySeatNumberAndMovieId(int seatNumber, int id);

    List<Seat> findByMovieMovieId(MovieId movieId);
}