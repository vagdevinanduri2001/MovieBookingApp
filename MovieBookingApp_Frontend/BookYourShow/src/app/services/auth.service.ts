import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }

  public setMovie(movie:any){
    localStorage.setItem("movie",JSON.stringify(movie));
  }
  public getMovie(){
    return JSON.parse(localStorage.getItem("movie")!);
  }

  public setCustomer(customer:any){
    localStorage.setItem("customer",JSON.stringify(customer));
  }
  public getCustomer(){
    return JSON.parse(localStorage.getItem("customer")!);
  }

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

  // public isLoggedIn() {
  //   let tokenStr = localStorage.getItem('token');
  //   if (tokenStr == undefined || tokenStr == '' || tokenStr == null)
  //     return false;
  //   else return true;
  // }
}
