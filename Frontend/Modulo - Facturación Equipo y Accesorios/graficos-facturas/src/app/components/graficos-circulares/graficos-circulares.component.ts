import { Component, OnInit, Input } from '@angular/core';
import { Chart } from 'chart.js';
import { InvoiceStatistics } from 'src/app/model/model';

@Component({
  selector: 'app-graficos-circulares',
  templateUrl: './graficos-circulares.component.html',
  styleUrls: ['./graficos-circulares.component.css']
})
export class GraficosCircularesComponent implements OnInit {

  // Props

  // Inputs
  @Input() inputData: InvoiceStatistics;
  @Input() visible: boolean = false;

  // Ctx
  ctx1: any = null;

  // Element html
  elementStatePieChar: any = undefined;
  elementPieChartByTypeOfInvoice: any = null;

  // Gráficas

  // Graficars circulares
  statePieChar: any = null;
  pieChartByTypeOfInvoice: any = null;

  // Gráficas de barra
  barGraphByTypeAndState: any = null;

  constructor() { }

  ngOnInit(): void {
    //console.log(this.visible);
    this.buildGraphics();

  }

  /**
   * Método encargado de crear las gráficas
   * 
   */
  async buildGraphics() {


    // Obtenemos los elementos por el id 
    this.elementStatePieChar = document.getElementById('graf_pastel') as HTMLCanvasElement;

    this.buildGraphicsCircleAll();



    //this.ctx1 = this.elementStatePieChar.getContext("2d");



  }


  /**
   * Método encargado de construir gráficos circulares
   * 
   */
  buildGraphicsCircleAll(): void {


    // Construyendo gráficas
    this.statePieChar = new Chart(this.elementStatePieChar, {

      type: 'pie',

      data: {
        labels: [`Pendientes`, `Autorizadas`, `Emitidas`, `Anulada Sin Emitir`, `Anulada Con Número Fiscal`, `Error`],

        datasets: [{
          label: "",
          backgroundColor: ["#f2f2f2", "#28a745", "#3354b8", "#D9BD0F", "#DB6C0D", "#dc3545"],
          data: [this.inputData.unauthorizedInvoices, this.inputData.authorizedInvoices, this.inputData.issuedInvoices, this.inputData.canceledInvoicesWithoutIssued, this.inputData.canceledInvoicesWithTaxNumber, this.inputData.errorInvoice]
        }]
      },
      options: {
        tooltips: {
          callbacks: {
            label: function (tooltipItem, data) {

              let arrayData = data.datasets[0]['data'];
              let suma = 0;

              for (let i = 0; i <= arrayData.length - 1; i++) {

                suma += Number(arrayData[i]);
              }
              /*
              let data1 = Number(data.datasets[0]['data'][0]);
              let data2 = Number(data.datasets[0]['data'][1]);
              let data3 = Number(data.datasets[0]['data'][2]);
              let data4 = Number(data.datasets[0]['data'][2]);
              let data5 = Number(data.datasets[0]['data'][2]);
              let data6 = Number(data.datasets[0]['data'][2]);
              let data7 = Number(data.datasets[0]['data'][2]);
              let suma = data1 + data2 + data3 + data4 + data5 + data6 + data7;
              */
              let actual = Number(data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index]);
              let porcentaje = Number((actual / suma) * 100).toFixed(2) + '%';
              return actual + ' de ' + suma + ' : ' + porcentaje;
            },
          }
        },

        maintainAspectRatio: false,

        title: {
          display: true,
          text: `Total Facturas: ${this.inputData.authorizedInvoices + this.inputData.canceledInvoicesWithTaxNumber + this.inputData.canceledInvoicesWithoutIssued + this.inputData.errorInvoice + this.inputData.issuedInvoices + this.inputData.unauthorizedInvoices}`
        }

      }

    });

    this.statePieChar.update();


  }



  /**
   * Método que construye la gráfica para los tipo de factura en total
   * No está en uso
   * 
   */
  buildGraphicsCircleType(): void {

    // // Obtener las claves del mapa como un array
    //const etiquetas = Array.from(datos.invoicesByType.keys());
    //const valores = Array.from(datos.invoicesByType.values());



    // Construyendo gráficas
    this.pieChartByTypeOfInvoice = new Chart(this.elementPieChartByTypeOfInvoice, {

      type: 'pie',

      data: {
        labels: [`Pendientes`, `Autorizadas`, `Emitidas`, `Fiscal`, `Anulada Sin Emitir`, `Anulada Con Fiscal`, `Error`],

        datasets: [{
          label: "",
          backgroundColor: ["#28a745", "#dc3545", "#3354b8", "#28a745", "#dc3545", "#3354b8", "#dc3545"],
          data: [1, 2, 3, 4, 5, 6, 7]
        }]
      },
      options: {
        tooltips: {
          callbacks: {
            label: function (tooltipItem, data) {



              let data1 = Number(data.datasets[0]['data'][0]);
              let data2 = Number(data.datasets[0]['data'][1]);
              let data3 = Number(data.datasets[0]['data'][2]);
              let data4 = Number(data.datasets[0]['data'][2]);
              let data5 = Number(data.datasets[0]['data'][2]);
              let data6 = Number(data.datasets[0]['data'][2]);
              let data7 = Number(data.datasets[0]['data'][2]);
              let suma = data1 + data2 + data3 + data4 + data5 + data6 + data7;
              let actual = Number(data.datasets[tooltipItem.datasetIndex].data[tooltipItem.index]);
              let porcentaje = Number((actual / suma) * 100).toFixed(2) + '%';
              return actual + ' de ' + suma + ' : ' + porcentaje;
            },
          }
        },

        maintainAspectRatio: false,

        title: {
          display: true,
          text: `Total facturas ${120}`
        }

      }

    });

    this.pieChartByTypeOfInvoice.update();


  }

}
