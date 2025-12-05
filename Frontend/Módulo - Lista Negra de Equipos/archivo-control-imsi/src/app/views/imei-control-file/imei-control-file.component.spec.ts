import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ImeiControlFileComponent } from './imei-control-file.component';

describe('ImsiControlFileComponent', () => {
  let component: ImeiControlFileComponent;
  let fixture: ComponentFixture<ImeiControlFileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ImeiControlFileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ImeiControlFileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
