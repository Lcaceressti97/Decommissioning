import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetallesLoteTarjetasModalComponent } from './detalles-lote-tarjetas-modal.component';

describe('DetallesLoteTarjetasModalComponent', () => {
  let component: DetallesLoteTarjetasModalComponent;
  let fixture: ComponentFixture<DetallesLoteTarjetasModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetallesLoteTarjetasModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetallesLoteTarjetasModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
