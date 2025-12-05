import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LineMooriningDetailComponent } from './line-moorining-detail.component';

describe('LineMooriningDetailComponent', () => {
  let component: LineMooriningDetailComponent;
  let fixture: ComponentFixture<LineMooriningDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LineMooriningDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LineMooriningDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
