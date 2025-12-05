import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SelfControlReportComponent } from './self-control-report.component';

describe('SelfControlReportComponent', () => {
  let component: SelfControlReportComponent;
  let fixture: ComponentFixture<SelfControlReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SelfControlReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SelfControlReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
