package com.moviebooking.service;

import com.moviebooking.entity.*;
import com.moviebooking.exception.CommonException;
import com.moviebooking.exception.MovieNotFoundException;
import com.moviebooking.repository.MovieRepository;
import com.moviebooking.repository.SeatRepository;
import com.moviebooking.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    private SeatRepository seatRepository;

    @Override
    public Ticket addTicket(Ticket ticket) {
        MovieId movieId = new MovieId(ticket.getMovieName(), ticket.getTheatreName());
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (movie.isPresent()) {
            int availableTickets = movie.get().getNoOfTicketsAllotted() - movie.get().getNoOfTicketsSold();

            if (availableTickets > ticket.getNoOfTickets()) {
                List<Seat> seatss = ticket.getSeats();
                List<Seat> seats = new ArrayList<>();
                for(Seat s:seatss){
                	Seat seat = seatRepository.findBySeatNumberAndMovieId(s.getSeatNumber(),movie.get().getId());
//                    Seat seat = seatRepository.findBySeatNumberAndMovieId(s.getSeatNumber(),s.getMovie().getId());
                    seats.add(seat);
                }
                double cost=0;
                for(Seat s:seats){
                    if(s.getSeatStatus().equals(SeatStatus.Booked)){
                        throw new CommonException( s.getSeatNumber()+" is already booked.Please select a new seat");
                    }else{
                    s.setSeatStatus(SeatStatus.Booked);
                    cost += s.getCost();
                    seatRepository.save(s);
                }
                }
                ticket.setTicketId(sequenceGenerator.getSequenceNumber(Ticket.SEQUENCE_NAME));
                ticket.setTotalCost((ticket.getNoOfTickets() * movie.get().getCostOfTicket())+cost);
                movie.get().setNoOfTicketsSold(movie.get().getNoOfTicketsSold() + ticket.getNoOfTickets());
                movieRepository.save(movie.get());
                return ticketRepository.save(ticket);
            } else {
                throw new CommonException("Tickets not available");
            }
        } else {
            throw new MovieNotFoundException("Movie not found");
        }

    }

//    public boolean checkSeatStatus(Seat seat){
//        if(seat.getSeatStatus().equals(SeatStatus.Available)){
//            return true;
//        }
//        else{
//            return false;
//        }
//    }
}
