import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailInvoiceModalComponent } from './detail-invoice-modal.component';

describe('DetailInvoiceModalComponent', () => {
  let component: DetailInvoiceModalComponent;
  let fixture: ComponentFixture<DetailInvoiceModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetailInvoiceModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetailInvoiceModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
