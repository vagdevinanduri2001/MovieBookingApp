import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NgForm } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ApiServiceService {

  PATH_OF_API = "http://localhost:8081";

  constructor(
    private http:HttpClient
  ) {}

  requestHeader = new HttpHeaders({"No-Auth":"True"})

   public register(customer:any){
    return this.http.post(this.PATH_OF_API+"/register",customer,
    {headers:this.requestHeader,"responseType":"text"});
   }
   public login(loginData:NgForm){
    return this.http.post(this.PATH_OF_API+"/signIn",loginData,
    {headers:this.requestHeader});
   }
}