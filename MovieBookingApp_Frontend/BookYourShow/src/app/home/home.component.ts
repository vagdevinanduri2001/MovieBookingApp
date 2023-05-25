import { Component, OnInit } from '@angular/core';
import { ApiServiceService } from '../services/api-service.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';

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
    private snack: MatSnackBar,
    private breakpointObserver: BreakpointObserver
  ) { }
  ngOnInit(): void {
    this.getAllMovies();
    // if (window.innerWidth <= 400) {
    //   this.breakpoint = 1;
    // } else if (window.innerWidth <= 1007) {
    //   this.breakpoint = 3;
    // } else {
    //   this.breakpoint = 5;
    // }
    this.breakpointObserver.observe([Breakpoints.Handset, Breakpoints.Tablet]).subscribe(result => {
      this.isHandset = result.matches && result.breakpoints[Breakpoints.Handset];
      this.isTablet = result.matches && result.breakpoints[Breakpoints.Tablet];
    });
  }
   onResize(event: any) {
  //   if (window.innerWidth <= 641) {
  //     this.breakpoint = 1;
  //   } else if (window.innerWidth <= 1007) {
  //     this.breakpoint = 3;
  //   } else {
  //     this.breakpoint = 5;
  //   }
  this.breakpointObserver.observe([Breakpoints.Handset, Breakpoints.Tablet]).subscribe(result => {
    this.isHandset = result.matches && result.breakpoints[Breakpoints.Handset];
    this.isTablet = result.matches && result.breakpoints[Breakpoints.Tablet];
  });
  this.getGridListCols();
   }

  getGridListCols() {
    return this.isHandset ? 1 :
      this.isTablet ? 3 : 5;
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
        this.allMovies.forEach((movie: any) => { console.log(movie.movieId.movieName) });
      }, (error) => {
        this.snack.open(error.error, 'Dismiss');
        console.log(error);
      }
    )
  }

}
