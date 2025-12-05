import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailBlockedLinesComponent } from './detail-blocked-lines.component';

describe('DetailBlockedLinesComponent', () => {
  let component: DetailBlockedLinesComponent;
  let fixture: ComponentFixture<DetailBlockedLinesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetailBlockedLinesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetailBlockedLinesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
