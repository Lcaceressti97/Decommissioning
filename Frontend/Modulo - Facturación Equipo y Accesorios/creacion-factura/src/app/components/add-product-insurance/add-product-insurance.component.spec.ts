import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddProductInsuranceComponent } from './add-product-insurance.component';

describe('AddProductInsuranceComponent', () => {
  let component: AddProductInsuranceComponent;
  let fixture: ComponentFixture<AddProductInsuranceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddProductInsuranceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddProductInsuranceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
