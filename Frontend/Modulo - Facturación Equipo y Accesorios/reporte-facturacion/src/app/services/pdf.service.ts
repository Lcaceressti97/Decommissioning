// pdf.service.ts

import { Injectable } from '@angular/core';
import * as pdfMake from 'pdfmake/build/pdfmake';
import * as pdfFonts from 'pdfmake/build/vfs_fonts';
import { Tigo as logo } from "../utils/Resources";
import { DatePipe } from '@angular/common';

@Injectable({
  providedIn: 'root',
})
export class PdfService {
  constructor(private datePipe: DatePipe) {
    pdfMake.vfs = pdfFonts.pdfMake.vfs;
  }

  generatePdf(data: any[]): void {
    const documentDefinition = {
      pageOrientation: 'landscape',
      content: [
        {
          columns: [
            {
              text: 'Reporte Facturación Equipos y Accesorios',
              fontSize: 18,
              bold: true,
              alignment: 'center',
              margin: [0, 10],
            },
            {
              image: logo,
              width: 100,
              alignment: 'left',
              margin: [0, 10],
            },
          ],
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
                'No. Factura',
                'Tipo de Factura',
                'Estado',
                'Cliente',
                'Vendedor',
                'Compañia',
                'Fecha Creación',
                'Fecha Vencimiento',
                'Autorizado Por',
              ],
              ...data.map((item) => [
                item.invoiceNo,
                item.invoiceType,
                item.status,
                item.customer,
                item.seller,
                item.agency,
                this.datePipe.transform(item.created,"dd/MM/yyyy hh:mm:ss"),
                this.datePipe.transform(item.dueDate,"dd/MM/yyyy hh:mm:ss"),
                item.authorizingUser,
              ]),
            ],
          },
          layout: 'lightHorizontalLines', 
        },
      ],
      defaultStyle: {
        font: 'Roboto', 
      },
    };

    pdfMake.createPdf(documentDefinition).open();
  }

  generatePdfNavega(data: any[], title?:string): void {
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
              text: 'Reporte Facturación Equipos y Accesorios',
              fontSize: 24,
              bold: true,
              alignment: 'center',
              margin: [0, 10],
            },
          ],
        },
        {
          text: ' ',
          margin: [0, 10],
        },
        {
          table: {
            headerRows: 1,
            widths: ['auto', 'auto', 'auto', 'auto', 'auto','auto', 'auto', 'auto', 'auto','auto','auto','auto','auto','auto'],
            body: [
              [
                { text: 'No. Factura', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Tipo Factura', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Estado', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Cliente', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Vendedor', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Sucursal', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Bodega', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Fecha Creación', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' } ,
                { text: 'Exonerada', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' } ,
                { text: 'SubTotal', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' } ,
                { text: 'Descuento', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' } ,
                { text: 'I.S.V', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' } ,
                { text: 'Total', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' } ,
                { text: 'Emitido Por', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' } ,
              ],
              ...data.map((item) => [
                item.invoiceNo,
                item.invoiceType,
                item.status,
                item.customer,
                item.seller,
                item.agency,
                item.warehouse,
                this.datePipe.transform(item.created,"dd/MM/yyyy hh:mm:ss"),
                item.exonerationStatus === 0 ? "No" : "Sí",
                item.subtotal,
                item.discount,
                item.amountTax,
                item.amountTotal,
                item.userIssued,
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
}
