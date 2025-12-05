// src/app/mocks/mock-administration-offers.ts

import { ReportResponse } from "../entities/response";


export const MOCK_REPORT_RESPONSE: ReportResponse = {
  code: 200,
  description: 'Success',
  data: [
    {
      id: 1,
      detail: 'Reporte de transacciones 20240805',
      status: 1, // Activo
      createdDate: '2024-09-01T10:00:00Z'
    },
    {
      id: 2,
      detail: 'Error en envio de correo 20240607',
      status: 0, // Inactivo
      createdDate: '2024-09-02T12:00:00Z'
    }
  ],
  errors: []
};
