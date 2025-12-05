import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReleaseSerialDetailComponent } from './release-serial-detail.component';

describe('ReleaseSerialDetailComponent', () => {
  let component: ReleaseSerialDetailComponent;
  let fixture: ComponentFixture<ReleaseSerialDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReleaseSerialDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReleaseSerialDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
