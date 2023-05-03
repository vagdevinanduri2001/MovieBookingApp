package com.moviebooking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviebooking.entity.*;
import com.moviebooking.exception.CommonException;
import com.moviebooking.exception.MovieNotFoundException;
import com.moviebooking.service.TicketServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest
public class TicketControllerTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();
    @InjectMocks
    private TicketController ticketController;
    @Mock
    private TicketServiceImpl ticketService;
    private Ticket ticket;
    private Movie movie;
    private MovieId movieId;
    private Seat seat1;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();
        movieId = new MovieId("movieName", "theatreName");
        movie = new Movie(movieId, 1, 100.00, 100, 1, "Available");
        seat1 = new Seat(1, 2, "Balcony", SeatStatus.Available, 100.0, movie);
        List<Seat> seats = List.of(seat1);
        ticket = new Ticket(1,"movieName","theatreName",100.00,1,seats);
    }
    @Test
    public void bookTicketTest_Success() throws Exception {
        ResponseEntity<String> response = ResponseEntity.ok("Ticket booked!");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/moviebooking/add")
                .header("Authorization", "token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticket))).andReturn();
        assertEquals(200, result.getResponse().getStatus());
        assertEquals(response.getBody(), result.getResponse().getContentAsString());
    }
    @Test
    public void bookTicketTest_MovieNotFoundException() throws Exception {
        ResponseEntity<String> response = ResponseEntity.ok("Movie not found");
        Mockito.when(ticketService.addTicket(any(Ticket.class))).thenThrow(new MovieNotFoundException("Movie not found"));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/moviebooking/add")
                .header("Authorization", "token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticket))).andReturn();
        assertEquals(406, result.getResponse().getStatus());
        assertEquals(response.getBody(), result.getResponse().getContentAsString());
    }
    @Test
    public void bookTicketTest_CommonException() throws Exception {
        ResponseEntity<String> response = ResponseEntity.ok("Tickets not available");
        Mockito.when(ticketService.addTicket(any(Ticket.class))).thenThrow(new CommonException("Tickets not available"));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/moviebooking/add")
                .header("Authorization", "token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticket))).andReturn();
        assertEquals(406, result.getResponse().getStatus());
        assertEquals(response.getBody(), result.getResponse().getContentAsString());
    }
}
