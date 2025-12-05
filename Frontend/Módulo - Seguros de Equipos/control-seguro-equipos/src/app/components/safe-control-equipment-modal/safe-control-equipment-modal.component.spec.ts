import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SafeControlEquipmentModalComponent } from './safe-control-equipment-modal.component';

describe('SafeControlEquipmentModalComponent', () => {
  let component: SafeControlEquipmentModalComponent;
  let fixture: ComponentFixture<SafeControlEquipmentModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SafeControlEquipmentModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SafeControlEquipmentModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
