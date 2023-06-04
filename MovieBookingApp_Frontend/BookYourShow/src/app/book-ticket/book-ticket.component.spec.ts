import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookTicketComponent } from './book-ticket.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ApiServiceService } from '../services/api-service.service';
import { AuthService } from '../services/auth.service';
import { MatIconModule } from '@angular/material/icon';

describe('BookTicketComponent', () => {
  let component: BookTicketComponent;
  let fixture: ComponentFixture<BookTicketComponent>;
  let authService : jasmine.SpyObj<AuthService>;

  beforeEach(() => {
    const authSpy = jasmine.createSpyObj('AuthService',['getMovie']);
    TestBed.configureTestingModule({
      imports:[
        HttpClientTestingModule,
        MatIconModule
      ],
      declarations: [BookTicketComponent],
      providers:[ApiServiceService,
        {provide:AuthService,useValue:authSpy}
      ]
    });

    authService = TestBed.inject(AuthService) as jasmine.SpyObj<AuthService>;
    authService.getMovie.and.returnValue({
      movieId:{
        movieName:'testMovie',
        theatreName:'testTheatre'
      },
      id:1,
      imageUrl:'someString',
      costOfTicket:100,
    noOfTicketsAllotted:100,
    noOfTicketsSold: 2,
    ticketStatus:'Book ASAP'    
    });

    fixture = TestBed.createComponent(BookTicketComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});