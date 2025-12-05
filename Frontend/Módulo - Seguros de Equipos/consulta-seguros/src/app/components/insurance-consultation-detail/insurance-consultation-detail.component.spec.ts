import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InsuranceConsultationDetailComponent } from './insurance-consultation-detail.component';

describe('InsuranceConsultationDetailComponent', () => {
  let component: InsuranceConsultationDetailComponent;
  let fixture: ComponentFixture<InsuranceConsultationDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InsuranceConsultationDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InsuranceConsultationDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
