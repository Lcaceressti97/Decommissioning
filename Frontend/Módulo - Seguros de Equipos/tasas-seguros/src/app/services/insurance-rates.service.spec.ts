import { TestBed } from '@angular/core/testing';

import { InsuranceRatesService } from './insurance-rates.service';

describe('InsuranceRatesService', () => {
  let service: InsuranceRatesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InsuranceRatesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
