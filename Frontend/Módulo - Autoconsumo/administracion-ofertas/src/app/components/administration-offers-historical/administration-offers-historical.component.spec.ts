import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministrationOffersHistoricalComponent } from './administration-offers-historical.component';

describe('AdministrationOffersHistoricalComponent', () => {
  let component: AdministrationOffersHistoricalComponent;
  let fixture: ComponentFixture<AdministrationOffersHistoricalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdministrationOffersHistoricalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdministrationOffersHistoricalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
