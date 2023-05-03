package com.moviebooking.service;

import com.moviebooking.entity.*;
import com.moviebooking.exception.CommonException;
import com.moviebooking.exception.MovieNotFoundException;
import com.moviebooking.repository.MovieRepository;
import com.moviebooking.repository.SeatRepository;
import com.moviebooking.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class TicketServiceTests {
    @InjectMocks
    private TicketServiceImpl ticketService;
    @Mock
    private SeatRepository seatRepository;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private SequenceGenerator sequenceGenerator;
    @Mock
    private TicketRepository ticketRepository;
    private Ticket ticket;
    private Movie movie;
    private MovieId movieId;

    private Seat seat1;

    @BeforeEach
    void setUp(){
        movieId = new MovieId("movieName", "theatreName");
        movie = new Movie(movieId, 1, 100.00, 100, 1, "Available");
        seat1 = new Seat(1, 2, "Balcony", SeatStatus.Available, 100.0, movie);
        //Seat seat2 = new Seat(1, 2, "Balcony", SeatStatus.Available, 100.0, movie);
        List<Seat> seats = List.of(seat1);
        ticket = new Ticket(1,"movieName","theatreName",100.00,1,seats);
    }

    @Test
    public void addMovie_Success(){
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(seatRepository.findBySeatNumberAndMovieId(2,1)).thenReturn(seat1);
        ticketService.addTicket(ticket);
        verify(movieRepository,times(1)).save(movie);
    }

    @Test
    public void addMovie_CommonException_SeatBooked(){
        seat1.setSeatStatus(SeatStatus.Booked);
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(seatRepository.findBySeatNumberAndMovieId(2,1)).thenReturn(seat1);
        CommonException e = assertThrows(CommonException.class,()->{ticketService.addTicket(ticket);});
        assertEquals("2 is already booked.Please select a new seat",e.getMessage());
    }

    @Test
    public void addMovie_CommonException(){
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        movie.setNoOfTicketsSold(movie.getNoOfTicketsAllotted());
        CommonException e = assertThrows(CommonException.class,()->{ticketService.addTicket(ticket);});
        assertEquals("Tickets not available",e.getMessage());
    }
    @Test
    public void addMovie_SizeCommonException(){
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        ticket.setNoOfTickets(2);
        CommonException e = assertThrows(CommonException.class,()->{ticketService.addTicket(ticket);});
        assertEquals("No.of seats selected should be equal size of seats list",e.getMessage());
    }

    @Test
    public void addMovie_MovieNotFoundException(){
        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());
        MovieNotFoundException e = assertThrows(MovieNotFoundException.class,()->{ticketService.addTicket(ticket);});
        assertEquals("Movie not found",e.getMessage());
    }
}
