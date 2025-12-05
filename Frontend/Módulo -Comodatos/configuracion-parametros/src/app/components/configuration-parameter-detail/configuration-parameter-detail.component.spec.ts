import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfigurationParameterDetailComponent } from './configuration-parameter-detail.component';

describe('ConfigurationParameterDetailComponent', () => {
  let component: ConfigurationParameterDetailComponent;
  let fixture: ComponentFixture<ConfigurationParameterDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfigurationParameterDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfigurationParameterDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
