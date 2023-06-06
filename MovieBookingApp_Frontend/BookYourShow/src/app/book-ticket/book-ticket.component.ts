import { Component, OnInit } from '@angular/core';
import { ApiServiceService } from '../services/api-service.service';
import { AuthService } from '../services/auth.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { NGXLogger } from 'ngx-logger';

@Component({
  selector: 'app-book-ticket',
  templateUrl: './book-ticket.component.html',
  styleUrls: ['./book-ticket.component.css']
})
export class BookTicketComponent implements OnInit {

  movieName = '';
  theatreName = '';
  movieCost = 0;
  totalSeats: any = [];
  rows = 0;
  cols = 15;
  seatArray: any[][] = []
  selectedSeats:any[] = [];
ticket:any = {}

  ngOnInit(): void {
    this.movieName = this.auth.getMovie().movieId.movieName;
    this.theatreName = this.auth.getMovie().movieId.theatreName;
    this.movieCost = this.auth.getMovie().costOfTicket;
    this.getAllSeatsByMovie();
  } 

  constructor(
    private api: ApiServiceService,
    private auth: AuthService,
    private router : Router,
    private logger : NGXLogger
  ) { 
    this.ticket = {
      "movieName": this.auth.getMovie().movieId.movieName,
      "theatreName": this.auth.getMovie().movieId.theatreName,
      "seats":this.selectedSeats,
      "noOfTickets":0    
    }
  }

  

  public getAllSeatsByMovie() {
    this.api.getSeatsByMovie(this.movieName, this.theatreName).subscribe(
      (response: any) => {
        this.totalSeats = response;
        const quo = Math.floor(this.totalSeats.length/15);
        const rem = this.totalSeats.length%15;
        this.rows = quo+rem;
        this.seatArray = this.generateSeatMap(this.rows,this.cols);
      },
      (error: any) => {
        console.log(error);
      }
    )
  }

  toggleSeatSelection(seatInp:any): void {
    if (seatInp.seatStatus=="Available") {
      seatInp.seatStatus = "Selected";
      this.selectedSeats.push(seatInp);
      // this.noOfTickets = this.selectedSeats.length;
      console.log(this.selectedSeats);
    }else if(seatInp.seatStatus=="Selected"){
      seatInp.seatStatus = "Available";
      const index = this.selectedSeats.findIndex(s=>s.seatNumber ===seatInp.seatNumber);
      this.selectedSeats.splice(index,1);
      // this.noOfTickets = this.selectedSeats.length;
      // console.log(this.noOfTickets);
    }
  }


  bookTickets() {
    this.ticket.noOfTickets = this.selectedSeats.length;
    this.api.bookTicket(this.ticket).subscribe(
      (response) => {
        const words = response.split(' ');
        const ticketId:number = +words[words.length-2];
        const cost = 0;
        this.api.getTicketById(ticketId).subscribe(
          (data:any)=>{
            Swal.fire({
              title:"Thank you for using our application to book tickets",
              text:response+" And Total cost of ticket is "+data.totalCost,
              icon:'success',
              footer: 'You can check total ticket details in MyTickets page'
            });
          }
        )
        this.router.navigate(['/my-tickets']);
        this.logger.info('Ticket booked');
      },
      (error) => {
        Swal.fire({
          title:error.error,
          icon:'warning'
        });
        this.logger.error(error.error);
      }
    )
  }

  generateSeatMap(rows: number, seatsPerRow: number): any[][] {
    let len:number = this.totalSeats.length-1;
    console.log(len);
    const seatList: any[][] = [];
    for (let i = 0; i < rows; i++) {
      const element = [];
      for (let j = 0; j < seatsPerRow; j++) {
        if (len >= 0) {
          // console.log(this.totalSeats[len]);
          element.push(this.totalSeats[len]);
          len--;
        }else{
          break;
        }
      }
      seatList.push(element);
    }
    return seatList;
  }

}
