import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserDetailCarouselComponent } from './user-detail-carousel.component';

describe('UserDetailCarouselComponent', () => {
  let component: UserDetailCarouselComponent;
  let fixture: ComponentFixture<UserDetailCarouselComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserDetailCarouselComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserDetailCarouselComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
