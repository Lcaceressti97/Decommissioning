import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PriceMasterMaintenanceComponent } from './price-master-maintenance.component';

describe('PriceMasterMaintenanceComponent', () => {
  let component: PriceMasterMaintenanceComponent;
  let fixture: ComponentFixture<PriceMasterMaintenanceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PriceMasterMaintenanceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PriceMasterMaintenanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
