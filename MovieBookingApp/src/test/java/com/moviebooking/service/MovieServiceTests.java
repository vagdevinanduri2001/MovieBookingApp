package com.moviebooking.service;

import com.moviebooking.entity.Movie;
import com.moviebooking.entity.MovieId;
import com.moviebooking.entity.Seat;
import com.moviebooking.entity.SeatStatus;
import com.moviebooking.exception.CommonException;
import com.moviebooking.exception.MovieAlreadyExistsException;
import com.moviebooking.exception.MovieNotFoundException;
import com.moviebooking.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class MovieServiceTests {
    @InjectMocks
    private MovieServiceImpl movieService;

    @Mock
    private SeatService seatService;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private KafkaTemplate kafkaTemplate;
    @Mock
    private SequenceGenerator sequenceGenerator;
    private Movie movie;
    private Movie movie1;
    private MovieId movieId;
    private Seat seat;

    @BeforeEach
    void setUp() {
        movieId = new MovieId("movieName", "theatreName");
        movie = new Movie(movieId, 1, 100.00, 100, 1, "Available");
        movie1 = new Movie(movieId, 1, 100.00, 0, 1, "Available");
        seat = new Seat(1, 2, "Balcony", SeatStatus.Available, 100.0, movie);
    }

    @Test
    public void addMovieTest_Success() {
        when(movieRepository.save(movie)).thenReturn(movie);
        Movie movie2 = movieService.addMovie(movie);
        assertThat(movie2).isNotNull();
    }

    @Test
    public void addMovieTest_Exception() {
        when(movieRepository.existsByMovieId(movieId)).thenReturn(true);
        MovieAlreadyExistsException e = assertThrows(MovieAlreadyExistsException.class, () -> {
            movieService.addMovie(movie);
        });
        verify(movieRepository, never()).save(movie);
        assertEquals("Movie already exists by Id", e.getMessage());
    }

    @Test
    public void addMovieTest_CommonException() {
        CommonException e = assertThrows(CommonException.class, () -> {
            movieService.addMovie(movie1);
        });
        verify(movieRepository, never()).save(movie);
        assertEquals("Number of tickets allotted cannot be less than or Equal to ZERO", e.getMessage());
    }

    @Test
    public void updateMovie_Success() {
        when(movieRepository.existsByMovieId(movieId)).thenReturn(true);
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        Movie movie2 = new Movie(movieId, 1, 200.00, 100, 1, "Available");
        when(movieRepository.save(any(Movie.class))).thenReturn(movie2);
        Movie movie3 = movieService.updateMovie(movie2);
        assertEquals(movie3.getCostOfTicket(), movie.getCostOfTicket());
    }

    @Test
    public void updateMovie_Exception() {
        when(movieRepository.existsByMovieId(movieId)).thenReturn(false);
        MovieNotFoundException e = assertThrows(MovieNotFoundException.class, () -> {
            movieService.updateMovie(movie);
        });
        verify(movieRepository, never()).save(movie);
        assertEquals("Movie not found to update", e.getMessage());
    }

    @Test
    public void updateTicketStatus_NotUpdated(){
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        movieService.updateTicketStatus(movieId);
        assertEquals("Book ASAP",movie.getTicketStatus());
    }

    @Test
    public void updateTicketStatus_Updated(){
        movie.setNoOfTicketsSold(movie.getNoOfTicketsAllotted());
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        movieService.updateTicketStatus(movieId);
        assertEquals("SOLD OUT",movie.getTicketStatus());

    }

    @Test
    public void updateTicketStatus_Exception(){
        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());
        MovieNotFoundException e = assertThrows(MovieNotFoundException.class,()->{movieService.updateTicketStatus(movieId);});
        assertEquals("Movie not found",e.getMessage());
    }

    @Test
    public void getAllMoviesTest() throws MovieNotFoundException {
        MovieId movieComp1 = new MovieId("URI", "Carnival");
        Movie movie2 = new Movie(movieComp1, 1, 100.00, 100, 1, "Available");

        when(movieRepository.findAll()).thenReturn(List.of(movie, movie2));
        List<Movie> movieList = movieService.getAllMovies();

        assertThat(movieList).isNotNull();
        assertThat(movieList.size()).isEqualTo(2);

    }

    @Test
    public void searchMovieByMovieNameTest(){
        MovieId movieComp1 = new MovieId("movieName", "Carnival");
        Movie movie2 = new Movie(movieComp1, 1, 100.00, 100, 1, "Available");

        when(movieRepository.findByMovieIdMovieName("movieName")).thenReturn(List.of(movie, movie2));
        List<Movie> movieList = movieService.searchMoviesByMovieName("movieName");
        assertThat(movieList).isNotNull();
        assertThat(movieList.size()).isEqualTo(2);
    }
    @Test
    public void searchMovieByTheatreNameTest(){
        MovieId movieComp1 = new MovieId("movieName", "Carnival");
        Movie movie2 = new Movie(movieComp1, 1, 100.00, 100, 1, "Available");

        when(movieRepository.findByMovieIdTheatreName("theatreName")).thenReturn(List.of(movie, movie2));
        List<Movie> movieList = movieService.searchMoviesByTheatreName("theatreName");
        assertThat(movieList).isNotNull();
        assertThat(movieList.size()).isEqualTo(2);
    }
    @Test
    public void searchMovieByMovieIdTest(){
        MovieId movieComp1 = new MovieId("movieName", "Carnival");
        Movie movie2 = new Movie(movieComp1, 1, 100.00, 100, 1, "Available");

        when(movieRepository.findByMovieIdMovieNameAndMovieIdTheatreName("movieName","theatreName")).thenReturn(movie);
        Movie result = movieService.searchByMovieId("movieName","theatreName");
        assertThat(result).isNotNull();
    }

    @Test
    public void deleteMovie_Success(){
        when(movieRepository.existsByMovieId(movieId)).thenReturn(true);
        willDoNothing().given(movieRepository).deleteById(movieId);
        movieService.deleteMovieById(movieId);
        verify(movieRepository,times(1)).deleteById(movieId);
    }

    @Test
    public void deleteMovieId_Exception(){
        when(movieRepository.existsByMovieId(movieId)).thenReturn(false);
        MovieNotFoundException e = assertThrows(MovieNotFoundException.class,()->{movieService.deleteMovieById(movieId);});
        assertEquals("Movie does not exist to delete",e.getMessage());
        verify(movieRepository,never()).deleteById(movieId);
    }


}
