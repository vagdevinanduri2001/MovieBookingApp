import { Component } from '@angular/core';
import forgot from '../model/forgot';
import { ApiServiceService } from '../services/api-service.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { NGXLogger } from 'ngx-logger';

@Component({
  selector: 'app-forgot',
  templateUrl: './forgot.component.html',
  styleUrls: ['./forgot.component.css']
})
export class ForgotComponent {
  hide = true

  userName: any = '';
  passwordComp: forgot;

  constructor(private api: ApiServiceService, private router: Router,
    private logger : NGXLogger
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
        this.logger.info('Your password is changed!');
        this.router.navigate(['/login']);
      },
      (error) => {
        Swal.fire({
          title:error.error,
          icon:'error'
        });
        this.logger.error(error.error);
        console.log(error);
      }
    )
  }
}
