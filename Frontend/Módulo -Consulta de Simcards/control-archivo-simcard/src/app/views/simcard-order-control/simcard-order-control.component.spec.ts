import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SimcardOrderControlComponent } from './simcard-order-control.component';

describe('SimcardOrderControlComponent', () => {
  let component: SimcardOrderControlComponent;
  let fixture: ComponentFixture<SimcardOrderControlComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SimcardOrderControlComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SimcardOrderControlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
