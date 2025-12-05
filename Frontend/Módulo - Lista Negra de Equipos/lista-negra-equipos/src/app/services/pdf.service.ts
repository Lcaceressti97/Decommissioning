import { Injectable } from '@angular/core';
import * as pdfMake from 'pdfmake/build/pdfmake';
import * as pdfFonts from 'pdfmake/build/vfs_fonts';
import { Tigo as logo } from "../utils/Resources";
import { HistoricalDetail } from '../models/HistoricalDetail';

@Injectable({
  providedIn: 'root'
})
export class PdfService {
  constructor() {
    pdfMake.vfs = pdfFonts.pdfMake.vfs;
  }

  generatePdf(rowsHistorical: HistoricalDetail): void {

    const rowsHistoricalData = rowsHistorical;


    const lockInfoSection = rowsHistoricalData?.nombreUsuarioTransaccion
      ? {
        table: {
          widths: ['*'],
          body: [
            [
              {
                text: 'Información de Bloqueo',
                fontSize: 14,
                bold: true,
                alignment: 'center',
                fillColor: '#01255c',
                color: 'white',
              },
            ],
          ],
        },
      }
      : null;

    const lockSection = rowsHistoricalData?.nombreUsuarioTransaccion
      ? {
        table: {
          widths: ['*', '*'],

          body: [
            [
              {
                text: 'Nombre Denunciante:',
                width: 100,
                bold: true,
              },
              {
                text: rowsHistoricalData?.nombreUsuarioTransaccion,
                alignment: 'center'
              },
            ],
            [
              {
                text: 'Identidad:',
                width: 100,
                bold: true,
              },
              {
                text: rowsHistoricalData?.identidadUsuarioTransaccion,
                alignment: 'center'
              },
            ],
            [
              {
                text: 'Dirección:',
                width: 100,
                bold: true,
              },
              {
                text: rowsHistoricalData?.direccionUsuarioTransaccion,
                alignment: 'center'
              },
            ],
            [
              {
                text: 'Teléfono:',
                width: 100,
                bold: true,
              },
              {
                text: rowsHistoricalData?.telefonoUsuarioTransaccion,
                alignment: 'center'
              },
            ],
            [
              {
                text: 'Motivo del Bloqueo:',
                width: 100,
                bold: true,
              },
              {
                text: rowsHistoricalData?.motivoBloqueo,
                alignment: 'center'
              },
            ],
            [
              {
                text: 'Fecha Bloqueo:',
                width: 100,
                bold: true,
              },
              {
                text: rowsHistoricalData?.fechaBloqueo,
                alignment: 'center'
              },
            ],
          ],

        },
      }
      : null;

    const unlockInfoSection = rowsHistoricalData?.nombreDesbloqueante
      ? {
        table: {
          widths: ['*'],
          body: [
            [
              {
                text: 'Información Desbloqueo',
                fontSize: 14,
                bold: true,
                alignment: 'center',
                fillColor: '#01255c',
                color: 'white'
              },
            ],
          ],
        },
      }
      : null;

    const unlockSection = rowsHistoricalData?.nombreDesbloqueante
      ? {
        table: {
          widths: ['*', '*'],
          body: [
            [
              {
                text: 'Nombre Desbloqueante:',
                width: 100,
                bold: true,
              },
              {
                text: rowsHistoricalData?.nombreDesbloqueante,
                alignment: 'center'
              },
            ],
            [
              {
                text: 'Identidad:',
                width: 100,
                bold: true,
              },
              {
                text: rowsHistoricalData?.identidadDesbloqueante,
                alignment: 'center'
              },
            ],
            [
              {
                text: 'Dirección:',
                width: 100,
                bold: true,
              },
              {
                text: rowsHistoricalData?.direccionDesbloqueante,
                alignment: 'center'
              },
            ],
            [
              {
                text: 'Teléfono:',
                width: 100,
                bold: true,
              },
              {
                text: rowsHistoricalData?.telefonoDesbloqueante,
                alignment: 'center'
              },
            ],
            [
              {
                text: 'Motivo de Desbloqueo:',
                width: 100,
                bold: true,
              },
              {
                text: rowsHistoricalData?.motivoDesbloqueo,
                alignment: 'center'
              },
            ],
            [
              {
                text: 'Fecha Desbloqueo:',
                width: 100,
                bold: true,
              },
              {
                text: rowsHistoricalData?.fechaDesbloqueo,
                alignment: 'center'
              },
            ],
          ],
        },
      }
      : null;

    const documentDefinition = {
      pageOrientation: 'portrait',
      content: [
        {
          columns: [
            {
              image: logo,
              width: 100,
            },
            {
              text: 'Detalle Bloqueo por Robo y Extravío',
              fontSize: 22,
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
            widths: ['*'],
            body: [
              [
                {
                  text: 'Información General',
                  fontSize: 14,
                  bold: true,
                  alignment: 'center',
                  fillColor: '#01255c',
                  color: 'white'
                },
              ],
            ],
          },
        },
        {
          table: {
            widths: ['*', '*'],
            body: [
              [
                {
                  text: 'ESN / IMEI:',
                  width: 100,
                  bold: true,
                },
                {
                  text: rowsHistoricalData?.esnImei,
                  alignment: 'center'
                },
              ],
              [
                {
                  text: 'IMEI sin ULT.DIGIT:',
                  width: 100,
                  bold: true,
                },
                {
                  text: rowsHistoricalData?.ivesn,
                  alignment: 'center'
                },
              ],
              [
                {
                  text: 'Operador:',
                  width: 100,
                  bold: true,
                },
                {
                  text: rowsHistoricalData?.operador,
                  alignment: 'center'
                },
              ],
              [
                {
                  text: 'Tipo de Bloqueo:',
                  width: 100,
                  bold: true,
                },
                {
                  text: rowsHistoricalData?.tipobloqueo,
                  alignment: 'center'
                },
              ],
              [
                {
                  text: 'Teléfono:',
                  width: 100,
                  bold: true,
                },
                {
                  text: rowsHistoricalData?.telefono,
                  alignment: 'center'
                },
              ],
/*               [
                {
                  text: 'Cuenta Facturación:',
                  width: 100,
                  bold: true,
                },
                {
                  text: rowsHistoricalData?.anexo,
                  alignment: 'center'
                },
              ], */
              [
                {
                  text: 'Tipo de Tecnología:',
                  width: 100,
                  bold: true,
                },
                {
                  text: rowsHistoricalData?.technologyType,
                  alignment: 'center'
                },
              ],
              [
                {
                  text: 'Simcard:',
                  width: 100,
                  bold: true,
                },
                {
                  text: rowsHistoricalData?.simcard,
                  alignment: 'center'
                },
              ],
              [
                {
                  text: 'Usuario Transacción:',
                  width: 100,
                  bold: true,
                },
                {
                  text: rowsHistoricalData?.usuarioTransaccion,
                  alignment: 'center'
                },
              ],
              [
                {
                  text: 'Pantalla Transacción:',
                  width: 100,
                  bold: true,
                },
                {
                  text: rowsHistoricalData?.pantallaTransaccion,
                  alignment: 'center'
                },
              ],
              [
                {
                  text: 'Modelo:',
                  width: 100,
                  bold: true,
                },
                {
                  text: rowsHistoricalData?.modelo,
                  alignment: 'center'
                },
              ],
            ],
          },
        },
        {
          text: ' ',
          margin: [0, 10],
        },

        lockInfoSection,
        lockSection,
        {
          text: ' ',
          margin: [0, 10],
        },
        unlockInfoSection,
        unlockSection

      ],
      styles: {
        header: {
          fontSize: 22,
          alignment: 'center',
          bold: true,
        },
      },
      defaultStyle: {
        font: 'Roboto',
      },
    };

    pdfMake.createPdf(documentDefinition).open();

  }



}
