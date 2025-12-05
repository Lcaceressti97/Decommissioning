import { TestBed } from '@angular/core/testing';

import { ModelsAsEbsService } from './models-as-ebs.service';

describe('ModelsAsEbsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ModelsAsEbsService = TestBed.get(ModelsAsEbsService);
    expect(service).toBeTruthy();
  });
});
