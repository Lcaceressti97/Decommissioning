import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadOceComponent } from './load-oce.component';

describe('LoadOceComponent', () => {
  let component: LoadOceComponent;
  let fixture: ComponentFixture<LoadOceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoadOceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadOceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
