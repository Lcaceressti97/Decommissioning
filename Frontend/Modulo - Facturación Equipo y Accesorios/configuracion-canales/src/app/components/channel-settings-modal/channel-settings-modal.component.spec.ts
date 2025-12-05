import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChannelSettingsModalComponent } from './channel-settings-modal.component';

describe('ChannelSettingsModalComponent', () => {
  let component: ChannelSettingsModalComponent;
  let fixture: ComponentFixture<ChannelSettingsModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChannelSettingsModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChannelSettingsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
