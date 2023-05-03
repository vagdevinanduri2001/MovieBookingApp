package com.moviebooking.repository;

import com.moviebooking.entity.Movie;
import com.moviebooking.entity.MovieId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MovieRepository extends MongoRepository<Movie, MovieId> {
    boolean existsByMovieId(MovieId movieId);
    List<Movie> findByMovieIdMovieName(String movieName);
    List<Movie> findByMovieIdTheatreName(String theatreName);
    Movie findByMovieIdMovieNameAndMovieIdTheatreName(String movieName, String theatreName);

}
