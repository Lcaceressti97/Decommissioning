import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfigurationParametersComponent } from './configuration-parameters.component';

describe('ConfigurationParametersComponent', () => {
  let component: ConfigurationParametersComponent;
  let fixture: ComponentFixture<ConfigurationParametersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfigurationParametersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfigurationParametersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
