import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GraficosCircularesComponent } from './graficos-circulares.component';

describe('GraficosCircularesComponent', () => {
  let component: GraficosCircularesComponent;
  let fixture: ComponentFixture<GraficosCircularesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GraficosCircularesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GraficosCircularesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
