import { Component, OnInit } from '@angular/core';
import { ApiServiceService } from '../services/api-service.service';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-book-ticket',
  templateUrl: './book-ticket.component.html',
  styleUrls: ['./book-ticket.component.css']
})
export class BookTicketComponent implements OnInit {

  movieName = '';
  theatreName = '';
  movieCost = 0;
  noOfTickets!: number;
  balcony :number = 0;
  orchestra :number = 0;
  totalSeats:any = [];
  rows = 0;
  cols = 10;


  ngOnInit(): void {
    this.movieName = this.auth.getMovie().movieId.movieName;
    this.theatreName = this.auth.getMovie().movieId.theatreName;
    this.movieCost = this.auth.getMovie().costOfTicket;
    this.getAllSeatsByMovie();
    this.rows = this.totalSeats.length;
  }

  constructor(
    private api: ApiServiceService,
    private auth: AuthService
  ) {}

  ticket={
    "movieName":this.movieName,
    "theatreName":this.theatreName,
  }

  public getAllSeatsByMovie(){
    this.api.getSeatsByMovie(this.movieName,this.theatreName).subscribe(
      (response: any)=>{
        this.totalSeats = response;
      },
      (error: any)=>{
        console.log(error);
      }
    )
  }
  

  bookTickets(){
    this.api.bookTicket(this.ticket).subscribe(
      (response)=>{
        
      },
      (error)=>{

      }
    )
  }

}
