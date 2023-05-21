import { Component } from '@angular/core';
import { NgForm, NgModel } from '@angular/forms';
import { ApiServiceService } from '../services/api-service.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent {
hide=true;

  userName = '';
  password = '';

constructor(
  private api:ApiServiceService
){}

public forgot(forgotForm:NgForm){
this.api.forgotPassword(this.userName,this.password).subscribe(
  (response)=>{
    console.log(response);
  },
  (error)=>{
console.log(error);
  }
)
}
}
