package com.moviebooking.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "movies")
public class Movie {

    @Transient
    public static final String SEQUENCE_NAME="movie_sequence";
    @Id
    private MovieId movieId;
    private int id;
    private double costOfTicket;
    private int noOfTicketsAllotted;
    private int noOfTicketsSold;
    private String ticketStatus="Book ASAP";

}
