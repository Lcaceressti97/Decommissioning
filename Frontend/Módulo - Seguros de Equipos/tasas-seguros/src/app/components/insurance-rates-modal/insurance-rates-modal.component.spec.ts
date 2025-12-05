import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InsuranceRatesModalComponent } from './insurance-rates-modal.component';

describe('InsuranceRatesModalComponent', () => {
  let component: InsuranceRatesModalComponent;
  let fixture: ComponentFixture<InsuranceRatesModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InsuranceRatesModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InsuranceRatesModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
