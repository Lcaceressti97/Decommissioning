import { Injectable } from '@angular/core';
import * as pdfMake from 'pdfmake/build/pdfmake';
import * as pdfFonts from 'pdfmake/build/vfs_fonts';
import { Tigo as logo } from "../utils/Resources";

@Injectable({
  providedIn: 'root'
})
export class PdfGeneratorService {
  constructor() {
    pdfMake.vfs = pdfFonts.pdfMake.vfs;
  }

  generatePdfNavega(data: any[], startDate: string, endDate: string): void {
    
    const documentDefinition = {
      pageOrientation: 'landscape',
      content: [
        {
          columns: [
            {
              image: logo,
              width: 100,
              alignment: 'left',
              margin: [10, 10, 0, 0],
            },
            {
              text: 'Informe de Pagos Navega',
              fontSize: 24,
              bold: true,
              alignment: 'center',
              margin: [0, 10],
            },
          ],
        },
        {
          text: `Fecha de Inicio: ${startDate} - Fecha de Fin: ${endDate}`,
          alignment: 'center',
          fontSize: 14,
          margin: [100, 0, 0, 0],
        },
        {
          text: ' ',
          margin: [0, 10],
        },
        {
          table: {
            headerRows: 1,
            widths: ['auto', 'auto', 'auto', 'auto', 'auto', 'auto', 'auto', 'auto', 'auto', 'auto'],
            body: [
              [
                { text: 'Código Navega', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Código Producto', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Moneda', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Monto Pago', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Banco', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Autorización Banco', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Tasa de Cambio', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Fecha Pago', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Cuenta del EBS', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'STS de Transacción', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
              ],
              ...data.map((item) => [
                item.navegaCode,
                item.productCode,
                item.currency,
                item.paymentAmount,
                item.bank,
                item.bankAuthorization,
                item.exchangeRate,
                item.paymentDate,
                item.ebsAccount,
                item.transactionSts
              ]),
            ],
          },
          layout: {
            hLineWidth: function (i, node) {
              return (i === 0 || i === node.table.body.length) ? 2 : 1;
            },
            vLineWidth: function (i, node) {
              return (i === 0 || i === node.table.widths.length) ? 2 : 1;
            },
          },
        },
      ],
      defaultStyle: {
        font: 'Roboto',
      },
    };

    pdfMake.createPdf(documentDefinition).open();
  }

  generatePdfAmnet(dataAmnet: any[], startDate: string, endDate: string): void {
    const documentDefinition = {
      pageOrientation: 'landscape',
      content: [
        {
          columns: [
            {
              image: logo,
              width: 100,
              alignment: 'left',
              margin: [10, 10, 0, 0],
            },
            {
              text: 'Informe de Pagos Amnet',
              fontSize: 24,
              bold: true,
              alignment: 'center',
              margin: [0, 10],
            },
          ],
        },
        {
          text: `Fecha de Inicio: ${startDate} - Fecha de Fin: ${endDate}`,
          alignment: 'center',
          fontSize: 14,
          margin: [100, 0, 0, 0],
        },
        {
          text: ' ',
          margin: [0, 10],
        },
        {
          table: {
            headerRows: 1,
            widths: ['auto', 'auto', 'auto', 'auto', 'auto', 'auto', 'auto', 'auto', 'auto'],
            body: [
              [
                { text: 'Código Amnet', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Moneda', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Monto Pago', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Banco Amnet', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Banco Tigo', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Autorización Banco', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Tasa de Cambio', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Fecha Pago', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'STS de Transacción', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
              ],
              ...dataAmnet.map((item) => [
                item.amnetCode,
                item.currency,
                item.paymentAmount,
                item.amnetBank,
                item.tigoBank,
                item.bankAuthorization,
                item.exchangeRate,
                item.paymentDate,
                item.transactionSts
              ]),
            ],
            
          },
          layout: {
            hLineWidth: function (i, node) {
              return (i === 0 || i === node.table.body.length) ? 2 : 1;
            },
            vLineWidth: function (i, node) {
              return (i === 0 || i === node.table.widths.length) ? 2 : 1;
            },
            
          },
        },
      ],
      defaultStyle: {
        font: 'Roboto',
      },
    };
    console.log("Datos para el PDF:", dataAmnet);
    console.log("Valores de Código Amnet:", dataAmnet.map(item => item.amnetCode));
    console.log("Valores de Moneda:", dataAmnet.map(item => item.currency));
    console.log("Valores de Monto Pago:", dataAmnet.map(item => item.paymentAmount));
    console.log("Valores de Banco Amnet:", dataAmnet.map(item => item.amnetBank));
    console.log("Valores de Banco Tigo:", dataAmnet.map(item => item.tigoBank));
    console.log("Valores de Autorización Banco:", dataAmnet.map(item => item.bankAuthorization));
    console.log("Valores de Tasa de Cambio:", dataAmnet.map(item => item.exchangeRate));
    console.log("Valores de Fecha Pago:", dataAmnet.map(item => item.paymentDate));
    console.log("Valores de STS de Transacción:", dataAmnet.map(item => item.transactionSts));

    pdfMake.createPdf(documentDefinition).open();
  }
}