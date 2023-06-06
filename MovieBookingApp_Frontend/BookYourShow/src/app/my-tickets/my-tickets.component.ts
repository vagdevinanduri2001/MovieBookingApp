import { Component, OnInit } from '@angular/core';
import { ApiServiceService } from '../services/api-service.service';
import { Movie } from '../model/movie';
import movieId from '../model/movieId';
import Swal from 'sweetalert2';
import { NGXLogger } from 'ngx-logger';

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
    private api : ApiServiceService,
    private logger : NGXLogger
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
errorMessage='';
  public viewTickets(){
    this.api.viewTickets().subscribe(
      (response:any)=>{
        // console.log(response);
        this.myTickets = response;
        console.log(this.myTickets);
        // this.myTickets.forEach((ticket: any) => {
        //   this.seats = ticket.seats;
        //   console.log(this.seats[0].movie.imageUrl);
        // });
        this.logger.info('tickets found');
        },
      (error:any)=>{
        this.errorMessage = error.error;
        this.logger.error(error.error);
      }
    );
  }

}
