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
  constructor(private datePipe:DatePipe) {
    pdfMake.vfs = pdfFonts.pdfMake.vfs;
  }

  generatePdf(data: any[]): void {
    const documentDefinition = {
      pageOrientation: 'landscape',
      content: [
        {
          columns: [
            {
              image: logo,
              width: 100,
              alignment: 'left',
              margin: [10, 10,0,0],
            },
            {
              text: 'Reporte Facturación Equipo y Accesorios',
              fontSize: 24,
              bold: true,
              alignment: 'center',
              margin: [0, 10],
            }
            
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
                item.created,
                item.dueDate,
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
              text: 'Reporte Facturación Equipo y Accesorios',
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
            widths: ['auto', 'auto', 'auto', 'auto', 'auto','auto', 'auto', 'auto', 'auto'],
            body: [
              [
                { text: 'No. Factura', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Tipo Factura', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Estado', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Cliente', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Vendedor', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Compañia', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Fecha Creación', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' } ,
                { text: 'Fecha Vencimiento', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' } ,
                { text: 'Autorizado Por', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' } ,
              ],
              ...data.map((item) => [
                item.invoiceNo,
                item.invoiceType,
                item.status,
                item.customer,
                item.seller,
                item.agency,
                item.created,
                item.dueDate,
                item.authorizingUser,
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

  generatePdfTwo(data: any[], title:string): void {
    //console.log(data);

    const documentDefinition = {
      pageOrientation: 'landscape',
      content: [
        {
          columns: [
            {
              text: title,
              fontSize: 18,
              bold: true,
              alignment: 'center',
              margin: [10, 10],
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
          columns: [
            {
              text: 'Reporte Control Registro de Facturación',
              fontSize: 18,
              bold: true,
              alignment: 'center',
              margin: [0, 10],
            }
          ]
        },
        {
          text: ' ',
          margin: [0, 10],
        },
        {
          table: {
            headerRows: 1,
            widths: ['auto', 'auto', 'auto', 'auto', 'auto'],
            body: [
              [
                { text: 'No. PreFactura', style: 'tableHeader' },
                { text: 'Tipo de Transacción', style: 'tableHeader' },
                { text: 'Descripción', style: 'tableHeader' },
                { text: 'Usuario', style: 'tableHeader' },
                { text: 'Fecha Creación', style: 'tableHeader' }
              ],
              ...data.map(item => [
                item.idPrefecture,
                item.typeApproval,
                item.description,
                item.userCreate,
                item.created,
              ]),
            ],
          },
          layout: {
            hLineWidth: function (i, node) {
              return (i === 0 || i === node.table.body.length) ? 2 : 1;
            },
            vLineWidth: function (i, node) {
              return 1; // Añadir línea vertical entre columnas
            },
            hLineColor: function (i, node) {
              return i === 0 ? '#fff' : '#aaa'; // Cambiar color de fondo del encabezado
            },
            paddingLeft: function (i, node) { return 8; },
            paddingRight: function (i, node) { return 8; },
            paddingTop: function (i, node) { return 4; },
            paddingBottom: function (i, node) { return 4; },
          },
          style: 'tableExample',
        },
      ],
      defaultStyle: {
        font: 'Roboto',
      },
      styles: {
        tableHeader: {
          fillColor: '#007BFF',
          color: '#fff',
          bold: true,
        },
        tableExample: {
          margin: [0, 5, 0, 15],
        },
      },
    };

    pdfMake.createPdf(documentDefinition).open();
  }

  generatePdfNavegaTwo(data: any[], title?:string,margin?:any): void {
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
              text: 'Reporte Control Registro de Facturas',
              fontSize: 24,
              bold: true,
              alignment: 'center',
              margin: [0, 10],
            },
          ],
        },
        {
          columns: [
            {
              text: title,
              fontSize: 18,
              bold: true,
              alignment: 'center',
              margin: [margin, 10],
            }
          ]
        },
        {
          text: ' ',
          margin: [0, 10],
        },
        {
          table: {
            headerRows: 1,
            widths: [50, 180, 300, 100, 100],
            body: [
              [
                { text: 'No. PreFactura', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white'},
                { text: 'Tipo de Transacción', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Descripción', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Usuario', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                { text: 'Fecha Creación', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' } ,
              ],
              ...data.map((item) => [
                item.idPrefecture,
                item.typeApproval,
                item.description,
                item.userCreate,
                this.datePipe.transform(item.created,"dd/MM/yyyy hh:mm:ss")
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
