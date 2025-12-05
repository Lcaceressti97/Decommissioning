import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultaComodatosSuscriptorComponent } from './consulta-comodatos-suscriptor.component';

describe('ConsultaComodatosSuscriptorComponent', () => {
  let component: ConsultaComodatosSuscriptorComponent;
  let fixture: ComponentFixture<ConsultaComodatosSuscriptorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConsultaComodatosSuscriptorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConsultaComodatosSuscriptorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
