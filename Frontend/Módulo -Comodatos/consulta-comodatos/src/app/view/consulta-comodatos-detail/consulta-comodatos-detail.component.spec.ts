import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultaComodatosDetailComponent } from './consulta-comodatos-detail.component';

describe('ConsultaComodatosDetailComponent', () => {
  let component: ConsultaComodatosDetailComponent;
  let fixture: ComponentFixture<ConsultaComodatosDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConsultaComodatosDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConsultaComodatosDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
