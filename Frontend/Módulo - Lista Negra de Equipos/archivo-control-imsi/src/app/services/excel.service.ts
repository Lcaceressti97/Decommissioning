import { Injectable } from '@angular/core';
import * as ExcelJS from 'exceljs';

@Injectable({
  providedIn: 'root'
})
export class ExcelService {


  generarExcel(data: any[], sheetName: string, columnNames: string[], filterColumnName?: string) {
    const workbook = new ExcelJS.Workbook();
    const worksheet = workbook.addWorksheet(sheetName);

    // Estilo personalizado para los nombres de las columnas
    const headerCellStyle = {
      fill: {
        type: 'pattern',
        pattern: 'solid',
        fgColor: { argb: 'ff001d5f' } // Color azul (formato ARGB)
      },
      font: {
        bold: true,
        color: { argb: 'FFFFFFFF' } // Color de texto blanco
      }
    };

    // Agregar los nombres de las columnas
    worksheet.addRow(columnNames);

    // Aplicar estilo a las celdas de los nombres de las columnas
    const headerRow = worksheet.getRow(1);
    headerRow.eachCell((cell) => {
      cell.fill = headerCellStyle.fill as ExcelJS.Fill;
      cell.font = headerCellStyle.font;
    });

    // Agregar los datos
    data.forEach((row) => {
      worksheet.addRow(row);
    });

    // Configurar autofiltro si se proporciona el nombre de la columna para filtrar
    worksheet.autoFilter = {
      from: { row: 1, column: 1 },
      to: { row: data.length + 1, column: columnNames.length }
    };

    // Ajustar el ancho de las columnas
    worksheet.columns.forEach((column) => {
      column.width = 25;
    });

    // Guardar el archivo
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
