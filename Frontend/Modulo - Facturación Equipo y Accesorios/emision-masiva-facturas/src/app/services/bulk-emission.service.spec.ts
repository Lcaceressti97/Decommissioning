import { TestBed } from '@angular/core/testing';

import { BulkEmissionService } from './bulk-emission.service';

describe('BulkEmissionService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: BulkEmissionService = TestBed.get(BulkEmissionService);
    expect(service).toBeTruthy();
  });
});
