import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoteTarjetasModalComponent } from './lote-tarjetas-modal.component';

describe('LoteTarjetasModalComponent', () => {
  let component: LoteTarjetasModalComponent;
  let fixture: ComponentFixture<LoteTarjetasModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoteTarjetasModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoteTarjetasModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
