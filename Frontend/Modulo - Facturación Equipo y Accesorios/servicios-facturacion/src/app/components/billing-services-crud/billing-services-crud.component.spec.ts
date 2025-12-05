import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BillingServicesCrudComponent } from './billing-services-crud.component';

describe('BillingServicesCrudComponent', () => {
  let component: BillingServicesCrudComponent;
  let fixture: ComponentFixture<BillingServicesCrudComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BillingServicesCrudComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BillingServicesCrudComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
