import { TestBed } from '@angular/core/testing';

import { ApiServiceService } from './api-service.service';
import { HttpClientTestingModule, HttpTestingController,  } from '@angular/common/http/testing';

describe('ApiServiceService', () => {
  let service: ApiServiceService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports : [HttpClientTestingModule],
      providers:[ApiServiceService]
    });
    service = TestBed.inject(ApiServiceService);
    httpMock = TestBed.inject(HttpTestingController);
    
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should send a POST request to register', () => {
    const customer = { name: 'John Doe', email: 'john@example.com' };
    const response = 'Success';

    service.register(customer).subscribe((res) => {
      expect(res).toEqual(response);
    });

    const req = httpMock.expectOne('http://localhost:8081/register');
    expect(req.request.method).toBe('POST');
    req.flush(response);
  });

});
