import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ControlOrderModalComponent } from './control-order-modal.component';

describe('ControlOrderModalComponent', () => {
  let component: ControlOrderModalComponent;
  let fixture: ComponentFixture<ControlOrderModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ControlOrderModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ControlOrderModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
