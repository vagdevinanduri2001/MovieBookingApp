import { Component } from '@angular/core';
import { FormBuilder, NgForm, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiServiceService } from '../services/api-service.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NGXLogger } from 'ngx-logger';

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
  private snack : MatSnackBar,
  private logger : NGXLogger
){}

register(registerForm:any){
    this.apiService.register(registerForm.value).subscribe(
      (response)=>{
        this.router.navigate(['/login']);
        this.snack.open('You have successfully registered','Ok');
        this.logger.info('You have successfully registered');
      },
      (error)=>{
        console.log(error);
        alert(error.error);
        this.logger.info(error);
      }
      )
}

}
