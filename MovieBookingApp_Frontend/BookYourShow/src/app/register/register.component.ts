import { Component } from '@angular/core';
import { FormBuilder, NgForm, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiServiceService } from '../services/api-service.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
hide=true;

constructor(
  private router : Router,
  private apiService : ApiServiceService,
  private snack : MatSnackBar
){}

register(registerForm:any){
    this.apiService.register(registerForm.value).subscribe(
      (response)=>{
        this.router.navigate(['/login']);
        this.snack.open('You have successfully registered','Ok');
      },
      (error)=>{
        console.log(error);
        alert(error.error)
      }
      )
}

}
