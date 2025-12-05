import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InsuranceConsultationComponent } from './insurance-consultation.component';

describe('InsuranceConsultationComponent', () => {
  let component: InsuranceConsultationComponent;
  let fixture: ComponentFixture<InsuranceConsultationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InsuranceConsultationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InsuranceConsultationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
