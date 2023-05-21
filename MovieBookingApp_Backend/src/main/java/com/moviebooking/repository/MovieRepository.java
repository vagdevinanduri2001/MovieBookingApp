package com.moviebooking.repository;

import com.moviebooking.entity.Movie;
import com.moviebooking.entity.MovieId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MovieRepository extends MongoRepository<Movie, MovieId> {
    boolean existsByMovieId(MovieId movieId);
//    @Query(collation = "{'locale': 'en_US', 'strength': 2}")
    List<Movie> findByMovieIdMovieName(String movieName);
    @Query("{$or:[{\"_id.movieName\": { $regex: ?0 , $options: 'i'}}, {\"_id.theatreName\": { $regex: ?0, $options: 'i' }}]}")
    List<Movie> findByMovieNameOrTheatreName(String searchKeyword);
//    @Query(collation = "{'locale': 'en_US', 'strength': 2}")
    List<Movie> findByMovieIdTheatreName(String theatreName);
//    @Query(collation = "{'locale': 'en_US', 'strength': 2}")
    Movie findByMovieIdMovieNameAndMovieIdTheatreName(String movieName, String theatreName);

}
