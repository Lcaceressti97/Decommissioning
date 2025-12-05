import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ComodatosDetailComponent } from './comodatos-detail.component';

describe('ComodatosDetailComponent', () => {
  let component: ComodatosDetailComponent;
  let fixture: ComponentFixture<ComodatosDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ComodatosDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ComodatosDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
