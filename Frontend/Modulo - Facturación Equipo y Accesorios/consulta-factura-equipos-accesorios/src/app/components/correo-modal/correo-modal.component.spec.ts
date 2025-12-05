import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CorreoModalComponent } from './correo-modal.component';

describe('CorreoModalComponent', () => {
  let component: CorreoModalComponent;
  let fixture: ComponentFixture<CorreoModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CorreoModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CorreoModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
