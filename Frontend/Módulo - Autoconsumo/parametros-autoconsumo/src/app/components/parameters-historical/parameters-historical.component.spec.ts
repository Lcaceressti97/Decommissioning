import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ParametersHistoricalComponent } from './parameters-historical.component';

describe('ParametersHistoricalComponent', () => {
  let component: ParametersHistoricalComponent;
  let fixture: ComponentFixture<ParametersHistoricalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ParametersHistoricalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ParametersHistoricalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
