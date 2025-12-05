import { TestBed } from '@angular/core/testing';

import { AdministrationOffersService } from './administration-offers.service';

describe('AdministrationOffersService', () => {
  let service: AdministrationOffersService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdministrationOffersService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
