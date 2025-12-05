import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeductibleRatesComponent } from './deductible-rates.component';

describe('DeductibleRatesComponent', () => {
  let component: DeductibleRatesComponent;
  let fixture: ComponentFixture<DeductibleRatesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeductibleRatesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeductibleRatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
