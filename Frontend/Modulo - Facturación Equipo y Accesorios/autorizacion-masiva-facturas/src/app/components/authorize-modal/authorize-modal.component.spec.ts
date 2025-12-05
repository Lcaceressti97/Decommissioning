import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthorizeModalComponent } from './authorize-modal.component';

describe('AuthorizeModalComponent', () => {
  let component: AuthorizeModalComponent;
  let fixture: ComponentFixture<AuthorizeModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AuthorizeModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuthorizeModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
