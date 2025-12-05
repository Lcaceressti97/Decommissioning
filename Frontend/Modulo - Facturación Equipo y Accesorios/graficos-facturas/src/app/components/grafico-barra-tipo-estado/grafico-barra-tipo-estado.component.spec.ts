import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GraficoBarraTipoEstadoComponent } from './grafico-barra-tipo-estado.component';

describe('GraficoBarraTipoEstadoComponent', () => {
  let component: GraficoBarraTipoEstadoComponent;
  let fixture: ComponentFixture<GraficoBarraTipoEstadoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GraficoBarraTipoEstadoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GraficoBarraTipoEstadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
