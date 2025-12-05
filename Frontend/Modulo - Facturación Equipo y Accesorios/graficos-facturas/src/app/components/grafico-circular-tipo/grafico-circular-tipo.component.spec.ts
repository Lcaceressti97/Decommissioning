import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GraficoCircularTipoComponent } from './grafico-circular-tipo.component';

describe('GraficoCircularTipoComponent', () => {
  let component: GraficoCircularTipoComponent;
  let fixture: ComponentFixture<GraficoCircularTipoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GraficoCircularTipoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GraficoCircularTipoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
