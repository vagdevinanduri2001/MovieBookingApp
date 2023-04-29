package com.moviebooking.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Transient
    public static final String SEQUENCE_NAME="ticket_sequence";
    @Id
    private int ticketId;

    //public enum ShowName {MorningShow,Matinee,FirstShow,SecondShow} ;
    private String movieName;
    private String theatreName;
    private double totalCost;
    private int noOfTickets;
    private List<Seat> seats;
}
