import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { BrancheResponse, InvoiceDetailResponse } from 'src/app/entities/invoice.entity';
import { InvoiceEquipmentAccesories } from 'src/app/models/InvoiceEquipmentAccesories';
import { Billing, Branche } from 'src/app/models/billing';
import { InvoiceDetail } from 'src/app/models/billing';
import { InvoiceService } from 'src/app/services/invoice.service';
import { UtilService } from 'src/app/services/util.service';
import { Buttons } from 'src/types';
import * as printJS from 'print-js';


@Component({
  selector: 'app-print-invoice',
  templateUrl: './print-invoice.component.html',
  styleUrls: ['./print-invoice.component.css']
})
export class PrintInvoiceComponent implements OnInit {

  // Props
  @Output() messageEvent = new EventEmitter<Billing>();
  @Input() billing: Billing;

  // Variables calculadas
  total: number | string = 0;
  totalLetra: string = "";
  valoresGravables: number | string = 0;
  creditoContado: string = "";
  // Invoice
  // Invoice
  //invoiceDetail: InvoiceDetail[] = [];

  // BrancheOffice
  branche: Branche = {};
  userSystem: string = "";
  constructor(private activeModal: NgbActiveModal, private invoiceService: InvoiceService, public utilService: UtilService) { }

  async ngOnInit() {
    await this.getBrancheById();

    if (this.billing.fiscalProcess === "Facturación por Reclamo de Seguros") {
      this.valoresGravables = this.billing.subtotal - this.billing.exemptAmount;
      this.creditoContado = "CONTRA ENTREGA (SIN INT";

  } else {
    this.valoresGravables = this.billing.subtotal - this.billing.discount;
    this.creditoContado = "";

  }

    if (this.billing.totalLps) {

      this.total = this.billing.totalLps;
      this.totalLetra = this.billing.totalLpsLetters;

    } else {
      // Realizamos el calculo del total en lempiras
      this.total = this.billing.amountTotal * this.billing.exchangeRate;
      //console.log(this.total.toFixed(2));
      this.total = Number(this.total.toFixed(2));
      //console.log(this.total);
      //await this.getInvoiceDetail();
      this.totalLetra = await this.convertirNumberToLetter(this.total);
    }

    this.userSystem = this.utilService.getSystemUser();

  }

    calculateValue(invoice: InvoiceDetail): number {
      if (this.billing.fiscalProcess === "Facturación por Reclamo de Seguros" && invoice.serieOrBoxNumber != null) {
          return invoice.amountTotal;
      } else {
          return invoice.unitPrice * invoice.quantity;
      }
  }

  closeModal() {
    this.activeModal.close();

  }

