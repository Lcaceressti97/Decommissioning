import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignBranchComponent } from './assign-branch.component';

describe('AssignBranchComponent', () => {
  let component: AssignBranchComponent;
  let fixture: ComponentFixture<AssignBranchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AssignBranchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignBranchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
