import { TestBed } from '@angular/core/testing';

import { PriceMasterService } from './price-master.service';

describe('PriceMasterService', () => {
  let service: PriceMasterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PriceMasterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
