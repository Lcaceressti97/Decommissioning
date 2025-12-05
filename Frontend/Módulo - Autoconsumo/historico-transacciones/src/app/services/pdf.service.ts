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

  generatePdfHistorical(data: any[]): void {
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
              text: 'Histórico de Transacciones',
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
            widths: ['10%', '15%', '15%', '15%', '15%', '15%', '15%'],
            body: [
              [
                { text: 'Ciclo', fillColor: '#f0f0f0', bold: true, alignment: 'center' },
                { text: 'Charge Code', fillColor: '#f0f0f0', bold: true, alignment: 'center' },
                { text: 'Cuenta Factiración', fillColor: '#f0f0f0', bold: true, alignment: 'center' },
                { text: 'Origen Suscriptor', fillColor: '#f0f0f0', bold: true, alignment: 'center' },
                { text: 'Charge Code Status', fillColor: '#f0f0f0', bold: true, alignment: 'center' },
                { text: 'Item Name', fillColor: '#f0f0f0', bold: true, alignment: 'center' },
                { text: 'Fecha', fillColor: '#f0f0f0', bold: true, alignment: 'center' },
              ],
              ...data.map((item) => [
                { text: item.cycle, alignment: 'center' },
                { text: item.chargeCodeType, alignment: 'center' },
                { text: item.acctCode, alignment: 'center' },
                { text: item.priIdentOfSubsc, alignment: 'center' },
                { text: item.status, alignment: 'center' },
                { text: item.itemName, alignment: 'center' },
                { text: item.created,  alignment: 'center' },
              ]),
            ],
          },
          layout: {
            hLineWidth: function (i: number, node: any) {
              return i === 0 || i === node.table.body.length ? 2 : 1;
            },
            vLineWidth: function () {
              return 0;
            },
            hLineColor: function (i: number) {
              return i === 0 || i === 1 ? 'black' : '#aaa';
            },
            paddingLeft: function () {
              return 10;
            },
            paddingRight: function () {
              return 10;
            },
            paddingTop: function () {
              return 5;
            },
            paddingBottom: function () {
              return 5;
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
