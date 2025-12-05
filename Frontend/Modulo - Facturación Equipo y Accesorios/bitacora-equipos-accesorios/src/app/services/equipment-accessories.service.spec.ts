import { TestBed } from '@angular/core/testing';

import { EquipmentAccessoriesService } from './equipment-accessories.service';

describe('EquipmentAccessoriesService', () => {
  let service: EquipmentAccessoriesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EquipmentAccessoriesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
