package com.moviebooking.service;

import com.moviebooking.entity.Customer;
import com.moviebooking.entity.Ticket;

public interface TicketService {
    Ticket addTicket(Customer customer, Ticket ticket);
}
