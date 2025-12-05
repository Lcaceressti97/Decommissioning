// src/app/mocks/mock-administration-offers.ts

import { AdministrationOffersResponse } from '../entities/response';

export const MOCK_ADMINISTRATION_OFFERS_RESPONSE: AdministrationOffersResponse = {
  code: 200,
  description: 'Success',
  data: [
    {
      id: 1,
      offeringId: '198712984671',
      chargeCode: 'C_FD_AUTOCONSUMO_038',
      itemName: 'Add-On Recurrecte Paquete 15GB',
      userName: 'leonardo.vijil',
      status: 1,
      createDate: '2024-09-01T10:00:00Z'
    },
    {
      id: 2,
      offeringId: '139162584587',
      chargeCode: 'C_FD_AUTOCONSUMO_041',
      itemName: 'Add-On Recurrecte Paquete 18GB',
      userName: 'leonardo.vijil',
      status: 0, 
      createDate: '2024-09-02T12:00:00Z'
    },
    {
      id: 3,
      offeringId: '226985741254',
      chargeCode: 'C_FD_AUTOCONSUMO_022',
      itemName: 'Add-On Recurrecte Paquete 3GB',
      userName: 'leonardo.vijil',
      status: 1,
      createDate: '2023-09-03T14:30:00Z'
    }
  ],
  errors: []
};
