import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BulkAuthorizeComponent } from './bulk-authorize.component';

describe('BulkAuthorizeComponent', () => {
  let component: BulkAuthorizeComponent;
  let fixture: ComponentFixture<BulkAuthorizeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BulkAuthorizeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BulkAuthorizeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
