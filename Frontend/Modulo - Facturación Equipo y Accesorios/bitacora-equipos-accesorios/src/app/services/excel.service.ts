import { Injectable } from '@angular/core';
import * as ExcelJS from 'exceljs';

@Injectable({
  providedIn: 'root'
})
export class ExcelService {

  constructor() { }

  generarExcel(data: any[], sheetName: string, columnNames: string[], filterColumnName?: string) {
    const workbook = new ExcelJS.Workbook();
    const worksheet = workbook.addWorksheet(sheetName);

    const headerCellStyle = {
      fill: {
        type: 'pattern',
        pattern: 'solid',
        fgColor: { argb: 'ff001d5f' }
      },
      font: {
        bold: true,
        color: { argb: 'FFFFFFFF' }
      }
    };

    worksheet.addRow(columnNames);


    const headerRow = worksheet.getRow(1);
    headerRow.eachCell((cell) => {
      cell.fill = headerCellStyle.fill as ExcelJS.Fill;
      cell.font = headerCellStyle.font;
    });


    data.forEach((row) => {
      worksheet.addRow(row);
    });


    worksheet.autoFilter = {
      from: { row: 1, column: 1 },
      to: { row: data.length + 1, column: columnNames.length }
    };


    worksheet.columns.forEach((column) => {
      column.width = 25;
    });


    workbook.xlsx.writeBuffer().then((buffer) => {
      this.guardarArchivo(buffer, sheetName + '.xlsx');
    });
  }

  private guardarArchivo(buffer: ArrayBuffer, fileName: string) {
    const data = new Blob([buffer], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
    const url = window.URL.createObjectURL(data);
    const link = document.createElement('a');
    link.href = url;
    link.download = fileName;
    link.click();
  }

}
