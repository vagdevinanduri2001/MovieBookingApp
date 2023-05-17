import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiServiceService } from '../services/api-service.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
hide=true;

constructor(
  private router : Router,
  private apiService : ApiServiceService
){}

register(registerForm:NgForm){
    this.apiService.register(registerForm.value).subscribe(
      (response)=>{
        this.router.navigate(['/login']);
      },
      (error)=>{
        console.log(error);
        alert(error.error)
      }
      )
}

}
