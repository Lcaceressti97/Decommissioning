import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EquipmentAccessoriesLogComponent } from './equipment-accessories-log.component';

describe('EquipmentAccessoriesLogComponent', () => {
  let component: EquipmentAccessoriesLogComponent;
  let fixture: ComponentFixture<EquipmentAccessoriesLogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EquipmentAccessoriesLogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EquipmentAccessoriesLogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
