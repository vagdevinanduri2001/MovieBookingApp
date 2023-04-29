package com.moviebooking.repository;

import com.moviebooking.entity.Movie;
import com.moviebooking.entity.MovieId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@AutoConfigureDataMongo
public class MovieRepositoryTests {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    private Movie movie;
    private Movie movie1;
    private MovieId movieId;
    private MovieId movieId1;

    @BeforeEach
    void setUp() {
        movieId = new MovieId("moviex", "theatreName");
        movieId1 = new MovieId("movieName1", "theatreName1");
        movie = new Movie(movieId, 1, 100.00, 100, 1, "Available");
        movie1 = new Movie(movieId1, 2, 100.00, 100, 1, "Available");
        mongoTemplate.save(movie);
    }

    @Test
    public void existByMovieId_True(){
        boolean result = movieRepository.existsByMovieId(movie.getMovieId());
        assertThat(result).isEqualTo(true);
    }
    @Test
    public void existByMovieId_False(){
        boolean result = movieRepository.existsByMovieId(movie1.getMovieId());
        assertThat(result).isEqualTo(false);
    }
    @Test
    public void findByMovieIdMovieName_returnsObject(){
        List<Movie> result = movieRepository.findByMovieIdMovieName(movieId.getMovieName());
        assertThat(result).isEqualTo(List.of(movie));
    }

    @Test
    public void findByMovieIdMovieName_returnsNull(){
        List<Movie> result = movieRepository.findByMovieIdMovieName(movieId1.getMovieName());
        assertThat(result).isEqualTo(List.of());
    }
//    @Test
//    public void findallTest(){
//        List<Movie> movies = movieRepository.findAll();
//        assertThat(1).isEqualTo(movies.size());
//    }
}
