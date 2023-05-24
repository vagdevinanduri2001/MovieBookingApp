import { Component } from '@angular/core';
import forgot from '../model/forgot';
import { ApiServiceService } from '../services/api-service.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

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
        Swal.fire({
          title:'Your password is changed!',
          text:'You can login now...',
          icon:'success'
        });
        console.log(response);
      },
      (error) => {
        Swal.fire({
          title:error.error,
          icon:'error'
        });
        console.log(error);
      }
    )
  }
}
