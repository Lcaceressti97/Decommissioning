import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MantenimientoTarjetasComponent } from './mantenimiento-tarjetas.component';

describe('MantenimientoTarjetasComponent', () => {
  let component: MantenimientoTarjetasComponent;
  let fixture: ComponentFixture<MantenimientoTarjetasComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MantenimientoTarjetasComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MantenimientoTarjetasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
