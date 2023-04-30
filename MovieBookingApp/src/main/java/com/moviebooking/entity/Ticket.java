package com.moviebooking.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "movieName cannot be null")
    private String movieName;
    @NotNull(message = "theatreName cannot be null")
    private String theatreName;

    private double totalCost;
    @NotNull(message = "noOfTickets cannot be null")
    private int noOfTickets;
    @NotNull(message = "seats cannot be null")
    private List<Seat> seats;
}
