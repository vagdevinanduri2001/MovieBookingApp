package com.moviebooking.repository;

import com.moviebooking.entity.Customer;
import com.moviebooking.entity.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends MongoRepository<Ticket,Integer> {

    List<Ticket> findByCustomerUserName(String userName);
}
