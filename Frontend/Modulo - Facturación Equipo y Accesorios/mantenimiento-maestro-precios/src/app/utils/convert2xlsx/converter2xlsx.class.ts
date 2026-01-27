import * as moment from 'moment';
import * as XLSX from 'xlsx-js-style';

export class Converter2Xlsx {
  public static async Create(json, columnWidths) {
    const worksheet = XLSX.utils.aoa_to_sheet([]);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Facturas');

    // Obtener las claves de las primeras filas y convertirlas a mayúsculas
    const jsonKeys: string[] = [];
    for (const key in json[0]) {
      jsonKeys.push(key.toUpperCase());
    }

    const title = ['Reporte maestro de precios'];

    // Agregar el título comenzando en la columna C
    XLSX.utils.sheet_add_aoa(worksheet, [title], { origin: 'B3' });

    // Agregar los datos (empezando en la fila 5)
    XLSX.utils.sheet_add_json(worksheet, json, { origin: 'B5' });

    // Establecer estilo para los encabezados (color de fondo azul, texto blanco y negrita)
    for (let i = 0; i < jsonKeys.length; i++) {
      const cell = worksheet[XLSX.utils.encode_cell({ r: 4, c: i + 1 })]; // 'i + 2' para comenzar en C
      cell.s = {
        fill: {
          fgColor: { rgb: '2774B7' }, // Fondo azul
        },
        font: {
          bold: true, // Negrita
          color: { rgb: 'FFFFFF' }, // Letra blanca
        },
        alignment: {
          horizontal: 'center', // Alineación centrada
          vertical: 'center',
        },
      };
    }

    // Establecer estilo para el título (centrar y negrita)
    for (let i = 0; i < title.length; i++) {
      const cell = worksheet[XLSX.utils.encode_cell({ r: 2, c: i + 1 })]; // 'i + 2' para comenzar en C
      cell.s = {
        font: {
          bold: true, // Negrita
        },
        alignment: {
          horizontal: 'center', // Alineación centrada
          vertical: 'center',
        },
      };
    }

    // Aplicar los anchos a las columnas C a G
    worksheet['!cols'] = [undefined, ...columnWidths];

    // Establecer alineación centrada para todas las celdas (no solo encabezados y título)
    const range = worksheet['!ref']; // Obtener el rango completo de la hoja (por ejemplo: "C3:G10")
    if (range) {
      const [start, end] = range.split(':');
      const startCell = XLSX.utils.decode_cell(start);
      const endCell = XLSX.utils.decode_cell(end);

      for (let row = startCell.r; row <= endCell.r; row++) {
        for (let col = startCell.c; col <= endCell.c; col++) {
          const cell = worksheet[XLSX.utils.encode_cell({ r: row, c: col })];
          if (cell) {
            cell.s = {
              ...(cell.s || {}),
              alignment: {
                horizontal: 'center', // Alineación centrada
                vertical: 'center',
              },
            };
          }
        }
      }
    }

    // Escribir el archivo con compresión
    XLSX.writeFile(
      workbook,
      `Reporte Maestro de Precios ${moment().format('DD.MM.YYYY_hh.mm')}.xlsx`,
      { compression: true }
    );
  }
}