  /**
   * Método que convierte números a letras
   * 
   */
  convertirNumberToLetter(numero: number): Promise<string> {

    return new Promise((resolve, reject) => {
      //console.log(numero);

      const numeroStr: string = numero.toFixed(2);
      let numeroEntero: number = parseInt(numeroStr);
      let decenaBool: boolean = false;
      let decimalLetras: string = "00/100";

      /**
       * Validamos si el total viene con decimales
       * 
       */
      if (numeroStr.includes(".")) {
        const arrayDecimal: string[] = numeroStr.split(".");
        decimalLetras = `${arrayDecimal[1]}/100`;
      }

      const unidades: string[] = ['', 'UNO', 'DOS', 'TRES', 'CUATRO', 'CINCO', 'SEIS', 'SIETE', 'OCHO', 'NUEVE'];
      const decenas: string[] = ['DIEZ', 'ONCE', 'DOCE', 'TRECE', 'CATORCE', 'QUINCE', 'DIECISEIS', 'DIECISIETE', 'DIECIOCHO', 'DIECINUEVE'];
      const decenasAltas: string[] = ['VEINTI', 'TREINTA', 'CUARENTA', 'CINCUENTA', 'SESENTA', 'SETENTA', 'OCHENTA', 'NOVENTA'];
      const centenas: string[] = ['CIENTO', 'DOSCIENTOS', 'TRESCIENTOS', 'CUATROCIENTOS', 'QUINIENTOS', 'SEISCIENTOS', 'SETESIENTOS', 'OCHOCIENTOS', 'NOVECIENTOS'];

      if (numeroEntero === 0) {
        return 'CERO ';
      }

      let letras = '';

      /**
       * Para los millones
       * 
       */
      if (numeroEntero >= 1000000) {
        if (numeroEntero == 1000000) {
          letras += "UN MILLON "
        } else {
          let numberMillon: string = Math.floor(numeroEntero / 1000000).toString();
          //console.log(numberMillon);

          if (!numberMillon.endsWith("00") && numberMillon.length == 3) {
            let numeroMillon: number = Number(numberMillon);

            letras += centenas[Math.floor(numeroMillon / 100) - 1] + ' ';
            numeroMillon %= 100;

            if (numeroMillon.toString().endsWith("0")) {
              letras += decenasAltas[Math.floor(numeroMillon / 10) - 2] + ' ';
            } else {
              letras += decenasAltas[Math.floor(numeroMillon / 10) - 2] + ' Y ';
            }

            numeroMillon %= 10;

            letras += unidades[numeroMillon] + ' MILLONES ';

          } else if (!numberMillon.endsWith("0") && numberMillon.length == 2) {
            let numeroMillon: number = Number(numberMillon);
            letras += decenasAltas[Math.floor(numeroMillon / 10) - 2] + ' Y ';

            numeroMillon %= 10;

            letras += unidades[numeroMillon] + ' MILLONES ';
          } else {

            if (numberMillon == "1") {
              letras += "UN MILLON "
            } else {
              letras += unidades[Number(numberMillon)] + ' MILLONES ';
            }

          }



        }
        numeroEntero %= 1000000;
        //console.log(numeroEntero);
      }

      if (numeroEntero >= 1000) {

        if (numeroEntero == 1000) {
          letras += "UN MIL "
        } else {
          let numberMil: string = Math.floor(numeroEntero / 1000).toString();
          //console.log(numberMil);

          /**
           * Condición que me dice si es de tres, dos o una cifra el valor de mil
           * 
           */
          if (numberMil.length == 3) {
            let numeroMil: number = Number(numberMil);
            //console.log(numeroMil);

            if (numberMil.endsWith("00")) {
              letras += centenas[Math.floor(numeroMil / 100) - 1] + ' MIL ';

            } else {

              let numeroLetra: string = numberMil.substring(0, 1) + "00";
              letras += centenas[Math.floor(Number(numeroLetra) / 100) - 1] + ' ';
              //console.log(numeroLetra);

            }
            numeroMil %= 100;

            //console.log(numeroMil);

            if (numeroMil.toString().length == 2) {


              if (numeroMil <= 19 && numeroMil >= 10) {
                letras += decenas[Math.floor(numeroMil / 10) - 2] + ' ';
                numeroMil %= 10;
              } else if (numeroMil <= 29 && numeroMil >= 20) {
                letras += decenasAltas[Math.floor(numeroMil / 10) - 2] + ' ';
                numeroMil %= 10;

                if (numeroMil > 0) {
                  letras += unidades[numeroMil] + ' ';
                }

              } else {

                // toDO
                let letraDecenaDos: string = Math.floor(numeroMil / 10).toString() + "0";
                //console.log(letraDecenaDos);
                letras += decenasAltas[Math.floor(Number(letraDecenaDos) / 10) - 2] + ' ';
                numeroMil %= 10;

                if (numeroMil > 0) {
                  letras = letras + "Y " + unidades[numeroMil] + ' MIL ';
                } else {
                  letras = letras + "MIL "
                }
              }


            } else {
              //console.log(numeroMil);
              if (letras.includes("MIL")) {
                //console.log("Entro");
                letras += unidades[numeroMil] + " MIL ";
              } else {
                //console.log("Entro 2");
                letras = letras + unidades[numeroMil];
                letras += " MIL ";
              }


              numeroMil %= 10;
            }




          } else if (numberMil.length == 2) {
            let numeroMil: number = Number(numberMil);
            //console.log(numeroMil %= 10);
            //console.log(numberMil);
            //console.log(numeroMil);

            // toDO
            if (numberMil.endsWith("0")) {
              letras += decenasAltas[Math.floor(numeroMil / 10) - 2] + " MIL ";
              numeroMil %= 10;
            } else {

              if (numeroMil <= 19 && numeroMil >= 10) {
                let numeroDecimalDecena: string = numeroMil.toString().substring(1, 2);
                //console.log(numeroDecimalDecena);
                letras += decenas[Number(numeroDecimalDecena)] + " MIL ";
                numeroMil %= 10;
              } else {
                letras += decenasAltas[Math.floor(numeroMil / 10) - 2] + ' Y ';
                numeroMil %= 10;
                letras += unidades[numeroMil] + ' MIL ';
              }

            }






          } else {

            letras += unidades[(Math.floor(numeroEntero / 1000))] + ' MIL ';

          }

        }
        numeroEntero %= 1000;
      }

      /**
       * Centenas
       * 
       */
      if (numeroEntero >= 100) {

        if (numeroEntero == 100) {
          letras += 'CIEN ';

        } else {
          letras += centenas[Math.floor(numeroEntero / 100) - 1] + ' ';

        }

        numeroEntero %= 100;
      }

      /**
       * Descenas 
       * 
       */
      if (numeroEntero >= 20) {

        if (numeroEntero >= 21 && numeroEntero <= 29) {
          letras += decenasAltas[Math.floor(numeroEntero / 10) - 2] + '';
        } else {
          letras += decenasAltas[Math.floor(numeroEntero / 10) - 2] + ' ';
          decenaBool = true;
        }
        numeroEntero %= 10;
      }

      if (numeroEntero >= 10) {
        letras += decenas[numeroEntero - 10] + ' ';
        numeroEntero = 0; // Evita duplicar las unidades
      }

      if (numeroEntero > 0) {

        if (decenaBool) {
          letras += "Y "
        }

        letras += unidades[numeroEntero] + ' ';


      }

      letras += "CON ";
      letras += decimalLetras;
      letras += " (LEMPIRAS)";
      //console.log(letras.trim());
      resolve(letras.trim());
    });




  }

