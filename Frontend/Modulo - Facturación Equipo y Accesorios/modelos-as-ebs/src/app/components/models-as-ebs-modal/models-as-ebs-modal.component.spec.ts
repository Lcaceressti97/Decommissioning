import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ModelsAsEbsModalComponent } from './models-as-ebs-modal.component';

describe('ModelsAsEbsModalComponent', () => {
  let component: ModelsAsEbsModalComponent;
  let fixture: ComponentFixture<ModelsAsEbsModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModelsAsEbsModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModelsAsEbsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
