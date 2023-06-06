import { Component, OnInit } from '@angular/core';
import { ApiServiceService } from '../services/api-service.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import movieId from '../model/movieId';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NGXLogger } from 'ngx-logger';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  constructor(
    private api: ApiServiceService,
    private router: Router,
    private snack : MatSnackBar,
    private logger : NGXLogger
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
        // this.logger.info('Movie found');
      }, (error) => {
        Swal.fire({
          title: 'No Movies found to display!',
          icon: 'error'
        });
        this.logger.error('No Movies found to display!');
      }
    )
  }

  public deleteMovie(movieName: string, theatreName: string) {
    this.api.deleteMovie(movieName, theatreName).subscribe(
      (response) => {
        this.getAllMovies();
        this.snack.open('Movie Deleted!','Ok');
        this.logger.info('Movie Deleted!');
      },
      (error) => {
        Swal.fire({
          title: error.error,
          icon: 'error'
        });
        this.logger.error(error.error);
      }
    )
  }

  public updateTicketStatus(id: movieId) {
    this.api.updateTicketStatus(id).subscribe(
      (response) => {
        this.getAllMovies();
        this.snack.open('Updated ticket Status','Ok');
        console.log(response);
        this.logger.info('Updated ticket Status')
      },
      (error) => {
        console.log(error);
        this.logger.error(error.error);
      }
    )
  }

}
