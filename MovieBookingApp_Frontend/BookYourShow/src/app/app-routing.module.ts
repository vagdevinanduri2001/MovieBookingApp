import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { AdminComponent } from './admin/admin.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { AddMovieComponent } from './add-movie/add-movie.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { MyTicketsComponent } from './my-tickets/my-tickets.component';
import { ForgotComponent } from './forgot/forgot.component';
import { BookTicketComponent } from './book-ticket/book-ticket.component';

const routes: Routes = [
  {path:'',component:HomeComponent},
  {path:'register',component:RegisterComponent},
  {path:'login',component:LoginComponent},
  {path:'admin',component:AdminComponent},
  {path:'profile',component:ProfileComponent},
  {path:'addMovie',component:AddMovieComponent},
  {path:'forgot',component:ForgotPasswordComponent},
  {path:'my-tickets',component:MyTicketsComponent},
  {path:'forgot-password',component:ForgotComponent},
  {path:'book-ticket',component:BookTicketComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
