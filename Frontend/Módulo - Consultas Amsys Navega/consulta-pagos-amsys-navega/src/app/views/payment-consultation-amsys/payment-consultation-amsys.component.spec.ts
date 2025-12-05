import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaymentConsultationAmsysComponent } from './payment-consultation-amsys.component';

describe('PaymentConsultationAmsysComponent', () => {
  let component: PaymentConsultationAmsysComponent;
  let fixture: ComponentFixture<PaymentConsultationAmsysComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaymentConsultationAmsysComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaymentConsultationAmsysComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
