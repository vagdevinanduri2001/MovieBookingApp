package com.moviebooking.service;

import com.moviebooking.entity.Customer;
import com.moviebooking.entity.Ticket;

import java.util.List;

public interface TicketService {

    Ticket addTicket(Customer customer, Ticket ticket);

    List<Ticket> viewAllTickets(String userName);
}
