import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BlacklistPageComponent } from './blacklist-page.component';

describe('BlacklistPageComponent', () => {
  let component: BlacklistPageComponent;
  let fixture: ComponentFixture<BlacklistPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BlacklistPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BlacklistPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
