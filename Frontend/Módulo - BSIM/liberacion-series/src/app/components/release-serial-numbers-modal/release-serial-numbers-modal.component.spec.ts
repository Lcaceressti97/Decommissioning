import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReleaseSerialNumbersModalComponent } from './release-serial-numbers-modal.component';

describe('ReleaseSerialNumbersModalComponent', () => {
  let component: ReleaseSerialNumbersModalComponent;
  let fixture: ComponentFixture<ReleaseSerialNumbersModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReleaseSerialNumbersModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReleaseSerialNumbersModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
