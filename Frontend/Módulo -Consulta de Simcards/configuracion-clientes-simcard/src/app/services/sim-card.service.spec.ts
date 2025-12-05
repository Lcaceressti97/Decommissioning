import { TestBed } from '@angular/core/testing';

import { SimCardService } from './sim-card.service';

describe('SimCardService', () => {
  let service: SimCardService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SimCardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
