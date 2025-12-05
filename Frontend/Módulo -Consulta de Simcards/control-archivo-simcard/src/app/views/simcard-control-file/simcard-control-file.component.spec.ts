import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SimcardControlFileComponent } from './simcard-control-file.component';

describe('SimcardControlFileComponent', () => {
  let component: SimcardControlFileComponent;
  let fixture: ComponentFixture<SimcardControlFileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SimcardControlFileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SimcardControlFileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
