import { TestBed } from '@angular/core/testing';

import { BankConfigurationService } from './bank-configuration.service';

describe('BankConfigurationService', () => {
  let service: BankConfigurationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BankConfigurationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
