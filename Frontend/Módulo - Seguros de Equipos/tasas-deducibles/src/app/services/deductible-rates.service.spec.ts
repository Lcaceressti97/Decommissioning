import { TestBed } from '@angular/core/testing';

import { DeductibleRatesService } from './deductible-rates.service';

describe('DeductibleRatesService', () => {
  let service: DeductibleRatesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DeductibleRatesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
