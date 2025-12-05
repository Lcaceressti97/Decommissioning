import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SafeControlEquipmentDetailComponent } from './safe-control-equipment-detail.component';

describe('SafeControlEquipmentDetailComponent', () => {
  let component: SafeControlEquipmentDetailComponent;
  let fixture: ComponentFixture<SafeControlEquipmentDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SafeControlEquipmentDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SafeControlEquipmentDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
