import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BankConfigurationModalComponent } from './bank-configuration-modal.component';

describe('BankConfigurationModalComponent', () => {
  let component: BankConfigurationModalComponent;
  let fixture: ComponentFixture<BankConfigurationModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BankConfigurationModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BankConfigurationModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
