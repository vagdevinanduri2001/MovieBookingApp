import { Component } from '@angular/core';
import { Movie } from '../model/movie';
import { ApiServiceService } from '../services/api-service.service';
import movieId from '../model/movieId';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NGXLogger } from 'ngx-logger';

@Component({
  selector: 'app-add-movie',
  templateUrl: './add-movie.component.html',
  styleUrls: ['./add-movie.component.css']
})
export class AddMovieComponent {

  movie: Movie;

  constructor(private api: ApiServiceService,private router:Router,
    private snack:MatSnackBar,
    private logger: NGXLogger
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
        this.logger.info('Movie Added successfully!');
      },
      (error) => {
        Swal.fire({
          title:error.error,
          icon:'warning'
        })
        console.log(error);
        this.logger.error(error.error);
      }
    );
  }

}