  printInvoice() {


    const contenidoHTML = document.getElementById('invoice').innerHTML;


    // Opciones de impresión (opcional)
    const configuration: printJS.Configuration = {
      printable: contenidoHTML,
      type: 'raw-html',
      style: `

      .container {
        width: 100%;
        margin-right: -10px;
        margin-left: 10px;
      }

      .margin-test {
        margin-left: 10px
      }

      .div {
        display: block;
      }

      .row {
        display: flex;
        flex-wrap: wrap;
      }

      .col-12 {
        flex: 0 0 100%;
        max-width: 100%;
      }

      .col-9 {
        flex: 0 0 75%;
        max-width: 75%;
      }

      .col-8 {
        flex: 0 0 66.66666666%;
        max-width: 66.66666666%;
      }

      .col-6 {
        flex: 0 0 50%;
        max-width: 50%;
      }

      .col-4 {
        flex: 0 0 33.33333333%;
        max-width: 33.33333333%;
      }

      .col-3 {
        flex: 0 0 25%;
        max-width: 33.33333333%;
      }

      .col-2 {
        flex: 0 0 16.66666667%;
        max-width: 16.66666667%;
      }

      .test-font {
        font-family: Calibri,  sans-serif;
        font-size: 13px;
        font-weight: normal !important;
      }
      `
    };

    // Llamar a la función print de print-js
    //printJS("Prueba");
    printJS(configuration);
  }


  /**
   * Método que consume un servicio para obtener la información de la sucursal
   * 
   * @returns 
   */
  getBrancheById(): Promise<boolean> {

    return new Promise((resolve, reject) => {
      // Consumir el servicio
      this.invoiceService.getBrancheById(this.billing.idBranchOffices).subscribe((response) => {

        // Validamos si el response es ok
        if (response.status === 200) {

          let billingrancheResponse = response.body as BrancheResponse;


          this.branche = billingrancheResponse.data;

          resolve(true);

        } else {
          resolve(false);
        }

      }, (error) => {
        resolve(false);
      })

    })

  }

  formatDate(deadLine: string): string {
    const dateParts = deadLine.split('-'); 
    if (dateParts.length < 3) return '';
  
    const formattedDate = `${dateParts[0]}-${dateParts[1]}-${dateParts[2]}T00:00:00`; // Ajuste a formato ISO
    return formattedDate;
  }
  
}
