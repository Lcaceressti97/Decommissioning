import { TestBed } from '@angular/core/testing';

import { BulkAuthorizeService } from './bulk-authorize.service';

describe('BulkAuthorizeService', () => {
  let service: BulkAuthorizeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BulkAuthorizeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
