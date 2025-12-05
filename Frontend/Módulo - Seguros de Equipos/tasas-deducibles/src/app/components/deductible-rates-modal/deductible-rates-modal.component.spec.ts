import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeductibleRatesModalComponent } from './deductible-rates-modal.component';

describe('DeductibleRatesModalComponent', () => {
  let component: DeductibleRatesModalComponent;
  let fixture: ComponentFixture<DeductibleRatesModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeductibleRatesModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeductibleRatesModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
