import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ComodatosMooringComponent } from './comodatos-mooring.component';

describe('ComodatosMooringComponent', () => {
  let component: ComodatosMooringComponent;
  let fixture: ComponentFixture<ComodatosMooringComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ComodatosMooringComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ComodatosMooringComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
