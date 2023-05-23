import { Component, OnInit } from '@angular/core';
import { ApiServiceService } from '../services/api-service.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import movieId from '../model/movieId';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  constructor(
    private api: ApiServiceService,
    private router: Router
  ) { }
  ngOnInit(): void {
    this.getAllMovies();
  }

  displayedColumns: string[] = ['id', 'movieName', 'theatreName', 'costOfTicket', 'noOfTicketsAllotted', 'noOfTicketsSold', 'ticketStatus', 'star'];
  allMovies: any = [];

  public getAllMovies(searchKeyword: string = "") {
    this.api.search(searchKeyword).subscribe(
      (response) => {
        this.allMovies = response;
      }, (error) => {
        Swal.fire({
          title: 'No Movies found to display!',
          icon: 'error'
        });
      }
    )
  }

  public deleteMovie(movieName: string, theatreName: string) {
    this.api.deleteMovie(movieName, theatreName).subscribe(
      (response) => {
        this.getAllMovies();
      },
      (error) => {
        Swal.fire({
          title: error.error,
          icon: 'error'
        });
      }
    )
  }

  public updateTicketStatus(id: movieId) {
    this.api.updateTicketStatus(id).subscribe(
      (response) => {
        console.log(response);
      },
      (error) => {
        console.log(error);
      }
    )
  }

}
