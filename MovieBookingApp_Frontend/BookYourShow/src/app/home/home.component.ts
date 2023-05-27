import { Component, OnInit } from '@angular/core';
import { ApiServiceService } from '../services/api-service.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  allMovies: any = [];
  isHandset: boolean = false;
  isTablet: boolean = false;
  breakpoint!: number;

  constructor(
    private api: ApiServiceService,
    private router:Router,
    private auth:AuthService,
    private snack: MatSnackBar
  ) { }
  ngOnInit(): void {
    this.getAllMovies();
    if (window.innerWidth <= 550) {
      this.breakpoint = 1;
    } else if (window.innerWidth <= 1007) {
      this.breakpoint = 3;
    } else {
      this.breakpoint = 5;
    }
  }
   onResize(event: any) {
    if (window.innerWidth <= 550) {
      this.breakpoint = 1;
    } else if (window.innerWidth <= 1007) {
      this.breakpoint = 3;
    } else {
      this.breakpoint = 5;
    }
   }

  searchByKeyword(searchKey: any) {
    console.log(searchKey);
    this.allMovies = []
    this.getAllMovies(searchKey)
  }

  public getAllMovies(searchKeyword: string = "") {
    this.api.search(searchKeyword).subscribe(
      (response) => {
        console.log(response);
        this.allMovies = response;
      }, (error) => {
        this.snack.open(error.error, 'Dismiss');
        console.log(error);
      }
    )
  }

  bookTickets(m:any){
    if(this.auth.isLoggedIn()){
      this.auth.setMovie(m);
      // this.auth.setMovieName(movieName);
      // this.auth.setTheatreName(theatreName);
      // this.auth.setMovieCost(costOfTicket);
      this.router.navigate(['/book-ticket']);
    }
    else{
      Swal.fire({
        title:'You have to login to book tickets...',
        icon:'warning'
      });
      this.router.navigate(['/login']);
    }
  }

}
