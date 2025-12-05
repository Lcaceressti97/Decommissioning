import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministrationOffersModalComponent } from './administration-offers-modal.component';

describe('AdministrationOffersModalComponent', () => {
  let component: AdministrationOffersModalComponent;
  let fixture: ComponentFixture<AdministrationOffersModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdministrationOffersModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdministrationOffersModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
