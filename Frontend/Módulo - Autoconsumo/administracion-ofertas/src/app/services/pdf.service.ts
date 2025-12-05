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
              text: 'Histórico Charge Code',
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
            widths: ['20%', '30%', '20%', '15%', '15%'],
            body: [
              [
                { text: 'Charge Code', fillColor: '#f0f0f0', bold: true, alignment: 'center' },
                { text: 'Item Name', fillColor: '#f0f0f0', bold: true, alignment: 'center' },
                { text: 'Usuario', fillColor: '#f0f0f0', bold: true, alignment: 'center' },
                { text: 'Estado', fillColor: '#f0f0f0', bold: true, alignment: 'center' },
                { text: 'Fecha', fillColor: '#f0f0f0', bold: true, alignment: 'center' },
              ],
              ...data.map((item) => [
                { text: item.chargeCode, alignment: 'center' },
                { text: item.itemName, alignment: 'center' },
                { text: item.userName, alignment: 'center' },
                { text: item.status, alignment: 'center' },
                { text: this.datePipe.transform(item.createDate, 'dd/MM/yyyy'), alignment: 'center' },
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
