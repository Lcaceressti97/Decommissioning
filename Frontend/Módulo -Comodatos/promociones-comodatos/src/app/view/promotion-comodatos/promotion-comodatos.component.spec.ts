import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PromotionComodatosComponent } from './promotion-comodatos.component';

describe('PromotionComodatosComponent', () => {
  let component: PromotionComodatosComponent;
  let fixture: ComponentFixture<PromotionComodatosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PromotionComodatosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PromotionComodatosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
