package com.moviebooking.controller;

import com.moviebooking.entity.Ticket;
import com.moviebooking.exception.CommonException;
import com.moviebooking.exception.MovieNotFoundException;
import com.moviebooking.service.TicketServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/moviebooking")
public class TicketController {
    @Autowired
    private TicketServiceImpl ticketService;

    Logger logger = LoggerFactory.getLogger(TicketController.class);

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<?> bookTicket(@RequestBody Ticket ticket) {
        try {
            ticketService.addTicket(ticket);
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
