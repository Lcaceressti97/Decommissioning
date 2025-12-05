import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BalanceInquiryAmsysComponent } from './balance-inquiry-amsys.component';

describe('BalanceInquiryAmsysComponent', () => {
  let component: BalanceInquiryAmsysComponent;
  let fixture: ComponentFixture<BalanceInquiryAmsysComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BalanceInquiryAmsysComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BalanceInquiryAmsysComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
