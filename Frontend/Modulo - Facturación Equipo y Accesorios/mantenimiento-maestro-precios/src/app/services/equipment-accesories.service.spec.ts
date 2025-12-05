import { TestBed } from '@angular/core/testing';

import { EquipmentAccesoriesService } from './equipment-accesories.service';

describe('EquipmentAccesoriesService', () => {
  let service: EquipmentAccesoriesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EquipmentAccesoriesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
