import { ElementRef, Injectable } from '@angular/core';
import * as pdfMake from 'pdfmake/build/pdfmake';
import * as pdfFonts from 'pdfmake/build/vfs_fonts';
import { Tigo as logo } from "../utils/Resources";
import { InvoiceStatistics, InvoiceType, TablePdf } from '../model/model';
import { DatePipe } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class PdfService {

 
  constructor() {
    pdfMake.vfs = pdfFonts.pdfMake.vfs;
  }

  generatePDFTwo(image: any, imageTwo: any, imageThree: any, imageFour: any, imageFive: any, data?: InvoiceStatistics, dateInit?:any, dateEnd?:any) {

    const invoiceType = data.invoicesByType as InvoiceType;

    const documentDefinition = {
      pageOrientation: 'landscape',
      content: [
        // Página 1
        {
          columns: [
            {
              image: logo,
              width: 100,
              alignment: 'left',
              margin: [0, 10,0,10],
            },
            {
              text: 'Control de Gráficos',
              fontSize: 24,
              bold: true,
              alignment: 'center',
              margin: [-30, 10,0,20],
            }
            
          ]
        },
        {
          text: `Fecha de Inicio: ${dateInit}  Fecha Fin: ${dateEnd}`,
          fontSize: 18,
          bold: true,
          alignment: 'center',
          margin: [10, 10],
        },
        {
          text: 'Gráficos Generales',
          fontSize: 18,
          bold: true,
          alignment: 'center',
          margin: [20, 10,20,10]
        },
        {
          margin: [20, 20, 20, 20],
          columns: [
            {
              width: '50%',
              table: {
                headerRows: 1,
                widths: ['45%', '45%'],
                
                body: [
                  [
                    { text: 'Estado', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                    { text: 'Valores', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' }
                  ],
                  [
                    { text: 'Pendientes', fontSize: 12, aligment: 'center' },
                    { text: data.unauthorizedInvoices, fontSize: 12, alignment: 'center' },
                  ],
                  [
                    { text: 'Autorizadas', fontSize: 12, aligment: 'center' },
                    { text: data.authorizedInvoices, fontSize: 12, alignment: 'center' }
                  ],
                  [
                    { text: 'Emitidas', fontSize: 12, aligment: 'center' },
                    { text: data.issuedInvoices, fontSize: 12, alignment: 'center' }
                  ],
                  [
                    { text: 'Factura Fiscal', fontSize: 12, aligment: 'center' },
                    { text: data.invoicesWithTaxNumber, fontSize: 12, alignment: 'center' }
                  ],
                  [
                    { text: 'Anuladas Sin Emitir', fontSize: 12, aligment: 'center' },
                    { text: data.canceledInvoicesWithoutIssued, fontSize: 12, alignment: 'center' }
                  ],
                  [
                    { text: 'Anuladas Con Número Fiscal', fontSize: 12, aligment: 'center' },
                    { text: data.canceledInvoicesWithTaxNumber, fontSize: 12, alignment: 'center' }
                  ],
                  [
                    { text: 'Error', fontSize: 12, aligment: 'center' },
                    { text: data.errorInvoice, fontSize: 12, alignment: 'center' }
                  ],
                ]
              },
              layout: {
                hLineWidth: function (i, node) {
                  return (i === 0 || i === node.table.body.length) ? 2 : 1;
                },
                vLineWidth: function (i, node) {
                  return (i === 0 || i === node.table.widths.length) ? 2 : 1;
                },
              }
            },
            {
              image: image,
              width: '50%',
              height: '50%',
              margin: [0, 0],
              fit: [300, 300]
            }
          ]
        },
        {
          text: ' ',
          margin: [10,10,10,10]
        },
        {
          text: ' ',
          margin:[10,10,10,10]
        },
        {
          text: ' ',
          margin: [10,10,10,10]
        },
        {
          text: ' ',
          margin: [10,10,10,10]
        },


        // Pagina 2
        {
          text: 'Gráfico De Barra Por Vendedor',
          fontSize: 18,
          bold: true,
          alignment: 'center',
          margin: [20, 20, 20 , 20],
        },
        {
          image: imageTwo,
          width: '100%',
          margin: [20, 10, 20, 0],
          fit: [700, 700]
        },
        {
          text: ' ',
          margin: [10,0,10,0]
        },
        {
          text: ' ',
          margin: [20,0,20,0]
        },
        {
          text: ' ',
          margin: [20,0,20,0]
        },

        // Pagina 3
        {
          text: 'Gráfico Por Tipo De Facturación',
          fontSize: 18,
          bold: true,
          alignment: 'center',
          margin: [20, 10],
        },

        {
          margin: [20, 20, 20, 20],
          columns: [
            {
              width: '50%',
              table: {
                headerRows: 1,
                widths: ['45%', '45%'],
                body: [
                  [
                    { text: 'Tipo', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' },
                    { text: 'Valores', fontSize: 14, bold: true, alignment: 'center', fillColor: '#01255c', color: 'white' }
                  ],
                  [
                    { text: 'FC1', fontSize: 12, alignment: 'center' },
                    { text: invoiceType.FC1, fontSize: 12, alignment: 'center' },
                  ],
                  [
                    { text: 'FC2', fontSize: 12, alignment: 'center' },
                    { text: invoiceType.FC2, fontSize: 12, alignment: 'center' },
                  ],
                  [
                    { text: 'FC3', fontSize: 12, alignment: 'center' },
                    { text: invoiceType.FC3, fontSize: 12, alignment: 'center' },
                  ],
                  [
                    { text: 'FC4', fontSize: 12, alignment: 'center' },
                    { text: invoiceType.FC4, fontSize: 12, alignment: 'center' },
                  ],

                ]
              },
              layout: {
                hLineWidth: function (i, node) {
                  return (i === 0 || i === node.table.body.length) ? 2 : 1;
                },
                vLineWidth: function (i, node) {
                  return (i === 0 || i === node.table.widths.length) ? 2 : 1;
                },
              }
            },
            {
              image: imageThree,
              width: '50%',
              margin: [10, 10],
              fit: [400, 600]
            },

          ]
        },
        {
          text: ' ',
          margin: [20, 10,20,10],
        },
        {
          text: ' ',
          margin: [20, 10,20,10],
        },
        {
          text: ' ',
          margin: [20, 10,20,10],
        },
        {
          text: ' ',
          margin: [20, 10,20,10],
        },
        {
          text: ' ',
          margin: [20, 10,20,10],
        },
        {
          text: ' ',
          margin: [20, 10,20,10],
        },
        

        // Página 4
        {
          text: 'Gráfico De Barra Por Tipo y Estado De Las Facturas',
          fontSize: 18,
          bold: true,
          alignment: 'center',
          margin: [20, 10, 20, 10],
        },
        {
          image: imageFour,
          width: '100%',
          margin: [20, 10, 20, 0],
          fit: [700, 700]
        },
        {
          text: ' ',
          margin: [10, 10,10,10],
        },
        {
          text: ' ',
          margin: [10, 10,10,10],
        },
        {
          text: ' ',
          margin: [10, 10,10,10],
        },

        // Pagina 5
        {
          text: 'Gráfico De Barra Por Sucursal',
          fontSize: 18,
          bold: true,
          alignment: 'center',
          margin: [20, 10,20,10],
        },
        {
          image: imageFive,
          width: '100%',
          margin: [10, 10, 10, 10],
          fit: [700, 700]
        },

      ]
    };


    pdfMake.createPdf(documentDefinition).open();

  }



}
