import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadOceCorporativeComponent } from './load-oce-corporative.component';

describe('LoadOceCorporativeComponent', () => {
  let component: LoadOceCorporativeComponent;
  let fixture: ComponentFixture<LoadOceCorporativeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoadOceCorporativeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoadOceCorporativeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
