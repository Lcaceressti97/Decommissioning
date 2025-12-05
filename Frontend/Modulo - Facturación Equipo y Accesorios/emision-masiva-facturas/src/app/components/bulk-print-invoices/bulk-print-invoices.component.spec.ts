import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BulkPrintInvoicesComponent } from './bulk-print-invoices.component';

describe('BulkPrintInvoicesComponent', () => {
  let component: BulkPrintInvoicesComponent;
  let fixture: ComponentFixture<BulkPrintInvoicesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BulkPrintInvoicesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BulkPrintInvoicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
