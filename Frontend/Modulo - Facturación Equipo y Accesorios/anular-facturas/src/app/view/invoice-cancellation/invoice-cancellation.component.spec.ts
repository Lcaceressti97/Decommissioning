import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InvoiceCancellationComponent } from './invoice-cancellation.component';

describe('InvoiceCancellationComponent', () => {
  let component: InvoiceCancellationComponent;
  let fixture: ComponentFixture<InvoiceCancellationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InvoiceCancellationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InvoiceCancellationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
