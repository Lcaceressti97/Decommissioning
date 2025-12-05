import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcessInvoicesModalComponent } from './process-invoices-modal.component';

describe('ProcessInvoicesModalComponent', () => {
  let component: ProcessInvoicesModalComponent;
  let fixture: ComponentFixture<ProcessInvoicesModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProcessInvoicesModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProcessInvoicesModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
