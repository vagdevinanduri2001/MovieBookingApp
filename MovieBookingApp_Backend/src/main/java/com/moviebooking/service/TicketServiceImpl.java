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
    public Ticket addTicket(Customer customer, Ticket ticket) {
        ticket.setCustomer(customer);
        MovieId movieId = new MovieId(ticket.getMovieName(), ticket.getTheatreName());
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (movie.isPresent()) {
            int availableTickets = movie.get().getNoOfTicketsAllotted() - movie.get().getNoOfTicketsSold();
        if(ticket.getNoOfTickets()==ticket.getSeats().size()) {
            if (availableTickets > ticket.getNoOfTickets()) {
                List<Seat> seatss = ticket.getSeats();
                List<Seat> seats = new ArrayList<>();
                for (Seat s : seatss) {
                    Seat seat = seatRepository.findBySeatNumberAndMovieId(s.getSeatNumber(), movie.get().getId());
                    seats.add(seat);
                }
                ticket.setSeats(seats);
                double cost = 0;
                for (Seat s : seats) {
                    if (s.getSeatStatus().equals(SeatStatus.Booked)) {
                        throw new CommonException(s.getSeatNumber() + " is already booked.Please select a new seat");
                    } else {
                        s.setSeatStatus(SeatStatus.Booked);
                        cost += s.getCost();
                        seatRepository.save(s);
                    }
                }
                ticket.setTicketId(sequenceGenerator.getSequenceNumber(Ticket.SEQUENCE_NAME));
                ticket.setTotalCost((ticket.getNoOfTickets() * movie.get().getCostOfTicket()) + cost);
                movie.get().setNoOfTicketsSold(movie.get().getNoOfTicketsSold() + ticket.getNoOfTickets());
                movieRepository.save(movie.get());
                return ticketRepository.save(ticket);
            } else {
                throw new CommonException("Tickets not available");
            }
        }else{
            throw new CommonException("No.of seats selected should be equal size of seats list");
        }
        } else {
            throw new MovieNotFoundException("Movie not found");
        }

    }

    @Override
    public List<Ticket> viewAllTickets(String userName){
        return ticketRepository.findByCustomerUserName(userName);
    }



}
