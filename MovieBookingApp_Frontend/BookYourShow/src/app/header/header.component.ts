import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit{

  constructor(private auth: AuthService,
    private router: Router) { } 

  ngOnInit(): void {
  }

  public isLoggedin() {
    return this.auth.isLoggedIn();
  }
  public logout() {
    this.auth.clear();
    this.router.navigate(['/']);
  }
 
  public CheckIfAdmin(){
    if(this.auth.getRole()==='Admin'){
      return true;
    }else{
      return false;
    }
  }
}
  