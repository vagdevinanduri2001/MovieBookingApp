import { Component } from '@angular/core';
import { FormBuilder, NgForm, Validators } from '@angular/forms';
import { ApiServiceService } from '../services/api-service.service';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

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
    private router:Router
    ){}

  loginData = this.fb.group(
    {userName:['',Validators.required],
    password:['',Validators.required]}
  );

login(loginForm:NgForm){
  this.apiService.login(loginForm.value).subscribe(
    (response:any)=>{
      this.auth.setToken(response.token);
      this.auth.setRole(response.customer.role);
      this.auth.setUserName(response.customer.userName);

      alert("Enjoy BookYourShow website")

      const role = response.customer.role;
      if(role==='Admin'){
        this.router.navigate(['/admin']);
      }else{
        this.router.navigate(['/customer']);
      }
    },
    (error)=>{
      alert("Please check your userName and password")
      console.log(error);
    }
  )
}
}
