import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StrModalComponent } from './str-modal.component';

describe('StrModalComponent', () => {
  let component: StrModalComponent;
  let fixture: ComponentFixture<StrModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StrModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StrModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
