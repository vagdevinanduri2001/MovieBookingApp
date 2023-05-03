package com.moviebooking.service;

import com.moviebooking.entity.Movie;
import com.moviebooking.entity.MovieId;
import com.moviebooking.entity.Seat;
import com.moviebooking.entity.SeatStatus;
import com.moviebooking.exception.CommonException;
import com.moviebooking.exception.MovieAlreadyExistsException;
import com.moviebooking.exception.MovieNotFoundException;
import com.moviebooking.repository.MovieRepository;
import com.moviebooking.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService{
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private SeatService seatService;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private KafkaTemplate<String, Movie> kafkaTemplate;
    @Autowired
    private SequenceGenerator sequenceGenerator;
    @Override
    public Movie addMovie(Movie movie) {

        if(movieRepository.existsByMovieId(movie.getMovieId())){
            throw new MovieAlreadyExistsException("Movie already exists by Id");
        } else if (movie.getNoOfTicketsAllotted()<=0) {
            throw new CommonException("Number of tickets allotted cannot be less than or Equal to ZERO");
        } else {
            movie.setId(sequenceGenerator.getSequenceNumber(Movie.SEQUENCE_NAME));
            int ti = movie.getNoOfTicketsAllotted();
            for(int i=ti;i>0;i--){
                Seat seat = new Seat();
                seat.setSeatId(sequenceGenerator.getSequenceNumber(Seat.SEQUENCE_NAME));
                seat.setSeatNumber(i);
                seat.setSeatType();
                seat.setSeatStatus(SeatStatus.Available);
                seat.setCost();
                seat.setMovie(movie);
                seatService.addSeat(seat);
            }
            return movieRepository.save(movie);
        }
    }

    @Override
    public Movie updateMovie(Movie movie) {
        if(movieRepository.existsByMovieId(movie.getMovieId())){
            Optional<Movie> movie1 =  movieRepository.findById(movie.getMovieId());
            movie1.get().setCostOfTicket(movie.getCostOfTicket());
            return movieRepository.save(movie1.get());
        }else{
            throw new MovieNotFoundException("Movie not found to update");
        }
    }

    @Override
    public Movie updateTicketStatus(MovieId movieId) {
        Optional<Movie> movie = movieRepository.findById(movieId);
        if(movie.isPresent()){
            if(movie.get().getNoOfTicketsAllotted()<= movie.get().getNoOfTicketsSold()){
                movie.get().setTicketStatus("SOLD OUT");
            }
            else{
                movie.get().setTicketStatus("Book ASAP");
            }
            kafkaTemplate.send("my-topic",movie.get());
            return movieRepository.save(movie.get());
        }else{
            throw new MovieNotFoundException("Movie not found");
        }
    }


    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public List<Movie> searchMoviesByMovieName(String movieName) {

        return movieRepository.findByMovieIdMovieName(movieName);
    }

    @Override
    public List<Movie> searchMoviesByTheatreName(String theatreName) {
        return movieRepository.findByMovieIdTheatreName(theatreName);
    }
    @Override
    public Movie searchByMovieId(String movieName,String theatreName){
        return movieRepository.findByMovieIdMovieNameAndMovieIdTheatreName(movieName,theatreName);
    }

    @Override
    public void deleteMovieById(MovieId movieId) {
        if(movieRepository.existsByMovieId(movieId)) {
            List<Seat> seats = seatRepository.findByMovieMovieId(movieId);
            movieRepository.deleteById(movieId);
            seatRepository.deleteAll(seats);
        }else{
            throw new MovieNotFoundException("Movie does not exist to delete");
        }
    }
}
