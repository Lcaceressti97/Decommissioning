import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApprovalsModalComponent } from './approvals-modal.component';

describe('ApprovalsModalComponent', () => {
  let component: ApprovalsModalComponent;
  let fixture: ComponentFixture<ApprovalsModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApprovalsModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApprovalsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
