import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BulkEmissionComponent } from './bulk-emission.component';

describe('BulkEmissionComponent', () => {
  let component: BulkEmissionComponent;
  let fixture: ComponentFixture<BulkEmissionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BulkEmissionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BulkEmissionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
