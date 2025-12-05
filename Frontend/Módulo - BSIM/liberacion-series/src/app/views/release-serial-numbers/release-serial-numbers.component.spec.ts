import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReleaseSerialNumbersComponent } from './release-serial-numbers.component';

describe('ReleaseSerialNumbersComponent', () => {
  let component: ReleaseSerialNumbersComponent;
  let fixture: ComponentFixture<ReleaseSerialNumbersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReleaseSerialNumbersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReleaseSerialNumbersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
