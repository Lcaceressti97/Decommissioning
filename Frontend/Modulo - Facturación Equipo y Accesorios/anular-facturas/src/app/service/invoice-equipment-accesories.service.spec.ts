import { TestBed } from '@angular/core/testing';

import { InvoiceEquipmentAccesoriesService } from './invoice-equipment-accesories.service';

describe('InvoiceEquipmentAccesoriesService', () => {
  let service: InvoiceEquipmentAccesoriesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InvoiceEquipmentAccesoriesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
