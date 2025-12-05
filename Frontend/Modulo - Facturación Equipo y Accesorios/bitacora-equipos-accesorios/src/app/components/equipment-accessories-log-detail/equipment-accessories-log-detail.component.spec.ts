import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EquipmentAccessoriesLogDetailComponent } from './equipment-accessories-log-detail.component';

describe('EquipmentAccessoriesLogDetailComponent', () => {
  let component: EquipmentAccessoriesLogDetailComponent;
  let fixture: ComponentFixture<EquipmentAccessoriesLogDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EquipmentAccessoriesLogDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EquipmentAccessoriesLogDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
