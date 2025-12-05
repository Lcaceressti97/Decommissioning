import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderControlModalComponent } from './order-control-modal.component';

describe('OrderControlModalComponent', () => {
  let component: OrderControlModalComponent;
  let fixture: ComponentFixture<OrderControlModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OrderControlModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderControlModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
