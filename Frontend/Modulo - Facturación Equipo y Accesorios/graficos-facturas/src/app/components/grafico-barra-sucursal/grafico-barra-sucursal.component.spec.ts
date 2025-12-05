import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GraficoBarraSucursalComponent } from './grafico-barra-sucursal.component';

describe('GraficoBarraSucursalComponent', () => {
  let component: GraficoBarraSucursalComponent;
  let fixture: ComponentFixture<GraficoBarraSucursalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GraficoBarraSucursalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GraficoBarraSucursalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
