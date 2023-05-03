package com.moviebooking.controller;

import com.moviebooking.entity.Movie;
import com.moviebooking.entity.MovieId;
import com.moviebooking.exception.CommonException;
import com.moviebooking.exception.MovieAlreadyExistsException;
import com.moviebooking.exception.MovieNotFoundException;
import com.moviebooking.service.MovieServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/moviebooking")
public class MovieController {
    @Autowired
    private MovieServiceImpl movieService;
Logger logger = LoggerFactory.getLogger(MovieController.class);
    @PostMapping("/addMovie")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<?> addMovie(@Valid @RequestBody Movie movie) {
        try {
            Movie movie1 = movieService.addMovie(movie);
            logger.info("----------------Movie created successfully!------------------");
            return new ResponseEntity<>("Movie created successfully!", HttpStatus.CREATED);
        } catch (MovieAlreadyExistsException e) {
            logger.info("----------------"+e.getMessage()+"------------------");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }catch (CommonException e) {
            logger.info("----------------"+e.getMessage()+"------------------");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }catch (Exception e) {
            logger.info("----------------"+e.getMessage()+"------------------");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @PutMapping("/updateMovie")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<?> updateMovie(@RequestBody Movie movie){
        try{
            movieService.updateMovie(movie);
            logger.info("----------------Movie updated successfully!------------------");
            return new ResponseEntity<>("Movie updated successfully!",HttpStatus.OK);
        }catch (MovieNotFoundException e){
            logger.info("----------------"+e.getMessage()+"------------------");
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<?> updateTicketStatus(@RequestBody MovieId movieId){
        try{
            movieService.updateTicketStatus(movieId);
            logger.info("----------------Updated Ticket Status------------------");
            return new ResponseEntity<>("Updated Ticket Status",HttpStatus.OK);
        }catch (MovieNotFoundException e){
            logger.info("----------------"+e.getMessage()+"------------------");
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllMovies() {
        List<Movie> movie = movieService.getAllMovies();
        if(movie.size() != 0){
            logger.info("----------------Total "+movie.size()+" Movies found------------------");
            return new ResponseEntity<>(movie,HttpStatus.FOUND);
        }else{
            logger.info("----------------Movies not FOUND...------------------");
            return new ResponseEntity<>("Movies not FOUND...",HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/search/movie/{movieName}")
    public ResponseEntity<?> searchMovieByMovieName(@PathVariable String movieName){
        List<Movie> movie = movieService.searchMoviesByMovieName(movieName);
        if(movie.size() != 0){
            logger.info("----------------"+movie.size()+" Movies found with given movie name------------------");
            return new ResponseEntity<>(movie,HttpStatus.FOUND);
        }else{
            logger.info("----------------Movies not FOUND...please check movie name------------------");
            return new ResponseEntity<>("Movies not FOUND...please check movie name",HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/search/theatre/{theatreName}")
    public ResponseEntity<?> searchMovieByTheatreName(@PathVariable String theatreName){
        List<Movie> movie = movieService.searchMoviesByTheatreName(theatreName);
        if(movie.size() != 0){
            logger.info("----------------"+movie.size()+" Movies found with given theatre name------------------");
            return new ResponseEntity<>(movie,HttpStatus.FOUND);
        }else{
            logger.info("----------------Movies not FOUND...please check theatre name------------------");
            return new ResponseEntity<>("Movies not FOUND...please check theatre name",HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/search/{movieName}/{theatreName}")
    public ResponseEntity<?> searchMovieByMovieAndTheatre(@PathVariable String movieName,@PathVariable String theatreName){
        Movie movie = movieService.searchByMovieId(movieName,theatreName);
        if(movie!= null){
            logger.info("----------------Movie found with given movie name and theatre name------------------");
            return new ResponseEntity<>(movie,HttpStatus.FOUND);
        }else{
            logger.info("----------------Movies not FOUND...please check movie or theatre name------------------");
            return new ResponseEntity<>("Movies not FOUND...please check movie or theatre name",HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("delete/{movieName}/{theatreName}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<?> deleteMovieById(@PathVariable String movieName,
                                             @PathVariable String theatreName){
        MovieId movieId = new MovieId(movieName,theatreName);
        try {
            movieService.deleteMovieById(movieId);
            logger.info("----------------Movie Deleted!------------------");
            return new ResponseEntity<>("Movie Deleted!", HttpStatus.OK);
        }catch(MovieNotFoundException e){
            logger.info("----------------"+e.getMessage()+"------------------");
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }catch (Exception e){
            logger.info("----------------"+e.getMessage()+"------------------");
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_ACCEPTABLE);
        }
    }

}

