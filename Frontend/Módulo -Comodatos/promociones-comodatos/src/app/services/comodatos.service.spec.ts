import { TestBed } from '@angular/core/testing';

import { ComodatosService } from './comodatos.service';

describe('ComodatosService', () => {
  let service: ComodatosService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ComodatosService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
