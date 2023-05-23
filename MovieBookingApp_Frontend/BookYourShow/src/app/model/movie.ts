import movieId from "./movieId";

export class Movie {

    movieId: movieId;
    id:number=0;
    imageUrl: string ='';
    costOfTicket: number=0;
    noOfTicketsAllotted: number=0;
    noOfTicketsSold: number=0;
    ticketStatus:string="Book ASAP";
    


    constructor(
        movieId: movieId,
        costOfTicket: number,
        noOfTicketsAllotted: number,
        imageUrl:string,
    ) {
        this.movieId = movieId;
        this.costOfTicket = costOfTicket;
        this.noOfTicketsAllotted = noOfTicketsAllotted;
        this.imageUrl=imageUrl;
    }
}