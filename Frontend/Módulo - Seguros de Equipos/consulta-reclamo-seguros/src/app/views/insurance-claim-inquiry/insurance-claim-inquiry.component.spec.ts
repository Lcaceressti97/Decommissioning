import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InsuranceClaimInquiryComponent } from './insurance-claim-inquiry.component';

describe('InsuranceClaimInquiryComponent', () => {
  let component: InsuranceClaimInquiryComponent;
  let fixture: ComponentFixture<InsuranceClaimInquiryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InsuranceClaimInquiryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InsuranceClaimInquiryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
