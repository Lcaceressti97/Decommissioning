import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GraficosPrincipalesComponent } from './graficos-principales.component';

describe('GraficosPrincipalesComponent', () => {
  let component: GraficosPrincipalesComponent;
  let fixture: ComponentFixture<GraficosPrincipalesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GraficosPrincipalesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GraficosPrincipalesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
