import { Component } from '@angular/core';
import { Movie } from '../model/movie';
import { ApiServiceService } from '../services/api-service.service';
import movieId from '../model/movieId';
import { NgForm } from '@angular/forms';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-add-movie',
  templateUrl: './add-movie.component.html',
  styleUrls: ['./add-movie.component.css']
})
export class AddMovieComponent {

  movie: Movie;

  constructor(private api: ApiServiceService,private router:Router,
    private snack:MatSnackBar
  ) {

    this.movie = new Movie(
      new movieId("", ""), 0, 0, ""
    );

  }

  addMovie() {
    this.api.addMovie(this.movie).subscribe(
      (response) => {
        console.log(response);
        this.snack.open('Movie Added successfully!','Ok');
        this.router.navigate(['/admin']);
      },
      (error) => {
        Swal.fire({
          title:error.error,
          icon:'warning'
        })
        console.log(error);
      }
    );
  }

}
