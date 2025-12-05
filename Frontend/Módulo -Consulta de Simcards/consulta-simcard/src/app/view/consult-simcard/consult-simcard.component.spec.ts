import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultSimcardComponent } from './consult-simcard.component';

describe('ConsultSimcardComponent', () => {
  let component: ConsultSimcardComponent;
  let fixture: ComponentFixture<ConsultSimcardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConsultSimcardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConsultSimcardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
