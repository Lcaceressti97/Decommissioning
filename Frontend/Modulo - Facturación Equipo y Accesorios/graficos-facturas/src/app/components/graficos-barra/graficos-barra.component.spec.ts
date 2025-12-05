import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GraficosBarraComponent } from './graficos-barra.component';

describe('GraficosBarraComponent', () => {
  let component: GraficosBarraComponent;
  let fixture: ComponentFixture<GraficosBarraComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GraficosBarraComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GraficosBarraComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
