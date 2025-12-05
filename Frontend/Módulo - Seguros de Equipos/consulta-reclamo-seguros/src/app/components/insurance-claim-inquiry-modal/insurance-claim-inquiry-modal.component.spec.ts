import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InsuranceClaimInquiryModalComponent } from './insurance-claim-inquiry-modal.component';

describe('InsuranceClaimInquiryModalComponent', () => {
  let component: InsuranceClaimInquiryModalComponent;
  let fixture: ComponentFixture<InsuranceClaimInquiryModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InsuranceClaimInquiryModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InsuranceClaimInquiryModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
