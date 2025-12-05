import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HistoricalModalComponent } from './historical-modal.component';

describe('HistoricalModalComponent', () => {
  let component: HistoricalModalComponent;
  let fixture: ComponentFixture<HistoricalModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HistoricalModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HistoricalModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
