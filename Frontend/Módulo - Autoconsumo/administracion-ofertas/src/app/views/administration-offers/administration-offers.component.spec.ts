import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministrationOffersComponent } from './administration-offers.component';

describe('AdministrationOffersComponent', () => {
  let component: AdministrationOffersComponent;
  let fixture: ComponentFixture<AdministrationOffersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdministrationOffersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdministrationOffersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
