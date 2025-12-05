import { TestBed } from '@angular/core/testing';

import { SimcardService } from './simcard.service';

describe('SimcardService', () => {
  let service: SimcardService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SimcardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
