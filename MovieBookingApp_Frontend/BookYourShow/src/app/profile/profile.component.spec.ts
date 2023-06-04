import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileComponent } from './profile.component';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { AuthService } from '../services/auth.service';
import { ApiServiceService } from '../services/api-service.service';

describe('ProfileComponent', () => {
  let component: ProfileComponent;
  let fixture: ComponentFixture<ProfileComponent>;
  let authService: jasmine.SpyObj<AuthService>;

  beforeEach(() => {
    const authSpy = jasmine.createSpyObj('AuthService', ['getCustomer']);
    TestBed.configureTestingModule({
      imports: [
        MatCardModule,
        MatIconModule,
        MatDividerModule
      ],
      declarations: [ProfileComponent],
      providers:[ApiServiceService,
        { provide: AuthService, useValue: authSpy }]
    });

    authService = TestBed.inject(AuthService) as jasmine.SpyObj<AuthService>;
    authService.getCustomer.and.returnValue({
      firstName:'firstName',
      lastName:'lastName',
      emailId:'email@email.com',
      loginId:1,
      userName:'userName',
      password:'password',
      confirmPassword:'password',
      contactNo:9999999999,
      role:'user'
    });

    fixture = TestBed.createComponent(ProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
