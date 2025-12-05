import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SeeOrderModalComponent } from './see-order-modal.component';

describe('SeeOrderModalComponent', () => {
  let component: SeeOrderModalComponent;
  let fixture: ComponentFixture<SeeOrderModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SeeOrderModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SeeOrderModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
