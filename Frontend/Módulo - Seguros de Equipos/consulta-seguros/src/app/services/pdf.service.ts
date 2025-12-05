import { Injectable } from '@angular/core';
import * as pdfMake from 'pdfmake/build/pdfmake';
import * as pdfFonts from 'pdfmake/build/vfs_fonts';
import { Tigo as logo } from "../utils/Resources";

@Injectable({
  providedIn: 'root'
})
export class PdfService {
  constructor() {
    pdfMake.vfs = pdfFonts.pdfMake.vfs;
  }

  generatePdf(rowsSafeControlEquipment: any): void {
    if (rowsSafeControlEquipment) {
      const documentDefinition = {
        pageOrientation: 'portrait',
        content: [
          {
            columns: [
              {
                image: logo,
                width: 110,
                alignment: 'left',
              },
              {
                text: 'Detalle del Seguro',
                fontSize: 22,
                bold: true,
                alignment: 'center',
                margin: [10, 10],
                width: '*',
                fillColor: '#063b66',
                color: 'black', // Color del texto
                decoration: 'underline', // Subrayar el título
                decorationStyle: 'double', // Estilo de subrayado
                decorationColor: 'black', // Color del subrayado
                characterSpacing: 2, // Espaciado entre caracteres
                italics: false, // Texto en cursiva
                border: [false, false, false, true], // Bordes (arriba, derecha, abajo, izquierda)
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
                  { text: 'ESN:', bold: true },
                  { text: rowsSafeControlEquipment.esn, alignment: 'center' },
                ],
                [
                  { text: 'Cuenta Cliente:', bold: true },
                  { text: rowsSafeControlEquipment.customerAccount, alignment: 'center' },
                ],
                [
                  { text: 'Cuenta de Servicio:', bold: true },
                  { text: rowsSafeControlEquipment.serviceAccount, alignment: 'center' },
                ],
                [
                  { text: 'Cuenta de Facturación:', bold: true },
                  { text: rowsSafeControlEquipment.billingAccount, alignment: 'center' },
                ],
                [
                  { text: 'Estado:', bold: true },
                  { text: rowsSafeControlEquipment.insuranceStatus, alignment: 'center' },
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
                  { text: 'Teléfono:', bold: true },
                  { text: rowsSafeControlEquipment.phone, alignment: 'center' },
                ],
                [
                  { text: 'Modelo del Teléfono:', bold: true },
                  { text: rowsSafeControlEquipment.phoneModel, alignment: 'center' },
                ],
                [
                  { text: 'Origen del Teléfono:', bold: true },
                  { text: rowsSafeControlEquipment.originPhone, alignment: 'center' },
                ],
                [
                  { text: 'Tipo de Inventario:', bold: true },
                  { text: rowsSafeControlEquipment.inventoryType, alignment: 'center' },
                ],
                [
                  { text: 'Tipo de Origen:', bold: true },
                  { text: rowsSafeControlEquipment.originType, alignment: 'center' },
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
                  { text: 'Información de Cuotas y Fechas', bold: true, alignment: 'center', colSpan: 2, fillColor: '#063b66', color: 'white' },
                  {},
                ],
                [
                  { text: 'Cuota Mensual:', bold: true },
                  { text: rowsSafeControlEquipment.monthlyFee, alignment: 'center' },
                ],
                [
                  { text: 'Período Actual:', bold: true },
                  { text: rowsSafeControlEquipment.currentPeriod, alignment: 'center' },
                ],
                [
                  { text: 'Cuota Mensual 2:', bold: true },
                  { text: rowsSafeControlEquipment.monthlyFee2, alignment: 'center' },
                ],
                [
                  { text: 'Período 2:', bold: true },
                  { text: rowsSafeControlEquipment.period2, alignment: 'center' },
                ],
                [
                  { text: 'Cuota Mensual 3:', bold: true },
                  { text: rowsSafeControlEquipment.monthlyFee3, alignment: 'center' },
                ],
                [
                  { text: 'Período 3:', bold: true },
                  { text: rowsSafeControlEquipment.period3, alignment: 'center' },
                ],
                [
                  { text: 'Fecha de Servicio:', bold: true },
                  { text: rowsSafeControlEquipment.dateStartService, alignment: 'center' },
                ],
                [
                  { text: 'Fecha de Inicio:', bold: true },
                  { text: rowsSafeControlEquipment.dateInit, alignment: 'center' },
                ],
                [
                  { text: 'Fecha de Fin:', bold: true },
                  { text: rowsSafeControlEquipment.dateEnd, alignment: 'center' },
                ],
                [
                  { text: 'Fecha de Inclusión:', bold: true },
                  { text: rowsSafeControlEquipment.dateInclusion, alignment: 'center' },
                ],
                [
                  { text: 'Fecha de Operación:', bold: true },
                  { text: rowsSafeControlEquipment.dateTransaction, alignment: 'center' },
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
