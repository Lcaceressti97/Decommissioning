import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PriceMasterModelComponent } from './price-master-model.component';

describe('PriceMasterModelComponent', () => {
  let component: PriceMasterModelComponent;
  let fixture: ComponentFixture<PriceMasterModelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PriceMasterModelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PriceMasterModelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
