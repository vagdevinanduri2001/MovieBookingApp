import { Component, OnInit } from '@angular/core';
import { ApiServiceService } from '../services/api-service.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{

  allMovies:any =[];

  constructor(
    private api:ApiServiceService
  ){}
  ngOnInit(): void {
    this.getAllMovies();
  }
  searchByKeyword(searchKey:any){
      console.log(searchKey);
      this.allMovies=[]
      this.getAllMovies(searchKey)
  }

  public getAllMovies(searchKeyword:string=""){
    this.api.search(searchKeyword).subscribe(
      (response)=>{
        console.log(response);
        this.allMovies=response;
      },(error)=>{
        console.log(error);
      }
    )
  }

}
