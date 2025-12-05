import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InsuranceRatesComponent } from './insurance-rates.component';

describe('InsuranceRatesComponent', () => {
  let component: InsuranceRatesComponent;
  let fixture: ComponentFixture<InsuranceRatesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InsuranceRatesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InsuranceRatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
