package com.moviebooking.controller;

import com.moviebooking.entity.Customer;
import com.moviebooking.entity.Ticket;
import com.moviebooking.exception.CommonException;
import com.moviebooking.exception.MovieNotFoundException;
import com.moviebooking.repository.CustomerRepository;
import com.moviebooking.service.JwtService;
import com.moviebooking.service.TicketServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/moviebooking")
public class TicketController {
    @Autowired
    private TicketServiceImpl ticketService;
    @Autowired
            private JwtService jwtService;
    @Autowired
            private CustomerRepository customerRepository;

    Logger logger = LoggerFactory.getLogger(TicketController.class);

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<?> bookTicket(@RequestHeader("Authorization") String token, @RequestBody Ticket ticket) {
        try {
            String userName = jwtService.extractUsername(token.substring(7));
            Optional<Customer> customer = customerRepository.findByUserName(userName);
            ticketService.addTicket(customer.get(),ticket);
            logger.info("----------------Ticket booked!------------------");
            return new ResponseEntity<>("Ticket booked!", HttpStatus.OK);
        }catch (MovieNotFoundException e){
            logger.info("----------------"+e.getMessage()+"------------------");
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_ACCEPTABLE);
        }catch (CommonException e){
            logger.info("----------------"+e.getMessage()+"------------------");
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
