import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LineMooringComponent } from './line-mooring.component';

describe('LineMooringComponent', () => {
  let component: LineMooringComponent;
  let fixture: ComponentFixture<LineMooringComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LineMooringComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LineMooringComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
