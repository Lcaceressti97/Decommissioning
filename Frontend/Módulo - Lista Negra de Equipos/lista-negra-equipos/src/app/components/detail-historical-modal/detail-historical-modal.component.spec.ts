import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailHistoricalModalComponent } from './detail-historical-modal.component';

describe('DetailHistoricalModalComponent', () => {
  let component: DetailHistoricalModalComponent;
  let fixture: ComponentFixture<DetailHistoricalModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetailHistoricalModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetailHistoricalModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
