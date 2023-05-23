import { Component } from '@angular/core';
import forgot from '../model/forgot';
import { ApiServiceService } from '../services/api-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-forgot',
  templateUrl: './forgot.component.html',
  styleUrls: ['./forgot.component.css']
})
export class ForgotComponent {
  hide = true

  userName: any = '';
  passwordComp: forgot;

  constructor(private api: ApiServiceService, private router: Router
  ) {

    this.passwordComp = new forgot(
      "", ""
    );

  }

  forgotPassword(forgotForm: any) {
    this.api.forgot(this.userName, this.passwordComp).subscribe(
      (response) => {
        console.log(response);
      },
      (error) => {
        console.log(error);
      }
    )
  }
}
