import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmissionModalComponent } from './emission-modal.component';

describe('EmissionModalComponent', () => {
  let component: EmissionModalComponent;
  let fixture: ComponentFixture<EmissionModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmissionModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmissionModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
