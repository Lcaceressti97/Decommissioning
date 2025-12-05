import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HistoricalImeiModalComponent } from './historical-imei-modal.component';

describe('HistoricalImeiModalComponent', () => {
  let component: HistoricalImeiModalComponent;
  let fixture: ComponentFixture<HistoricalImeiModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HistoricalImeiModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HistoricalImeiModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
