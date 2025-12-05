import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProviderCrudComponent } from './provider-crud.component';

describe('ProviderCrudComponent', () => {
  let component: ProviderCrudComponent;
  let fixture: ComponentFixture<ProviderCrudComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProviderCrudComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProviderCrudComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
