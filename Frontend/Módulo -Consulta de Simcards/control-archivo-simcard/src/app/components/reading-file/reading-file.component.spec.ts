import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReadingFileComponent } from './reading-file.component';

describe('ReadingFileComponent', () => {
  let component: ReadingFileComponent;
  let fixture: ComponentFixture<ReadingFileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReadingFileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReadingFileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
