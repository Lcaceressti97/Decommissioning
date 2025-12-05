import { TestBed } from '@angular/core/testing';

import { InsuranceClaimInquiryService } from './insurance-claim-inquiry.service';

describe('InsuranceClaimInquiryService', () => {
  let service: InsuranceClaimInquiryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InsuranceClaimInquiryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
