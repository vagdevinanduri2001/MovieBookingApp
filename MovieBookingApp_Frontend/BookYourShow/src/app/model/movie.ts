import movieId from "./movieId";

export class Movie {

    movieId: movieId;

    costOfTicket: number=0;

    noOfTicketsAllotted: number=0;

    constructor(

        movieId: movieId,

        costOfTicket: number,

        noOfTicketsAllotted: number,

    ) {

        this.movieId = movieId;

        this.costOfTicket = costOfTicket;

        this.noOfTicketsAllotted = noOfTicketsAllotted;

    }

}