import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProviderConfigurationComponent } from './provider-configuration.component';

describe('ProviderConfigurationComponent', () => {
  let component: ProviderConfigurationComponent;
  let fixture: ComponentFixture<ProviderConfigurationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProviderConfigurationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProviderConfigurationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
