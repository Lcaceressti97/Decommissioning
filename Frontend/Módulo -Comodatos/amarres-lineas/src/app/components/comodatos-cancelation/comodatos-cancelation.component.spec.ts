import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ComodatosCancelationComponent } from './comodatos-cancelation.component';

describe('ComodatosCancelationComponent', () => {
  let component: ComodatosCancelationComponent;
  let fixture: ComponentFixture<ComodatosCancelationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ComodatosCancelationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ComodatosCancelationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
