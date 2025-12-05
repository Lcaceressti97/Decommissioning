import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InvoiceCreationModalComponent } from './invoice-creation-modal.component';

describe('InvoiceCreationModalComponent', () => {
  let component: InvoiceCreationModalComponent;
  let fixture: ComponentFixture<InvoiceCreationModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InvoiceCreationModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InvoiceCreationModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
