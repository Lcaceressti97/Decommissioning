import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InsuranceClaimModalComponent } from './insurance-claim-modal.component';

describe('InsuranceClaimModalComponent', () => {
  let component: InsuranceClaimModalComponent;
  let fixture: ComponentFixture<InsuranceClaimModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InsuranceClaimModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InsuranceClaimModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
