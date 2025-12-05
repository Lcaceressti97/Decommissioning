import { TestBed } from '@angular/core/testing';

import { ReleaseSerialNumbersService } from './release-serial-numbers.service';

describe('ReleaseSerialNumbersService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ReleaseSerialNumbersService = TestBed.get(ReleaseSerialNumbersService);
    expect(service).toBeTruthy();
  });
});
