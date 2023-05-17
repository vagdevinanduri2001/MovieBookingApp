import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }

  public setRole(role:string){
    localStorage.setItem("role",role);
  }
  public getRole(){
    return localStorage.getItem("role");
  }
  public setUserName(userName:string){
    localStorage.setItem("userName",userName);
  }
  public getUserName(){
    return localStorage.getItem("userName");
  }
  public setToken(token:string){
    localStorage.setItem("token",token);
  }
  public getToken(){
    return localStorage.getItem("token");
  }
  public clear(){
    localStorage.clear();
  }
  public isLoggedIn(){
    return this.getRole()&&this.getToken();
  }
}
