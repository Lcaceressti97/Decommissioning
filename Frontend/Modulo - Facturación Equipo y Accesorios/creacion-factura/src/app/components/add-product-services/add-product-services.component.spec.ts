import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddProductServicesComponent } from './add-product-services.component';

describe('AddProductServicesComponent', () => {
  let component: AddProductServicesComponent;
  let fixture: ComponentFixture<AddProductServicesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddProductServicesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddProductServicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
