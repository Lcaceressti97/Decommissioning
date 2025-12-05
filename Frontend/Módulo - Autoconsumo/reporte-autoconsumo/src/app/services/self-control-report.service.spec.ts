import { TestBed } from '@angular/core/testing';

import { SelfControlReportService } from './self-control-report.service';

describe('SelfControlReportService', () => {
  let service: SelfControlReportService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SelfControlReportService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
