import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportGenerationInvComponent } from './report-generation-inv.component';

describe('ReportGenerationInvComponent', () => {
  let component: ReportGenerationInvComponent;
  let fixture: ComponentFixture<ReportGenerationInvComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportGenerationInvComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportGenerationInvComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
