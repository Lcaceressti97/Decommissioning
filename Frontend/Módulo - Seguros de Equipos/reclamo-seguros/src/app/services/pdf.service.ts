import { Injectable } from '@angular/core';
import * as pdfMake from 'pdfmake/build/pdfmake';
import * as pdfFonts from 'pdfmake/build/vfs_fonts';
import { Logo as logo } from "../utils/Resources";

@Injectable({
  providedIn: 'root'
})
export class PdfService {
  constructor() {
    pdfMake.vfs = pdfFonts.pdfMake.vfs;
  }

  generatePdf(rowsInsuranceClaim: any): void {
    if (rowsInsuranceClaim) {
      const documentDefinition = {
        pageOrientation: 'portrait',
        content: [
          {
            columns: [
              {
                image: logo,
                width: 100,
                alignment: 'left', 
              },
              {
                text: 'Detalle Reclamo de Seguros',
                fontSize: 22,
                bold: true,
                alignment: 'center',
                margin: [10, 10],
                width: '*',
                fillColor: '#063b66', 
               
              },
            ],
          },
          {
            text: '', 
            margin: [0, 10],
          },
          {
            style: 'info-box',
            table: {
              headerRows: 1,
              widths: ['50%', '50%'],
              body: [
                [
                  { text: 'Información del Cliente', bold: true, alignment: 'center', colSpan: 2, fillColor: '#063b66', color: 'white' },
                  {},
                ],
                [
                  { text: 'Número de Cliente:', bold: true },
                  rowsInsuranceClaim.customerAccount,
                ],
                [
                  { text: 'Cuenta de Servicio:', bold: true },
                  rowsInsuranceClaim.serviceAccount,
                ],
                [
                  { text: 'Cuenta de Facturación:', bold: true },
                  rowsInsuranceClaim.billingAccount,
                ],
                [
                  { text: 'Teléfono:', bold: true },
                  rowsInsuranceClaim.phone,
                ],
                [
                  { text: 'Estado del Teléfono:', bold: true },
                  rowsInsuranceClaim.phoneStatus,
                ],
                [
                  { text: 'Tipo de Cliente:', bold: true },
                  rowsInsuranceClaim.clientType,
                ],
                [
                  { text: 'Precio Actual:', bold: true },
                  rowsInsuranceClaim.actualPrice,
                ],
              ],
            },
            margin: [0, 10],
          },
          {
            style: 'info-box',
            table: {
              headerRows: 1,
              widths: ['50%', '50%'],
              body: [
                [
                  { text: 'Información del Equipo', bold: true, alignment: 'center', colSpan: 2, fillColor: '#063b66', color: 'white' },
                  {},
                ],
                [
                  { text: 'ESN Actual:', bold: true },
                  rowsInsuranceClaim.actualEsn,
                ],
                [
                  { text: 'Modelo Actual:', bold: true },
                  rowsInsuranceClaim.actualModel,
                ],
                [
                  { text: 'Tipo de Inventario Actual:', bold: true },
                  rowsInsuranceClaim.actualInvType,
                ],
                [
                  { text: 'Razón del Reclamo:', bold: true },
                  rowsInsuranceClaim.reasonClaim,
                ],
                [
                  { text: 'ESN Nuevo:', bold: true },
                  rowsInsuranceClaim.newEsn,
                ],
                [
                  { text: 'Modelo Nuevo:', bold: true },
                  rowsInsuranceClaim.newModel,
                ],
                [
                  { text: 'Tipo de Inventario Nuevo:', bold: true },
                  rowsInsuranceClaim.newInvType,
                ],
              ],
            },
            margin: [0, 10],
          },
          {
            style: 'info-box',
            table: {
              headerRows: 1,
              widths: ['50%', '50%'],
              body: [
                [
                  { text: 'Información Adicional', bold: true, alignment: 'center', colSpan: 2, fillColor: '#063b66', color: 'white' },
                  {},
                ],
                [
                  { text: 'Usuario que Creó:', bold: true },
                  rowsInsuranceClaim.userCreate,
                ],
                [
                  { text: 'Fecha de Creación:', bold: true },
                  rowsInsuranceClaim.dateCreate,
                ],
                [
                  { text: 'Usuario que Resolvió:', bold: true },
                  rowsInsuranceClaim.userResolution,
                ],
                [
                  { text: 'Fecha de Resolución:', bold: true },
                  rowsInsuranceClaim.dateResolution,
                ],
                [
                  { text: 'Tipo de Factura:', bold: true },
                  rowsInsuranceClaim.invoiceType,
                ],
                [
                  { text: 'Letra de la Factura:', bold: true },
                  rowsInsuranceClaim.invoiceLetter,
                ],
                [
                  { text: 'Número de Factura:', bold: true },
                  rowsInsuranceClaim.invoiceNumber,
                ],
                [
                  { text: 'Anexo de Sucursal:', bold: true },
                  rowsInsuranceClaim.branchAnnex,
                ],
                [
                  { text: 'Estado del Reclamo:', bold: true },
                  rowsInsuranceClaim.statusClaim === 'P' ? 'Procesado' : (rowsInsuranceClaim.statusClaim === 'A' ? 'Anulado' : 'E'),
                ],
                [
                  { text: 'Observaciones:', bold: true },
                  rowsInsuranceClaim.observations,
                ],
              ],
            },
            margin: [0, 10],
          },
        ],
        styles: {
          'info-box': {
            border: '2px solid #ccc',
            borderRadius: 10,
            padding: 10,
            marginBottom: 20,
            textAlign: 'center',
            backgroundColor: '#fff',
            color: '#333',
            shadow: [2, 2, 4, 'rgba(0, 0, 0, 0.3)'],
          },
        },
        defaultStyle: {
          font: 'Roboto',
        },
      };

      pdfMake.createPdf(documentDefinition).open();
    } else {
      // Manejar el caso en el que la información esté vacía, si es necesario
    }
  }
}
