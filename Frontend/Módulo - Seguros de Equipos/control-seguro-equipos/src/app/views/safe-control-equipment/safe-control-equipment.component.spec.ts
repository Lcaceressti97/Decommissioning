import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SafeControlEquipmentComponent } from './safe-control-equipment.component';

describe('SafeControlEquipmentComponent', () => {
  let component: SafeControlEquipmentComponent;
  let fixture: ComponentFixture<SafeControlEquipmentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SafeControlEquipmentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SafeControlEquipmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
