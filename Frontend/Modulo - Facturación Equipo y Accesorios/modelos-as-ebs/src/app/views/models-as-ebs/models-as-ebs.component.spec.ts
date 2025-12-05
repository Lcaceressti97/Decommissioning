import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ModelsAsEbsComponent } from './models-as-ebs.component';

describe('ModelsAsEbsComponent', () => {
  let component: ModelsAsEbsComponent;
  let fixture: ComponentFixture<ModelsAsEbsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModelsAsEbsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModelsAsEbsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
