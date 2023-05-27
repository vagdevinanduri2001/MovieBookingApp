import { Component, OnInit } from '@angular/core';
import { ApiServiceService } from '../services/api-service.service';
import { Movie } from '../model/movie';
import movieId from '../model/movieId';

@Component({
  selector: 'app-my-tickets',
  templateUrl: './my-tickets.component.html',
  styleUrls: ['./my-tickets.component.css']
})
export class MyTicketsComponent implements OnInit{

  myTickets:any =[];
  seats:any = [];
  breakpoint!: number;

  constructor(
    private api : ApiServiceService
  ){}

  ngOnInit(): void {
    this.viewTickets();
    if (window.innerWidth <= 768) {
      this.breakpoint = 1;
    } else if (window.innerWidth <= 1200) {
      this.breakpoint = 3;
    } else {
      this.breakpoint = 5;
    }
  }
  onResize(event: any) {
    if (window.innerWidth <= 768) {
      this.breakpoint = 1;
    } else if (window.innerWidth <= 1200) {
      this.breakpoint = 3;
    } else {
      this.breakpoint = 5;
    }
   }

  public viewTickets(){
    this.api.viewTickets().subscribe(
      (response:any)=>{
        console.log(response);
        this.myTickets = response;
        this.myTickets.forEach((ticket: any) => {
          this.seats = ticket.seats;
          console.log(this.seats);
        });
        },
      (error:any)=>{
        console.log(error);
      }
    );
  }

}
