import { Component } from '@angular/core';
import { FormBuilder, NgForm, Validators } from '@angular/forms';
import { ApiServiceService } from '../services/api-service.service';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NGXLogger } from 'ngx-logger';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  hide=true;
  constructor(private fb:FormBuilder,
    private apiService:ApiServiceService,
    private auth:AuthService,
    private router:Router,
    private snack : MatSnackBar,
    private logger : NGXLogger
    ){}

  loginData = this.fb.group(
    {userName:['',Validators.required],
    password:['',Validators.required]}
  );

login(loginForm:NgForm){
  this.apiService.login(loginForm.value).subscribe(
    (response:any)=>{
      this.snack.open('You have successfully logged in!','Ok',{duration:3000});
      this.auth.setToken(response.token);
      this.auth.setCustomer(response.customer);
      this.auth.setRole(response.customer.role);
      this.auth.setUserName(response.customer.userName);
      const role = response.customer.role;
      if(role==='Admin'){
        this.logger.info('Admin Logged in');
        this.router.navigate(['/admin']);
      }else{
        this.logger.info('User Logged in');
        this.router.navigate(['/']);
      }
    },
    (error)=>{
      alert("Please check your userName and password")
      console.log(error);
      this.logger.info(error);
    }
  )
}
}
